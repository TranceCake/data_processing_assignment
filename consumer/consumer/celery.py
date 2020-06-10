import os
from celery import Celery
from celery import bootsteps
from kombu import Consumer, Exchange, Queue
# from worker.tasks import filter_task

os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'consumer.settings')

filter_queue = Queue('filter', Exchange('filter'), 'filter')
dedup_queue = Queue('dedup', Exchange('dedup', type='direct'), 'dedup')

app = Celery('consumer')
app.config_from_object('django.conf:settings', namespace='CELERY')
app.autodiscover_tasks()

with app.pool.acquire(block=True) as conn:
    dedup_queue.declare()


class FilterStep(bootsteps.ConsumerStep):

    def get_consumers(self, channel):
        return [Consumer(channel,
                         queues=[filter_queue],
                         callbacks=[self.handle_message],
                         accept=['json'])]

    def handle_message(self, body, message):
        # result = app.send_task('worker.tasks.filter_task', kwargs={'message': body})
        result = app.send_task('worker.tasks.filter_task', kwargs=body)
        # result = filter_task(body)
        print(result)
        message.ack()


app.steps['consumer'].add(FilterStep)

