# data_processing_assignment

Hi this is my code for the data processing test assignment. 

A few notes:

* I kept the master clean, so it only has code I consider 'working'.
The rest I merged into the development branch so it was all in one place.

* I started on hte consumer/worker feature branches. the goal was to create a filter step that sent to dedup and poison queues and a dedup step that would have identical separate workers. Searching online for what the best framework would be for this quickly got me to celery. I got a consumer working but failed ot get it to resend the messages back to the other queues. I tried to debug celery with a logger but that also didn't work showing no errors, just celerys debug logs but not my own logs. This all took the majority of my time. 

* After not getting celery to behave I decided to try a consumer using the 'pika' framework. this was easy enough following the rabbitmq docs, but I had troulbe to integrate the standalone pika code into django. This was not a problem for hte filter step since it didn't need the database. But for the dedup step I really wanted to use the django ORM.

* After not getting pika end django to work together I tried to just work in the deduplication logic but got stuck on the migrations. I got the database to work but upon trying to save my model it failed with a foreign key constraint. At this point I ran out of time to investigate this furhter.

* I invite you to look at the java generator code to get somewhat of a better glimpse of how I write code, since there is not much there on the python side.

* This assignment included a lot of frameworks and protocols I had never used/seen before, inlcluding: (django, amqp/rabbitMQ, multithreading in python, pika, celery). this made it much more difficult to get anywhere, especially without a mentor. I think I failed to find good resources to help me get on my way. The celery documentation was too brief and high over with only simple examples. Due to this I feel like I was not able to properly show my skill in software engineering.

* I recognized a lot of what django does from Java's Spring framework and hibernate ORM, it's just the implemmentation I got stuck on. Examples are the routing, the way templates are used, the way models and migrations work e.t.c.
