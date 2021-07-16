package com.example.climachallenge.common

class Constants {
    companion object {
        public val OPENWEATHER_API_BASE_URL = "https://api.openweathermap.org/data/2.5/"

        public val VALUE_LAT = "-34.90" // latitude
        public val VALUE_LON = "-56.17" // longitude
        public val VALUE_EXCLUDE = "hourly,minutely"
        public val VALUE_UNITS = "metric"
        public val OPENWEATHER_API_KEY = "0ef07f6b9a710e651a88f0de691fbdb3"

        public val URL_PARAM_LAT = "lat" // latitude
        public val URL_PARAM_LON = "lon" // longitude
        public val URL_PARAM_EXCLUDE = "exclude"
        public val URL_PARAM_UNITS = "units"
        public val URL_PARAM_OPENWEATHER_API_KEY = "appid"
    }
}