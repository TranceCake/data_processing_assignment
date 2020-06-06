import os
from celery import Celery
from celery import bootsteps
from kombu import Consumer, Exchange, Queue

os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'consumer.settings')

# my_queue = Queue('custom', Exchange('custom'), 'routing_key')
my_queue = Queue('filter', Exchange('filter'), 'filter')

app = Celery('consumer')
app.config_from_object('django.conf:settings', namespace='CELERY')
app.autodiscover_tasks()


class MyConsumerStep(bootsteps.ConsumerStep):

    def get_consumers(self, channel):
        return [Consumer(channel,
                         queues=[my_queue],
                         callbacks=[self.handle_message],
                         accept=['json'])]

    def handle_message(self, body, message):
        print('Received message: {0!r}'.format(body))
        message.ack()


app.steps['consumer'].add(MyConsumerStep)
