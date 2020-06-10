from __future__ import absolute_import, unicode_literals
from celery import shared_task
from celery.contrib import rdb
from celery.utils.log import get_task_logger
from consumer.celery import app
logger = get_task_logger(__name__)


@shared_task
def filter_task(message):
    # result = SyncBag.objects.filter(id=message['id'])
    # print(result.count())
    # rdb.set_trace()
    logger.error('========> received message: {0!r}'.format(message))
    if message['syncItem']['grade'] < 'C':
        send_dedup_message(message)
        return 'sent to dedup'
    else:
        return 'message unusable!'


@shared_task
def send_dedup_message(message):
    with app.producer_pool.acquire(block=True) as producer:
        producer.publish(
            message,
            serializer='json',
            exchange='dedup',
            routing_key='dedup',
            # declare=[dedup_queue],
            retry=True,
        )