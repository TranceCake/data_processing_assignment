import json

from django.http import HttpResponse

from worker.models import SyncBag, SyncData, HistoryData, SyncItem

# Attempted to show how I would do a deduplication without the amqp and multitheading I could not get to work.
# but it seems the migrations are not cooperating...

db_item = '{"id":"676f978e-6a23-4fbc-91fd-efcb262b9c34","source":"b8b4f86a-179d-406e-a579-6f2ef3337f2d","syncItem":{"id":"bbec835a-eef2-4dbc-a993-59bd0ed5ff80","grade":"B","currentData":{"id":"d11fb58d-ff9a-421a-bd4a-259e2566640b","status":"0","created":"2020-06-09T12:11:28+00:00","dataString0":"http://banana.com/file.html","dataString1":"2020-06-02T12:11:28+00:00","dataString2":"XML","dataString3":"1172EL","dataString4":"","dataString5":""},"historyData":[]}}'
donation_item = '{"id":"676f978e-6a23-4fbc-91fd-efcb262b9c34","source":"b8b4f86a-179d-406e-a579-6f2ef3337f2d","syncItem":{"id":"bbec835a-eef2-4dbc-a993-59bd0ed5ff80","grade":"B","currentData":{"id":"d11fb58d-ff9a-421a-bd4a-259e2566640b","status":"0","created":"2020-06-09T12:11:28+00:00","dataString0":"http://banana.com/file.html","dataString1":"2020-06-02T12:11:28+00:00","dataString2":"XML","dataString3":"1172EL","dataString4":"14","dataString5":""},"historyData":[]}}'


def merge(request):
    data = json.loads(db_item)
    sync_bag = SyncBag.create(**data)
    sync_bag.save()
    print(sync_bag.syncItem.grade)

    return HttpResponse(SyncBag.objects.all())
