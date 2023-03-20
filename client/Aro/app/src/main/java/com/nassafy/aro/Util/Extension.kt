package com.nassafy.aro.util

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.material.snackbar.Snackbar

fun Context.showToastMessage(message: String) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.TOP, 0, 0)
    //toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL)
    toast.show()
} // End of showToastMessage

fun View.showSnackBarMessage(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
} // End of showSnackBarMessage

fun Polyline.addInfoWindow(map: GoogleMap, location: LatLng, title: String, message: String) {
//    val pointsOnLine = this.points.size
//    Log.d("sdr", "pointsOnLine: ${this.points}")
//    val infoLatLng = this.points[(pointsOnLine / 2)]
//    Log.d("sdr", "infoLatLng: $pointsOnLine")
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
    Log.d("Extension", "addInfoWindow: $location")
    marker?.showInfoWindow()
} // End of addInfoWindow

fun convertImageToBlurImage(context: Context?, image: Bitmap): Bitmap? {
    val BITMAP_SCALE = 0.1f
    val BLUR_RADIUS = 25f

    val width = Math.round(image.width * BITMAP_SCALE)
    val height = Math.round(image.height * BITMAP_SCALE)
    val inputBitmap = Bitmap.createScaledBitmap(image, width, height, false)
    val outputBitmap = Bitmap.createBitmap(inputBitmap)
    val rs: RenderScript = RenderScript.create(context)
    val intrinsicBlur: ScriptIntrinsicBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
    val tmpIn: Allocation = Allocation.createFromBitmap(rs, inputBitmap)
    val tmpOut: Allocation = Allocation.createFromBitmap(rs, outputBitmap)
    intrinsicBlur.setRadius(BLUR_RADIUS)
    intrinsicBlur.setInput(tmpIn)
    intrinsicBlur.forEach(tmpOut)
    tmpOut.copyTo(outputBitmap)
    return outputBitmap
}

fun setImageWithGrayScale(image: Bitmap?, imageView: ImageView): Int {
    //image is null
    image ?: return -1;

    //grayScale
    val matrix = ColorMatrix()
    matrix.setSaturation(0f)
    val filter = ColorMatrixColorFilter(matrix)
    imageView.colorFilter = filter

    imageView.setImageBitmap(image)
    return 0;
} // End of setImageWithGrayScale
