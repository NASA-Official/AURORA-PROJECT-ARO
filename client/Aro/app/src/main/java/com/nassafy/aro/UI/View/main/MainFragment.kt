package com.nassafy.aro.ui.view.main

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.nassafy.aro.R
import com.nassafy.aro.databinding.FragmentAuroraBinding
import com.nassafy.aro.databinding.FragmentMainBinding
import com.nassafy.aro.ui.TempFragment
import com.nassafy.aro.ui.view.BaseFragment
import com.nassafy.aro.ui.view.aurora.AuroraFragment
import java.time.LocalDateTime

private const val TAG = "MainFragment_sdr"

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private lateinit var mContext : Context
    private var tabTitle: ArrayList<String> = arrayListOf()
    private var tabIcon: ArrayList<Drawable> = arrayListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewpager.apply {
            adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
            isUserInputEnabled = false
        }
        // initialize tab layout
        initTabLayout()

    } // End of onViewCreated

    private fun initTabLayout() {
        // add tablayout title
        tabTitle = arrayListOf(
            getString(R.string.service_aurora),
            getString(R.string.service_meteor_shower)
        )
        // add tablayout Icon
        tabIcon = arrayListOf(
            ContextCompat.getDrawable(requireContext(), R.drawable.weather_snowy_icon)!!,
            ContextCompat.getDrawable(requireContext(), R.drawable.weather_sunny_icon)!!
        )

        TabLayoutMediator(binding.tablayout, binding.viewpager) { tab, position ->
            tab.text = tabTitle[position]
            tab.icon = tabIcon[position]
        }.attach()
    } // End of initTabLayout

    inner class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        override fun getItemCount(): Int {
            return tabTitle.size
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> AuroraFragment()
                // TODO: Meteor Shower Fragment
                else -> TempFragment()
            }
        }
    } // End of ViewPagerAdapter
}