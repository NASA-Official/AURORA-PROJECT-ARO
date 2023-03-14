package com.nassafy.aro.ui.view.aurora

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentAuroraBinding

private const val TAG = "AuroraFragment_sdr"
class AuroraFragment : Fragment(), OnMapReadyCallback  {
    private var _binding: FragmentAuroraBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuroraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_view) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}