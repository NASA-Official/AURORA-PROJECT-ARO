package com.nassafy.aro.ui.view.aurora

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.maps.android.ktx.awaitMap
import com.nassafy.aro.BuildConfig
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentAuroraBinding
import com.nassafy.aro.ui.view.dialog.DateHourSelectDialog
import com.nassafy.aro.ui.view.main.MainActivity
import com.nassafy.aro.util.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val TAG = "AuroraFragment_sdr"

class AuroraFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentAuroraBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleMap: GoogleMap
    private var now = LocalDateTime.now()
    private var dateList = arrayListOf<String>()
    private var hourList = arrayListOf<ArrayList<String>>()
    private var clickedLocation = LatLng(0.0, 0.0)
    private lateinit var bottomSheet : CardView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuroraBinding.inflate(inflater, container, false)
        return binding.root
    } // End of onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val mapFragment: SupportMapFragment =
//            childFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//        initView()
        bottomSheet = binding.bottomSheet
        val cornerRadius = bottomSheet.radius

        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        bottomSheetBehavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // 상태가 변함에 따라서 할일들을 적어줍니다.
                if (newState == STATE_EXPANDED) {
                    // TODO; 내용을 보여주기 위해 fragment 붙이기...
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // slideOffset 접힘 -> 펼쳐짐: 0.0 ~ 1.0
                if (slideOffset >= 0) {
                    // 둥글기는 펼칠수록 줄어들도록
//                    binding.bottomSheet.radius = cornerRadius - (cornerRadius * slideOffset)
                    // 화살표는 완전히 펼치면 180도 돌아가게
                    binding.guideline1.rotation = (1 - slideOffset) * 180F
                    // 글자는 조금더 빨리 사라지도록
                    binding.guideline2.alpha = 1 - slideOffset * 2.3F
                    // 내용의 투명도도 같이 조절...
                    binding.fragment.alpha = Math.min(slideOffset * 2F, 1F)
                }
            }

        })
    } // End of onViewCreated

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
//        setCustomMapStyle()
//        var kpIndex = 3.0F
//        val polylineOptions = getKpPolylineOptions(kpIndex)
//        googleMap.addPolyline(polylineOptions)
//        googleMap.setOnMapClickListener {
//            // TODO: setClickedLocation using viewModel
//            clickedLocation = it
//        }
//        googleMap.setOnPolylineClickListener {
//            it.addInfoWindow(googleMap, clickedLocation, "KP 지수 $kpIndex", "")
//        }
    } // End of onMapReady

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    } // End of onDestroyView


//    private fun initView() {
//        dateList = getDateList(now)
//        hourList = getHourList(dateList, now)
//
//        binding.drawerImagebutton.setOnClickListener {
//            val mainActivity = activity as MainActivity
//            mainActivity.openDrawer()
//        }
//
//        binding.dateTextview.text = LocalDate.of(now.year, now.month, now.dayOfMonth).format(
//            DateTimeFormatter.ofPattern("yy/MM/dd")
//        )
//
//        binding.hourTextview.text =
//            LocalDateTime.of(now.year, now.month, now.dayOfMonth, now.hour, 0).format(
//                DateTimeFormatter.ofPattern("HH:mm")
//            )
//
//        binding.dateHourLinearlayout.setOnClickListener {
//            val dateHourSelectDialog = DateHourSelectDialog(dateList, hourList)
//            dateHourSelectDialog.show(
//                childFragmentManager, "DateHourSelectDialog"
//            )
//        }
//    }
//
//
//    fun setDateTimeLinearLayoutText(date: String, hour: String) {
//        binding.dateTextview.text = date
//        binding.hourTextview.text = hour
//    }
//
//    private fun setCustomMapStyle() {
//        try {
//            // Customise the styling of the base map using a JSON object defined
//            // in a raw resource file.
//            val success = googleMap.setMapStyle(
//                MapStyleOptions.loadRawResourceStyle(
//                    requireContext(), R.raw.map_night_style
//                )
//            )
//            if (!success) {
//                Log.e(TAG, "Style parsing failed.")
//            }
//        } catch (e: Resources.NotFoundException) {
//            Log.e(TAG, "Can't find style. Error: ", e)
//        }
//    } // End of setCustomMapStyle

}