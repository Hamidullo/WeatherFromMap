package com.criminalcode.weather_gps

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.criminalcode.weather_gps.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener
import com.google.android.gms.maps.GoogleMap.OnPoiClickListener
import com.google.android.gms.maps.model.Marker
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import com.google.android.gms.maps.model.PointOfInterest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var check: Button
    private lateinit var searchView: SearchView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lat: String
    private lateinit var long: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        check = binding.find
        searchView = binding.searchLc
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                var location = searchView.query.toString()
                var addressList = arrayListOf<Address>()
                if (location != null || location != ""){
                    var geocoder = Geocoder(this@MapsActivity)
                    try {
                        addressList = geocoder.getFromLocationName(location,1) as ArrayList<Address>
                    } catch (e: IOException){
                        e.printStackTrace()
                    }
                    var address = addressList[0]
                    var latLng = LatLng(address.latitude,address.longitude)
                    lat = address.latitude.toString()
                    long = address.longitude.toString()
                    mMap.addMarker(MarkerOptions().position(latLng).title(location))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10F))
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })

        check.setOnClickListener {

            val intent = Intent(this,DataActivity::class.java)
            intent.putExtra("lat",lat)
            intent.putExtra("long",long)
            startActivity(intent)

        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location->
                    if (location != null) {
                        val sydney = LatLng(location.latitude,location.longitude)
                        lat = location.latitude.toString()
                        long = location.longitude.toString()
                        mMap.addMarker(MarkerOptions().position(sydney).title("My Location"))
                        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10F))

                    }
                }
        } else {
            // Add a marker in Sydney and move the camera
            val sydney = LatLng(41.31037009684337, 69.22373759673032)
            lat = sydney.latitude.toString()
            long = sydney.longitude.toString()
            mMap.addMarker(MarkerOptions().position(sydney).title("Tashkent"))
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10F))
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location->
                    if (location != null) {
                        val sydney = LatLng(location.latitude,location.longitude)
                        lat = location.latitude.toString()
                        long = location.longitude.toString()
                        mMap.addMarker(MarkerOptions().position(sydney).title("My Location"))
                        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10F))

                    }
                }
        } else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PackageManager.PERMISSION_GRANTED
            )
        }

        mMap.setOnMapClickListener {

            var markerOptions = MarkerOptions()
            markerOptions.position(it)
            markerOptions.title("New Location")
            mMap.clear()
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it,10F))
            mMap.addMarker(markerOptions)
            long = it.longitude.toString()
            lat = it.latitude.toString()
        }
    }
}