from django.shortcuts import render
from django.http import HttpResponse


def dashboard(request):
    stats = "<html><h1>OK</h1></html>"

    return HttpResponse(stats)