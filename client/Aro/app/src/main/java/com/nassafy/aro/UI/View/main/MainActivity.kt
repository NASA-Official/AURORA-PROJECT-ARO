package com.nassafy.aro.ui.view.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.nassafy.aro.R
import com.nassafy.aro.databinding.ActivityMainBinding


private const val TAG = "MainActivity_sdr"

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 임시 권한 성공하면 initView()
//        requestPermission()
        initView()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mypage_item -> {
                Toast.makeText(this, "${item.title}", Toast.LENGTH_SHORT).show()
            }
            R.id.stamp_item -> {
                Toast.makeText(this, "${item.title}", Toast.LENGTH_SHORT).show()
            }
            R.id.setting_item -> {
                Toast.makeText(this, "${item.title}", Toast.LENGTH_SHORT).show()
            }
        }

        closeDrawer()
        return true
    }


    private fun initView() {
        // initialize navigation view
        binding.mainNavigation.setNavigationItemSelectedListener(this@MainActivity)

        // initialize drawer view
        initDrawer()

        // initialize fragment container view
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentcontainerview)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFragment, MainFragment())
                .commit()
        }
    }

    // 임시 권한 체크
//    private fun requestPermission() {
//        TedPermission.create()
//            .setPermissionListener(object : PermissionListener {
//                override fun onPermissionGranted() {
//                    initView()
//                }
//
//                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
//                    Toast.makeText(
//                        this@MainActivity,
//                        "권한을 허가해주세요",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            })
//            .setDeniedMessage("권한을 허용해주세요.")
//            .setPermissions(
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.INTERNET,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            )
//            .check()
//    }

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
    }

    // Main Fragment에서 FAB를 누르면 Drawer Open
    fun openDrawer() {
        binding.mainDrawerlayout.openDrawer(GravityCompat.END)
    }

    fun closeDrawer() {
        binding.mainDrawerlayout.closeDrawer(GravityCompat.END)
    }

    private fun hideStatusBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    } // End of hideStatusBar
}