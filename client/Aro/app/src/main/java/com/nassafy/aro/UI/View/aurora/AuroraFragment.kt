package com.nassafy.aro.ui.view.aurora

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.maps.android.PolyUtil
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentAuroraBinding
import com.nassafy.aro.ui.adapter.BottomSheetFavoriteAdapter
import com.nassafy.aro.ui.view.BaseFragment
import com.nassafy.aro.ui.view.dialog.DateHourSelectDialog
import com.nassafy.aro.ui.view.main.MainActivity
import com.nassafy.aro.util.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


private const val TAG = "AuroraFragment_sdr"

class AuroraFragment : BaseFragment<FragmentAuroraBinding>(FragmentAuroraBinding::inflate),
    OnMapReadyCallback {
    private val auroraViewModel: AuroraViewModel by viewModels()
    private lateinit var googleMap: GoogleMap
    private lateinit var favoriteAdapter: BottomSheetFavoriteAdapter
    private var now = LocalDateTime.now()
    private var dateList = arrayListOf<String>()
    private var hourList = arrayListOf<ArrayList<String>>()
    var kpIndex = 3.0F

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment: SupportMapFragment =
            childFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment
        mapFragment.getMapAsync(this)

//        auroraViewModel.clickedLocation.observe(viewLifecycleOwner) { latLng ->
//            googleMap.setOnPolylineClickListener {
//
//            }
//        }
        initView()

        initBottomSheetRecyclerView()

    } // End of onViewCreated

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
        googleMap.uiSettings.isMapToolbarEnabled = false
        setCustomMapStyle()
        val polylineOptions = getKpPolylineOptions(kpIndex)
        val polyline = googleMap.addPolyline(polylineOptions)

        googleMap.setOnMapClickListener { latLng ->
            auroraViewModel.setClickedLocation(latLng)

            // When Clicked Location is on Polyline, Google Map shows Info.
            val tolerance = getKpPolylineTolerance(googleMap.cameraPosition.zoom)
            if (PolyUtil.isLocationOnPath(latLng, polylineOptions.points, true, tolerance)) {
                polyline.addInfoWindow(googleMap, latLng, "KP 지수", "$kpIndex")
            }
        } // End of setOnMapClickListener

    } // End of onMapReady

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
    } // End of initView

    private fun initBottomSheetRecyclerView() {
        favoriteAdapter = BottomSheetFavoriteAdapter(itemList)

        val dividerItemDecoration = DividerItemDecoration(
            requireContext(),
            LinearLayoutManager(requireContext()).orientation
        )
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.line_divider)!!)

        val behavior = BottomSheetBehavior.from(binding.bottomSheet.root)

        binding.bottomSheet.favoriteRecyclerview.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(dividerItemDecoration)
            addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        when (newState) {
                            RecyclerView.SCROLL_STATE_IDLE -> {
                                behavior.isDraggable = true
                            }
                            RecyclerView.SCROLL_STATE_DRAGGING -> {
                                behavior.isDraggable = false
                            }
                            RecyclerView.SCROLL_STATE_SETTLING -> {
                                behavior.isDraggable = false
                            }
                        }
                        super.onScrollStateChanged(recyclerView, newState)
                    } // End of onScrollStateChanged
                })
        }

    } // End of initBottomSheetRecyclerView


    fun setDateTimeLinearLayoutText(date: String, hour: String) {
        binding.dateTextview.text = date
        binding.hourTextview.text = hour
    } // End of setDateTimeLinearLayoutText

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


    companion object {
        var item1 = arrayListOf<String>("그리핀도르", "88", "Thunderstorm")
        var item2 = arrayListOf<String>("슬리데린", "40", "Drizzle")
        var item3 = arrayListOf<String>("레벤클로", "20", "Rain")
        var item4 = arrayListOf<String>("후플프푸", "10", "Snow")
        var item5 = arrayListOf<String>("레이캬비크", "50", "Atmosphere")
        var item6 = arrayListOf<String>("신도림", "49", "Clear")
        var item7 = arrayListOf<String>("구미", "100", "Clouds")
        var itemList =
            arrayListOf<MutableList<String>>(item1, item2, item3, item4, item5, item6, item7)
    }

} // End of AuroraFragment