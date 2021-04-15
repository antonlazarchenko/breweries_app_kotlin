package com.alazar.breweries.base

import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.alazar.breweries.BreweriesFragment

open class BaseActivity : AppCompatActivity(){

    companion object {
        private val TAG = BreweriesFragment::class.simpleName
    }

    protected val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                Log.e(TAG, "${it.key} == ${it.value}")
            }
        }
}