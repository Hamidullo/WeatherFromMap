package com.criminalcode.weather_gps.entity

import android.location.Location
import com.criminalcode.weather_gps.model.DataW

interface IItemWeather {
    fun getWeatherItem(city: String = "Tashkent"): rx.Observable<DataW?>?
    fun getWeatherItemByLocation(location: Location): rx.Observable<DataW?>?
}