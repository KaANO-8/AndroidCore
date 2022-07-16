package com.kaano8.androidcore.com.kaano8.androidcore.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kaano8.androidcore.databinding.ActivityMainBinding
import com.kaano8.androidcore.service.MemoFragment
import com.kaano8.androidcore.service.MemoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding

    private val memoViewModel: MemoViewModel by viewModels()

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
        Log.d(TAG, "onStart: height = ${binding.recyclerView.height}")

    }

    override fun onResume() {
        super.onResume()
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val elapsedTime = intent?.getIntExtra(MemoFragment.NOTIFICATION_TEXT, 0) ?: 0
        memoViewModel.updateElapsedTime(elapsedTime)
    }
}
