package com.nassafy.aro.ui.view.main.stamp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class StampCountryPlacePagerFragmentStateAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) { // End of StampCountryPlacePagerFragmentStateAdapter

    var fragments: ArrayList<Fragment> = ArrayList()
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    } // End of createFragment

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
        notifyItemInserted(fragments.size - 1)
    } // End of addFragment
    fun removeFragment() {
        fragments.removeLast()
        notifyItemRemoved(fragments.size)
    } // End of removeFragment
} // End of StampCountryPlacePagerFragmentStateAdapter