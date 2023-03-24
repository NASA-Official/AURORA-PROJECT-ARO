package com.nassafy.aro.ui.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.nassafy.aro.R
import com.nassafy.aro.data.dto.Place
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import java.lang.Exception

private const val TAG = "InfoRenderer_SDR"

class CustomMarkerInfoRenderer(private val layoutInflater: LayoutInflater, val context: Context) :
    GoogleMap.InfoWindowAdapter {

    private var infoWindow = layoutInflater.inflate(R.layout.map_info_window, null)
    private var lastMarker : Marker? = null

    override fun getInfoContents(marker: Marker): View? {
        return null
    } // End of getInfoContents

    override fun getInfoWindow(marker: Marker): View {
        if (lastMarker != marker) {
            lastMarker = marker
            val place: Place = marker.tag as Place

            val infoImageView = infoWindow.findViewById<ImageView>(R.id.map_info_imageview)
            val infoTextView = infoWindow.findViewById<TextView>(R.id.map_info_textview)


            infoImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.loading_spinner))
            infoTextView?.text = marker.snippet

            CoroutineScope(Dispatchers.Main).launch {
                val result: Deferred<Int> = async {
                    val picasso = Picasso.get()
                        .load(place.mapImage)
                        .resize(infoImageView!!.width, infoImageView.height)
                        .centerCrop()
                        .noFade()
                        .into(infoImageView, object : Callback {
                            override fun onSuccess() {
                                when {
                                    marker.isInfoWindowShown -> {
                                        infoImageView.visibility = View.VISIBLE
                                        marker.showInfoWindow()
                                    }
                                }
                            }
                            override fun onError(e: Exception?) {
                                Log.e(TAG, "onError: $e")
                            }
                        })
                    0
                }
                result.await()
            }
        }
        return infoWindow
    } // End of getInfoWindow

} // End of CustomMarkerInfoRenderer