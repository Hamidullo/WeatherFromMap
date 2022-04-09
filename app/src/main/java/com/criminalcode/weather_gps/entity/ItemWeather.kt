package com.criminalcode.weather_gps.entity

import android.location.Location
import com.criminalcode.weather_gps.model.DataW
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable

class ItemWeather : IItemWeather {
    override fun getWeatherItem(city: String): Observable<DataW?>? {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org/")
                .build()

        val weatherService = retrofit.create(IItemWeatherService::class.java)

        return weatherService.weatherData(city,"metric","87371da9baf6be746254d014e4eac004")
    }

    override fun getWeatherItemByLocation(location: Location): Observable<DataW?>? {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/")
            .build()

        val weatherService = retrofit.create(IItemWeatherService::class.java)

        return weatherService.weatherDataByLocation(location.latitude.toString(),location.longitude.toString(),"metric","87371da9baf6be746254d014e4eac004")

    }

    fun getWeatherItemByLocation(lat:String,long: String): Observable<DataW?>? {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org/")
                .build()

        val weatherService = retrofit.create(IItemWeatherService::class.java)

        return weatherService.weatherDataByLocation(lat,long,"metric","87371da9baf6be746254d014e4eac004")
    }
}