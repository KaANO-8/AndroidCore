package com.kaano8.androidcore.com.kaano8.androidcore.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.kaano8.androidcore.R
import com.kaano8.androidcore.databinding.ActivityMainBinding
import com.kaano8.androidcore.service.MemoFragment
import com.kaano8.androidcore.service.ServiceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding

    private val serviceViewModel: ServiceViewModel by viewModels()

    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        setupActionBarWithNavController(navController = navHostFragment.navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
    }

    /**
     * Poor setup, will be constantly calling onPause - onResume every secs when broadcast is received
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val elapsedTime = intent?.getIntExtra(MemoFragment.NOTIFICATION_TEXT, 0) ?: 0
        serviceViewModel.updateElapsedTime(elapsedTime)
    }
}
