package com.nassafy.aro.ui.view.main

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.nassafy.aro.R
import com.nassafy.aro.databinding.ActivityMainBinding

private const val TAG = "MainActivity_sdr"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 임시 권한 성공하면 initView()
        requestPermission()

    }

    private fun initView() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentcontainerview)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFragment, MainFragment())
                .commit()
        }
    }

    // 임시 권한 체크
    private fun requestPermission() {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    initView()
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(
                        this@MainActivity,
                        "권한을 허가해주세요",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
            .setDeniedMessage("권한을 허용해주세요.")
            .setPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .check()
    }

}