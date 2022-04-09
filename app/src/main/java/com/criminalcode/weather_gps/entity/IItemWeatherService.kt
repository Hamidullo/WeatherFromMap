package com.criminalcode.weather_gps.entity

import com.criminalcode.weather_gps.model.DataW
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface IItemWeatherService {

    @GET("data/2.5/weather?") //q={city}/units=metric/appid=87371da9baf6be746254d014e4eac004
    fun weatherData(@Query("q") city: String,@Query("units") metric: String,@Query("appid") app_id: String): Observable<DataW?>?

    @GET("data/2.5/weather?") //q={city}/units=metric/appid=87371da9baf6be746254d014e4eac004
    fun weatherDataByLocation(@Query("lat") lat: String,@Query("lon") lon: String,@Query("units") metric: String,@Query("appid") app_id: String): Observable<DataW?>?

}