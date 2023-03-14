package com.nassafy.aro.ui.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.nassafy.aro.R
import com.nassafy.aro.ui.view.stamp.CountryPlaceViewPagerAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var countryPlaceAdapter: CountryPlaceViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countryPlaceAdapter = CountryPlaceViewPagerAdapter(this)
        hideStatusBar()
    }

    private fun hideStatusBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    } // End of hideStatusBar
}