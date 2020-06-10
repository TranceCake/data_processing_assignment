import pika
import json

# this should be in a config file but I had trouble loading the django setting into this so I have put it here for ease.
connection = pika.BlockingConnection(
    pika.URLParameters('amqp://otrmjmki:lReftAHSXWS9SSFUgioHlElGLzeecvt3@rattlesnake.rmq.cloudamqp.com/otrmjmki'))
channel = connection.channel()
channel.queue_declare(queue='filter', durable=True)
channel.queue_declare(queue='dedup', durable=True)
channel.queue_declare(queue='poison', durable=True)

# the assumption was made that both missing and corrupt data were unacceptable for teh non-optional fields.
# according to the grading table messages that have all required fields get either an A or B grade.
# this makes the filtering rather easy since if an item has a C or lower, it has missing fields and is unusable.


def callback(ch, method, properties, body):
    message = json.loads(body)

    if message['syncItem']['grade'] < 'C':
        send('poison', body)
    else:
        send('dedup', body)

    ch.basic_ack(delivery_tag=method.delivery_tag)


def send(key, message):
    print('sending to {}'.format(key))
    channel.basic_publish(
        exchange='',
        routing_key=key,
        body=message,
        properties=pika.BasicProperties(delivery_mode=2))


channel.basic_consume(queue='filter', on_message_callback=callback)

print(' [*] Waiting for messages. To exit press CTRL+C')
channel.start_consuming()
