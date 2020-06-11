from __future__ import absolute_import, unicode_literals
from celery import shared_task
from celery.utils.log import get_task_logger
from consumer.celery import app
from .models import SyncBag

logger = get_task_logger(__name__)


@shared_task
def filter_task(message):
    if message['syncItem']['grade'] < 'C':
        send_message(message, 'dedup')
        return 'sent to dedup'
    else:
        send_message(message, 'poison')
        return 'message unusable! sent to poison'


@shared_task
def send_message(message, key):
    with app.producer_pool.acquire(block=True) as producer:
        producer.publish(
            message,
            serializer='json',
            exchange='',
            routing_key=key,
            retry=True,
        )


@shared_task
def dedup_task(message):
    # result = SyncBag.objects.filter(id=message['id'])
    # sync_bag = SyncBag.create(**message)
    # sync_bag.save()

    logger.info('{0!r}'.format(message))
    result, created = SyncBag.objects.get_or_create(**message)

    if not created:
        return 'object exists'

