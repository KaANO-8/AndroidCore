package com.kaano8.androidcore.com.kaano8.androidcore.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.kaano8.androidcore.R
import com.kaano8.androidcore.com.kaano8.androidcore.workmanager.workers.SuggestionWorker
import com.kaano8.androidcore.com.kaano8.androidcore.workmanager.workers.SuggestionWorker.Companion.NEW_WORD
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
        scheduleWork()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun scheduleWork() {
        val workRequest = OneTimeWorkRequestBuilder<SuggestionWorker>().build()
        WorkManager.getInstance(applicationContext).enqueueUniqueWork("SuggestionWork", ExistingWorkPolicy.KEEP, workRequest)
        WorkManager.getInstance(applicationContext).getWorkInfosForUniqueWorkLiveData("SuggestionWork").observe(this) {
            Log.d(TAG, "scheduleWork: ${it.first().outputData.getString(NEW_WORD)}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: called")
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
