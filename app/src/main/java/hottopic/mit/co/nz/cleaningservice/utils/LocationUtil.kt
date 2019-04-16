package hottopic.mit.co.nz.cleaningservice.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log

import java.io.IOException
import java.util.Locale


object LocationUtil {
    private var locationListener: LocationListener = object : LocationListener {
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

        }

        override fun onProviderEnabled(provider: String) {

        }

        override fun onProviderDisabled(provider: String) {

        }

        override fun onLocationChanged(location: Location) {
            Log.i("ABC", "Changed")
        }
    }

    /**
     * Get the lng and lat
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    fun getMyLocationAddress(context: Context): String {
        val location: Location?
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location == null) {
                return getAddress(context, getLngAndLatWithNetwork(context))
            }
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0f, locationListener)
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        }
        return getAddress(context, location!!)
    }


    @SuppressLint("MissingPermission")
    private fun getLngAndLatWithNetwork(context: Context): Location {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0f, locationListener)
        return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
    }

    private fun getAddress(context: Context, location: Location): String {
        var currentPosition = ""
        val gc = Geocoder(context, Locale.ENGLISH)
        try {
            val locationList = gc.getFromLocation(location.latitude, location.longitude, 1)

            if (locationList != null) {
                val address = locationList[0]
                currentPosition = address.featureName
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return currentPosition
    }
}
