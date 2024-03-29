package com.nassafy.aro.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import retrofit2.Response

/**
 * ex) val layout = layoutInflater.inflate(R.layout.custom_toast, null)
    context?.showToastView(layout)
 */
fun Context.showToastView(toastView: View) {
    Toast(this).apply {
        duration = Toast.LENGTH_SHORT
        setGravity(Gravity.CENTER, 0, 0)
        view = toastView
    }.show()
} // End of showToastMessage

fun View.showSnackBarMessage(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
} // End of showSnackBarMessage

fun Polyline.addInfoWindow(map: GoogleMap, location: LatLng, title: String, message: String) {
    val invisibleMarker =
        BitmapDescriptorFactory.fromBitmap(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888))
    val marker = map.addMarker(
        MarkerOptions()
            .position(location)
            .title(title)
            .snippet(message)
            .alpha(0f)
            .icon(invisibleMarker)
            .anchor(0f, 0f)
    )
    marker?.showInfoWindow()
} // End of addInfoWindow

fun generateBitmapDescriptorFromRes(context: Context, resId: Int): BitmapDescriptor {
    val drawable = ContextCompat.getDrawable(context, resId)
    drawable!!.setBounds(
        0, 0,
        drawable.intrinsicWidth,
        drawable.intrinsicHeight
    )
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

/**
 * Code: 204의 경우는 처리하지 못합니다.
 */
suspend fun <T> MutableLiveData<NetworkResult<T>>.setNetworkResult(response: Response<T>) {
    this.postValue(NetworkResult.Loading())
    try {
        when {
            response.isSuccessful -> {
                this.postValue(
                    NetworkResult.Success(response.body()!!)
                )
            }
            response.errorBody() != null -> {
                this.postValue(NetworkResult.Error(response.errorBody()!!.string()))
            }
        } // End of when
    } catch (e: java.lang.Exception) {
        Log.e("ssafy", "getServerCallTest: ${e.message}")
    } // End of try-catch
} // End of selectService
