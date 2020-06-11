# data_processing_assignment

Hi this is my code for the data processing test assignment. 

A few notes:

* I kept the master clean, so it only has code I consider 'finished'.
The rest I merged into the development branch so it is all in one place for viewers ease.

* I just (thursday 11th of june) discovered that the problems I encountered with celery (described below) were due to it running on Windows. I did read the notice that as of celery 4.0 it was no longer supporting Windows, but I found an article that went into detail why and that it was quite easy to fix. Lesson learned I guess because trying my code on linux instantly fixed some of the issues I was having with the logger and threads becoming unresponsive. In the future I will probably use a virtual linux machine to do development from the get go, now that I have one set up.

* I started on the consumer/worker feature branches. the goal was to create a filter step that sent to dedup and poison queues and a dedup step that would have identical separate workers. Searching online for what the best framework would be for this quickly got me to celery. ~~I got a consumer working but failed ot get it to resend the messages back to the other queues. I tried to debug celery with a logger but that also didn't work showing no errors, just celery's debug logs but not my own logs. This all took the majority of my time.~~ In the branch feature/worker_second_attempt, running on linux, I actually got cellery and the logging to behave, now I got stuck on the django ORM if I had a little more time I'm sure I would have figured it out. It had to do with my nested db schema you can see it in odels.py how I tried to solve it with 'custom contructors' or class methods. 

* After not getting celery to behave I decided to try a consumer using the 'pika' framework. this was easy enough following the rabbitmq docs, but I had troulbe to integrate the standalone pika code into django. This was not a problem for the filter step since it didn't need the database. But for the dedup step I really wanted to use the django ORM.

* After not getting pika end django to work together I tried to just work in the deduplication logic in one of hte views but got stuck on the migrations. I got the database to work but upon trying to save my model it failed with a foreign key constraint. At this point I ran out of time to investigate this furhter.

* I invite you to also look at the java generator code to get somewhat of a better glimpse of how I write code when I'm comfortable with the language and techniques used.

* This assignment included a lot of frameworks and protocols I had never used/seen before, inlcluding: (django, amqp/rabbitMQ, multithreading in python, pika, celery). this made it much more difficult to get anywhere, especially without a mentor. I think I failed to find good resources to help me get on my way. The running on windos error I made cost me a lot of time. Due to this I feel like I did not fully show my skill in software engineering
