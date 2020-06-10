from django.shortcuts import render
from django.http import HttpResponse
from worker.models import SyncBag
import logging

logger = logging.getLogger(__name__)


def dashboard(request):
    result = SyncBag.objects.all()

    logger.debug('test {0!r}'.format(result))
    return HttpResponse('results: {0!r}'.format(result))
