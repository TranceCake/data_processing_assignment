from django.db import models


class SyncBag(models.Model):
    id = models.UUIDField(primary_key=True)
    source = models.CharField(max_length=36)


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


class SyncItem(models.Model):
    id = models.UUIDField(primary_key=True)
    syncBag = models.ForeignKey(SyncBag, on_delete=models.CASCADE)
    grade = models.CharField(max_length=1)
    currentData = models.ForeignKey(SyncData, on_delete=models.CASCADE)


class HistoryData(models.Model):
    syncItem = models.ForeignKey(SyncItem, on_delete=models.CASCADE)
    syncData = models.ForeignKey(SyncData, on_delete=models.CASCADE)

