package hottopic.mit.co.nz.cleaningservice.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import hottopic.mit.co.nz.cleaningservice.entities.map.PlaceInfo
import hottopic.mit.co.nz.cleaningservice.entities.map.Viewport


object MapUtil {

    fun addMark(map: GoogleMap, placeInfo: PlaceInfo): GoogleMap {
        map.addMarker(MarkerOptions().position(placeInfo.geometry.location.location).title(placeInfo.formatted_address))
        return map
    }

    fun displayArea(map: GoogleMap, context: Context, bound: Viewport, padding: Int): GoogleMap {
        val bounds = LatLngBounds.Builder().include(bound.northeast.location).include(bound.southwest.location).build()
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, GeneralUtil.dip2px(context, padding)))
        return map
    }

    fun intentToGoogleMap(context: Context, location: String) {
        val gmmIntentUri = Uri.parse("google.navigation:q=$location&travelmode=driving")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        context.startActivity(mapIntent)
    }

    /**
     * Method to decode polyline points
     */
   /* private fun decodePoly(encoded: String): List<LatLng> {

        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val p = LatLng(lat.toDouble() / 1E5,
                    lng.toDouble() / 1E5)
            poly.add(p)
        }

        return poly
    }*/
}
