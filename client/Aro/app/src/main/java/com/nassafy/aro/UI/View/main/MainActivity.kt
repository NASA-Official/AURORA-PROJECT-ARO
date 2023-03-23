package com.nassafy.aro.ui.view.main

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.nassafy.aro.R
import com.nassafy.aro.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragmentcontainerview) as NavHostFragment
        val navController = navHostFragment.navController

        binding.mainNavigation.setupWithNavController(navController)

        initDrawer()
    } // End of onCreate

    private fun initDrawer() {
        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            binding.mainDrawerlayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
            }
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                val nickname: TextView = findViewById<View>(R.id.nickname_textview) as TextView
                val email: TextView = findViewById<View>(R.id.email_textview) as TextView
                val closeButton: ImageButton = findViewById(R.id.close_button)

                // TODO : Add Nickname and Email from User Data
//                nickname.text = user.nickname
//                email.text = user.email

                closeButton.setOnClickListener {
                    closeDrawer()
                }
            }
        }
        binding.mainDrawerlayout.apply {
            setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            addDrawerListener(toggle)
        }
        toggle.syncState()
    } // End of initDrawer

    // Main Fragment에서 버튼을 누르면 Drawer Open
    fun openDrawer() {
        binding.mainDrawerlayout.openDrawer(GravityCompat.END)
    }

    fun closeDrawer() {
        binding.mainDrawerlayout.closeDrawer(GravityCompat.END)
    }

}