package com.nassafy.aro.ui.view

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.nassafy.aro.R
import com.nassafy.aro.data.dto.Place
import com.nassafy.aro.util.generateBitmapDescriptorFromRes

class CustomMarkerRenderer(
    context: Context, googleMap: GoogleMap, clusterManager: ClusterManager<Place>) :
    DefaultClusterRenderer<Place>(context, googleMap, clusterManager) {

    private val customMarker = generateBitmapDescriptorFromRes(context, R.drawable.map_marker)


    override fun onBeforeClusterItemRendered(item: Place, markerOptions: MarkerOptions) {
        markerOptions
            .position(item.position)
            .title(item.title)
            .icon(customMarker)

        super.onBeforeClusterItemRendered(item, markerOptions)
    }

    override fun onClusterItemRendered(item: Place, marker: Marker) {
        super.onClusterItemRendered(item, marker)
        marker.tag = item
    }
}