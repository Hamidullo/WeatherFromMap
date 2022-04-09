package com.criminalcode.weather_gps

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.criminalcode.weather_gps.entity.ItemWeather
import com.criminalcode.weather_gps.model.DataW
import com.criminalcode.weather_gps.presenter.NetworkHelper
import com.squareup.picasso.Picasso
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class DataActivity : AppCompatActivity() {

    private lateinit var networkHelper: NetworkHelper
    private lateinit var weatherItem: ItemWeather
    private lateinit var data: DataW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        weatherItem = ItemWeather()

        val lat = intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")

        networkHelper = NetworkHelper(this)
        if (networkHelper.isNetworkConnected()){

            if (lat != null && long != null) {
                location(lat,long)
            }

        }

        /*if(data != null){
            setView()
        }*/

    }

    private fun setView(){

        findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE

        findViewById<TextView>(R.id.address).text = data.name + ", " + data.sys.country

        //city = data.name.toString().split(" ")[0]
        findViewById<TextView>(R.id.updated_at).text =  SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH)
            .format(Date(data.dt.toLong() * 1000))
        findViewById<TextView>(R.id.status).text = data.weather[0].description.capitalize()
        findViewById<TextView>(R.id.temp).text = data.main.temp.toString() + "°C"
        findViewById<TextView>(R.id.temp_min).text = data.main.tempMin.toString() + "°C"
        findViewById<TextView>(R.id.temp_max).text = data.main.tempMax.toString() + "°C"
        findViewById<TextView>(R.id.sunrise).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
            .format(Date(data.sys.sunrise.toLong() * 1000))
        findViewById<TextView>(R.id.sunset).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
            .format(Date(data.sys.sunset.toLong() * 1000))
        findViewById<TextView>(R.id.wind).text = data.wind.speed.toString()
        findViewById<TextView>(R.id.pressure).text = data.main.pressure.toString()
        findViewById<TextView>(R.id.humidity).text = data.main.humidity.toString()

        val iconUrl = "http://openweathermap.org/img/wn/" + data.weather[0].icon + "@2x.png"
        //  http://openweathermap.org/img/wn/01d@2x.png

        Picasso.with(this).load(iconUrl).error(R.drawable.sunrise).into(findViewById<ImageView>(R.id.weatherIcon))


    }

    private fun city(city: String){

        val dataObservable = weatherItem.getWeatherItem(city)

        dataObservable!!.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { weatherItemData: DataW? ->

                println(weatherItemData)

                if (weatherItemData != null) {
                    data = weatherItemData
                }
            }

    }


    private fun location(lat: String,long: String){
        val dataObservable = weatherItem.getWeatherItemByLocation(lat,long)

        dataObservable!!.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { weatherItemData: DataW? ->

                println(weatherItemData)

                if (weatherItemData != null) {
                    data = weatherItemData


                    findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE

                    findViewById<TextView>(R.id.address).text = data.name + ", " + data.sys.country

                    //city = data.name.toString().split(" ")[0]
                    findViewById<TextView>(R.id.updated_at).text =  SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH)
                        .format(Date(data.dt.toLong() * 1000))
                    findViewById<TextView>(R.id.status).text = data.weather[0].description.capitalize()
                    findViewById<TextView>(R.id.temp).text = data.main.temp.toString() + "°C"
                    findViewById<TextView>(R.id.temp_min).text = data.main.tempMin.toString() + "°C"
                    findViewById<TextView>(R.id.temp_max).text = data.main.tempMax.toString() + "°C"
                    findViewById<TextView>(R.id.sunrise).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                        .format(Date(data.sys.sunrise.toLong() * 1000))
                    findViewById<TextView>(R.id.sunset).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                        .format(Date(data.sys.sunset.toLong() * 1000))
                    findViewById<TextView>(R.id.wind).text = data.wind.speed.toString()
                    findViewById<TextView>(R.id.pressure).text = data.main.pressure.toString()
                    findViewById<TextView>(R.id.humidity).text = data.main.humidity.toString()

                    val iconUrl = "http://openweathermap.org/img/wn/" + data.weather[0].icon + "@2x.png"
                    //  http://openweathermap.org/img/wn/03d@2x.png
                    println(iconUrl)

                    Picasso.with(this).load(iconUrl).into(findViewById<ImageView>(R.id.weatherIcon))

                }

            }
    }
}