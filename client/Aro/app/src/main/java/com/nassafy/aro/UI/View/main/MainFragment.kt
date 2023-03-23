package com.nassafy.aro.ui.view.main

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private lateinit var mContext : Context
    private var tabTitle: ArrayList<String> = arrayListOf()
    private var tabIcons: ArrayList<Drawable> = arrayListOf()

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
//        var tabIcon1 = layoutInflater.inflate(R.layout.tab_icon_custom_view, null)
//        tabIcon1.findViewById<ImageView>(R.id.icon).setBackgroundResource(R.drawable.tab_aurora_icon)
//        var tabIcon2 = layoutInflater.inflate(R.layout.tab_icon_custom_view, null)
//        tabIcon2.findViewById<ImageView>(R.id.icon).setBackgroundResource(R.drawable.tab_aurora_icon)

        tabIcons = arrayListOf(
            ContextCompat.getDrawable(requireContext(), R.drawable.tab_aurora_icon)!!,
            ContextCompat.getDrawable(requireContext(), R.drawable.aurora_icon)!!
        )

        TabLayoutMediator(binding.tablayout, binding.viewpager) { tab, position ->
            tab.text = tabTitle[position]
            tab.icon = tabIcons[position]
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