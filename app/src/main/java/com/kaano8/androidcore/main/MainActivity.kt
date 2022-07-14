package com.kaano8.androidcore.com.kaano8.androidcore.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kaano8.androidcore.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Log.d(TAG, "onCreate: called")
        Log.d(TAG, "onCreate: height = ${binding.recyclerView.height}")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
        Log.d(TAG, "onStart: height = ${binding.recyclerView.height}")

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
        Log.d(TAG, "onResume: height = ${binding.recyclerView.height}")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: ")
    }
}
