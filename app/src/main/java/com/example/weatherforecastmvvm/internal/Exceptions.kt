package com.example.weatherforecastmvvm.internal

import java.io.IOException

class NoConnectivityException : IOException("No Internet Connection")
class LocationPermissionNotGrantedException: Exception()
class DateNotFoundException: Exception()