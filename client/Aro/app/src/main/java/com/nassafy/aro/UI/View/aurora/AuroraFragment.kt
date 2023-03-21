package com.nassafy.aro.ui.view.aurora

import android.content.res.Resources
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.PolyUtil
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentAuroraBinding
import com.nassafy.aro.ui.view.dialog.DateHourSelectDialog
import com.nassafy.aro.ui.view.main.MainActivity
import com.nassafy.aro.util.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.sqrt

private const val TAG = "AuroraFragment_sdr"

class AuroraFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentAuroraBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleMap: GoogleMap
    private var now = LocalDateTime.now()
    private var dateList = arrayListOf<String>()
    private var hourList = arrayListOf<ArrayList<String>>()
    private val auroraViewModel: AuroraViewModel by viewModels()
    var kpIndex = 3.0F

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
        val mapFragment: SupportMapFragment =
            childFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment
        mapFragment.getMapAsync {
            googleMap = it
            setCustomMapStyle()
            val polylineOptions = getKpPolylineOptions(kpIndex)
            val polyline = googleMap.addPolyline(polylineOptions)
            googleMap.setOnMapClickListener { latLng ->
                Log.d(TAG, "onViewCreated: 1")
                auroraViewModel.setClickedLocation(latLng)
                Log.d(TAG, "onViewCreated: here")
                if (PolyUtil.isLocationOnPath(latLng, polylineOptions.points, true, 90000000.0)) {
                    Log.d(TAG, "onViewCreated: yes")
                    polyline.addInfoWindow(googleMap, latLng, "KP 지수", "$kpIndex")
                }


            }
        }

        auroraViewModel.clickedLocation.observe(viewLifecycleOwner) { latLng ->
            googleMap.setOnPolylineClickListener {

                auroraViewModel.polylineClickAction(it, googleMap, latLng, kpIndex)

            }
        }

        initView()
    } // End of onViewCreated

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap

    } // End of onMapReady

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    } // End of onDestroyView


    private fun initView() {
        dateList = getDateList(now)
        hourList = getHourList(dateList, now)

        binding.drawerImagebutton.setOnClickListener {
            val mainActivity = activity as MainActivity
            mainActivity.openDrawer()
        }

        binding.dateTextview.text = LocalDate.of(now.year, now.month, now.dayOfMonth).format(
            DateTimeFormatter.ofPattern("yy/MM/dd")
        )

        binding.hourTextview.text =
            LocalDateTime.of(now.year, now.month, now.dayOfMonth, now.hour, 0).format(
                DateTimeFormatter.ofPattern("HH:mm")
            )

        binding.dateHourLinearlayout.setOnClickListener {
            val dateHourSelectDialog = DateHourSelectDialog(dateList, hourList)
            dateHourSelectDialog.show(
                childFragmentManager, "DateHourSelectDialog"
            )
        }
    }


    fun setDateTimeLinearLayoutText(date: String, hour: String) {
        binding.dateTextview.text = date
        binding.hourTextview.text = hour
    }

    private fun setCustomMapStyle() {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireContext(), R.raw.map_night_style
                )
            )
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    } // End of setCustomMapStyle

}