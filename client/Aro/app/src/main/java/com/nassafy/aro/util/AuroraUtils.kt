package com.nassafy.aro.util

import android.graphics.Color
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.PatternItem
import com.nassafy.aro.R


private const val TAG = "AuroraUtils_sdr"
fun getKpPolylineOptions(kpIndex: Float) : PolylineOptions {
    val polylineOptions = PolylineOptions()
    when {
      kpIndex >= 9 -> {
          polylineOptions.add(
              LatLng(38.0, -80.0),
              LatLng(41.0, -60.0),
              LatLng(46.0,  -40.0),
              LatLng(50.0, -20.0),
              LatLng(52.0,0.0),
              LatLng(52.1, 20.0),
              LatLng(52.2, 40.0),
              LatLng(52.5, 60.0),
              LatLng(52.8, 80.0),
              LatLng(53.0, 100.0),
              LatLng(53.0,120.0),
              LatLng(54.5, 140.0),
              LatLng(54.2, 160.0),
              LatLng(53.2, 180.0),
              LatLng(50.2, -160.0),
              LatLng(48.0, -140.0),
              LatLng(42.5, -120.0),
              LatLng(39.0, -100.0),
              LatLng(38.0, -80.0)
          )
              .color(Color.RED)
              .width(8.0F)
              .clickable(true)
              .geodesic(true)
      }
      kpIndex >= 7 -> {
          polylineOptions.add(
              LatLng(42.0, -80.0),
              LatLng(45.0, -60.0),
              LatLng(50.0, -40.0),
              LatLng(53.0, -20.0),
              LatLng(55.0, 0.0),
              LatLng(56.0, 20.0),
              LatLng(56.2, 40.0),
              LatLng(56.5, 60.0),
              LatLng(56.8, 80.0),
              LatLng(57.0, 100.0),
              LatLng(57.5, 120.0),
              LatLng(58.5, 140.0),
              LatLng(58.5,160.0),
              LatLng(57.5, 180.0),
              LatLng(55.0, -160.0),
              LatLng(50.5, -140.0),
              LatLng(46.5, -120.0),
              LatLng(42.5, -100.0),
              LatLng(42.0, -80.0),
              )
              .color(Color.YELLOW)
              .width(8.0F)
              .clickable(true)
              .geodesic(true)
      }
      kpIndex >= 5 -> {
          polylineOptions.add(
              LatLng(46.0, -80.0),
              LatLng(49.1,-60.0),
              LatLng(53.0, -40.0),
              LatLng(57.0,-20.0),
              LatLng(59.0,0.0),
              LatLng(59.9, 20.0),
              LatLng(60.2, 40.0),
              LatLng(60.5,60.0),
              LatLng(60.8, 80.0),
              LatLng(61.0, 100.0),
              LatLng(61.5, 120.0),
              LatLng(62.5,140.0),
              LatLng(62.3, 160.0),
              LatLng(61.3, 180.0),
              LatLng(58.5, -160.0),
              LatLng(54.5, -140.0),
              LatLng(50.2, -120.0),
              LatLng(47.0, -100.0),
              LatLng(46.0, -80.0),
          )
              .color(Color.GREEN)
              .width(8.0F)
              .clickable(true)
              .geodesic(true)
      }
      else -> {
          polylineOptions
              .add(LatLng(50.1, -80.0))
              .add(LatLng(53.0,-60.0))
              .add(LatLng(57.0, -40.0))
              .add(LatLng(60.0,-20.0))
              .add(LatLng(62.5,0.0))
              .add(LatLng(63.7, 20.0))
              .add(LatLng(64.5, 40.0))
              .add(LatLng(64.5,60.0))
              .add(LatLng(64.8, 80.0))
              .add(LatLng(65.0, 100.0))
              .add(LatLng(65.4, 120.0))
              .add(LatLng(66.4, 140.0))
              .add(LatLng(66.3, 160.0))
              .add(LatLng(65.0, 180.0))
              .add(LatLng(62.8, -160.0))
              .add(LatLng(59.0, -140.0))
              .add(LatLng(54.6, -120.0))
              .add(LatLng(51.5, -100.0))
              .add(LatLng(50.1, -80.0))
              .color(Color.BLUE)
              .width(8.0F)
              .clickable(true)
              .geodesic(false)
      }
    }
    return polylineOptions
} // End of getKpPolylineOptions