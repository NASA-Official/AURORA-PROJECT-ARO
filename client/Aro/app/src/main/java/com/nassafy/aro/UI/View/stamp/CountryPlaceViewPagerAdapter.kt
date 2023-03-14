package com.nassafy.aro.ui.view.stamp

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class CountryPlaceViewPagerAdapter(fm : FragmentActivity) : FragmentStateAdapter(fm) { // End of CountryPlaceViewPagerAdapter
    // End of CountryPlaceViewPagerAdapter
    override fun getItemCount(): Int  = Int.MAX_VALUE

    override fun createFragment(position: Int): StampHomeFragment {
        val country = getItemId(position)
        return StampHomeFragment.newInstance("sdff")
    }


    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }



    override fun containsItem(itemId: Long): Boolean {
        return super.containsItem(itemId)
    }

    companion object {
        const val START_POSITION = Int.MAX_VALUE / 2
    }

}