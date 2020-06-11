from django.db import models

from celery.utils.log import get_task_logger

logger = get_task_logger(__name__)



class SyncData(models.Model):
    id = models.UUIDField(primary_key=True)
    status = models.SmallIntegerField()
    created = models.DateTimeField()
    dataString0 = models.CharField(max_length=200)
    dataString1 = models.CharField(max_length=200)
    dataString2 = models.CharField(max_length=200)
    dataString3 = models.CharField(max_length=200)
    dataString4 = models.CharField(max_length=200)
    dataString5 = models.CharField(max_length=200)


class HistoryData(models.Model):
    syncData = models.ForeignKey(SyncData, on_delete=models.CASCADE)

    @classmethod
    def create(cls, syncData):
        return cls(syncData=SyncData(*syncData))


class SyncItem(models.Model):
    id = models.UUIDField(primary_key=True)
    grade = models.CharField(max_length=1)
    currentData = models.ForeignKey(SyncData, on_delete=models.CASCADE)
    historyData = models.ForeignKey(HistoryData, on_delete=models.CASCADE)

    @classmethod
    def create(cls, id, grade, currentData, historyData):
        logger.warn('{}'.format(currentData))
        return cls(id=id, grade=grade, currentData=SyncData(**currentData),
                   historyData=HistoryData.create(historyData))


class SyncBag(models.Model):
    id = models.UUIDField(primary_key=True)
    source = models.CharField(max_length=36)
    syncItem = models.ForeignKey(SyncItem, on_delete=models.CASCADE)

    @classmethod
    def create(cls, id, source, syncItem):
        return cls(id=id, source=source, syncItem=SyncItem.create(**syncItem))

