package com.nassafy.aro.UI.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.nassafy.aro.R
import com.nassafy.aro.databinding.ActivityMainBinding

private const val TAG = "MainActivity_sdr"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentcontainerview)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFragment, MainFragment())
                .commit()
        }
    }

}