package com.nassafy.aro.ui.view.main.stamp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.exifinterface.media.ExifInterface
import com.nassafy.aro.databinding.FragmentStampValidateBinding
import com.nassafy.aro.ui.view.BaseFragment
import com.nassafy.aro.util.ChangeMultipartUtil
import com.nassafy.aro.util.showSnackBarMessage
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.util.*

private const val TAG = "StampValidateFragment_싸피"

class StampValidateFragment :
    BaseFragment<FragmentStampValidateBinding>(FragmentStampValidateBinding::inflate) {
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    } // End of onAttach

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //val filePhoto = PhotoFil

        // 사진 찍어서 가져오기
        binding.stampValidateCameraButton.setOnClickListener {

        }

        // 갤러리에서 가져온 사진.
        binding.stampValidateGalleryButton.setOnClickListener {
            selectGallery()
        }

        // 촬영한 이미지의 결과를 가져옴
    } // End of onViewCreated


    private fun selectGallery() {
//        val writePermission = ContextCompat.checkSelfPermission(
//            mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//        )
//        val readPermission = ContextCompat.checkSelfPermission(
//            mContext, android.Manifest.permission.READ_EXTERNAL_STORAGE
//        )
//
//        if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) {
//            ActivityCompat.requestPermissions(
//                mContext as Activity, arrayOf(
//                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    android.Manifest.permission.READ_EXTERNAL_STORAGE
//                ), REQ_GALLERY
//            )
//        } else {
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.setDataAndType(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*"
//            )
//            imageResult.launch(intent)
//        }

        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*"
        )
        imageResult.launch(intent)
    } // End of selectGallery

    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // 가져온 이미지가 있을 경우 해당 데이터를 불러옴.
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data ?: return@registerForActivityResult

            val file = File(
                ChangeMultipartUtil().changeAbsoluteyPath(imageUri, requireActivity())
            )

            // 이미지 띄우기
            Picasso.get().load(imageUri).fit().centerCrop().into(binding.stampValidateImageview)


            val exif: ExifInterface = ExifInterface(file)
            val lat = exif.latLong?.get(0)!!
            val lng = exif.latLong?.get(1)!!

            CoroutineScope(Dispatchers.IO).launch {
                locationCarc(lat, lng)
            }


            binding.imageMetadataTextview.text = "Lat : ${lat}, Lon : ${lng}"
            // 이미지의 메타데이터에서 위도, 경도를 가져와서 해당 위치의 오차범위를 계산해서 옳은지 판단.


//            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
//            val body =
//                MultipartBody.Part.createFormData("newImageList", file.name, requestFile)
        }
    } // End of registerForActivityResult

    private suspend fun locationCarc(nowLat: Double, nowLng: Double) {

        var address: String? = null
        val job = CoroutineScope(Dispatchers.Default).async {
            val getAdd = getAddressByCoordinates(nowLat, nowLng)
            if (getAdd != null) {
                address = getAdd.getAddressLine(0)
            }
        }

        job.join()

        Log.d(TAG, "address : ${address}")

    } // End of getAddress

    private fun getAddressByCoordinates(latitude: Double, longitude: Double): Address? {
        val geocoder = Geocoder(mContext, Locale.KOREA)

        val addresses: List<Address>?

        addresses = try {
            geocoder.getFromLocation(latitude, longitude, 7)
        } catch (ioException: IOException) {
            binding.root.showSnackBarMessage("지오코더 서비스 사용불가")
            ioException.printStackTrace()
            return null
        } catch (illegalArgumentException: java.lang.IllegalArgumentException) {
            illegalArgumentException.printStackTrace()
            binding.root.showSnackBarMessage("잘못된 위도 경도 입니다.")
            return null
        }

        if (addresses == null || addresses.isEmpty()) {
            binding.root.showSnackBarMessage("주소가 발견되지 않았습니다.")
            return null
        }

        val address: Address = addresses[0]
        return address
    } // End of getAddressByCoordinates


    companion object {
        private lateinit var photoPath: String
        private val INTENT_CODE = 1
        private val PERMISSION_CODE = 2
    }

} // End of StampValidateFragment class
