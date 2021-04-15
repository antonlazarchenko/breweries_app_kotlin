package com.alazar.breweries

import android.Manifest
import android.os.Bundle
import com.alazar.breweries.base.BaseActivity
import com.alazar.breweries.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        requirePermissions()

        supportFragmentManager.beginTransaction()
            .add(binding.frameLayout.id, BreweriesFragment())
            .commit()
    }


    private fun requirePermissions() {
        requestMultiplePermissions.launch(
            arrayOf(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
            )
        )
    }
}