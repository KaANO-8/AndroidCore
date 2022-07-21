package com.kaano8.androidcore.service

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.kaano8.androidcore.com.kaano8.androidcore.extensions.secondsToTime
import com.kaano8.androidcore.com.kaano8.androidcore.service.model.TimerState
import com.kaano8.androidcore.com.kaano8.androidcore.service.progressservice.ProgressService
import com.kaano8.androidcore.com.kaano8.androidcore.service.receiver.TimerReceiver
import com.kaano8.androidcore.databinding.FragmentMemoBinding
import com.kaano8.androidcore.service.timerservice.TimerService
import com.kaano8.androidcore.service.timerservice.TimerService.Companion.SERVICE_COMMAND
import kotlinx.coroutines.flow.collectLatest

class MemoFragment : Fragment() {

    companion object {
        const val TIMER_ACTION = "TimerAction"
        const val NOTIFICATION_TEXT = "NotificationText"
    }

    private val viewModel: ServiceViewModel by activityViewModels()

    private lateinit var binding: FragmentMemoBinding

    // Foreground receiver
    private val timerReceiver: TimerReceiver by lazy { TimerReceiver() }

    // Bound service
    private var progressService: ProgressService? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        observeForChanges()
    }

    override fun onResume() {
        super.onResume()
        if (!viewModel.isReceiverRegistered) {
            LocalBroadcastManager.getInstance(context!!).registerReceiver(timerReceiver, IntentFilter(TIMER_ACTION))
            viewModel.isReceiverRegistered = true
        }
        startProgressService()
    }

    override fun onPause() {
        super.onPause()
        if (viewModel.isReceiverRegistered) {
            LocalBroadcastManager.getInstance(context!!).unregisterReceiver(timerReceiver)
            viewModel.isReceiverRegistered = false
        }
        if (viewModel.binder.value != null)
            context?.unbindService(viewModel.serviceConnection)
    }

    private fun observeForChanges() {
        viewModel.elapsedTime.observe(viewLifecycleOwner) { elapsedTime ->
            binding.timerTextView.text = elapsedTime.secondsToTime()
        }

        viewModel.binder.observe(viewLifecycleOwner) { binder ->
            progressService = binder?.getService()
        }

        viewModel.isProgressBarUpdating.observe(viewLifecycleOwner) { isUpdating ->
            /*val handler = Handler(Looper.getMainLooper())
            val runnable = object : Runnable {
                override fun run() {
                    if (isUpdating) {
                        if (viewModel.binder.value != null) {
                            // service is bound
                            if (progressService?.progress!! == progressService?.maxProgress!!) {
                                viewModel.setIsProgressBarUpdating(false)
                            }
                            binding.progressBar.apply {
                                progress = progressService?.progress!!
                                max = progressService?.maxProgress!!
                            }
                            val progress =
                                "${100 * progressService?.progress!! / progressService?.maxProgress!!} %"
                            binding.progressTextView.text = progress
                        }
                        handler.postDelayed(this, 100)
                    } else {
                        handler.removeCallbacks(this)
                    }
                }
            }*/

            if (isUpdating) {
                binding.progressButton.text = "Pause"

            } else {
                if (progressService?.progress == progressService?.maxProgress) {
                    binding.progressButton.text = "Restart"
                } else {
                    binding.progressButton.text = "Start"
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.apply {
            startSevice.setOnClickListener {
                sendCommandToForegroundService(TimerState.START)
            }
            stopService.setOnClickListener {
                sendCommandToForegroundService(TimerState.STOP)
            }
            progressButton.setOnClickListener {
                toggleUpdates()
            }
        }
    }

    private fun sendCommandToForegroundService(command: TimerState) {
        context?.startForegroundService(getServiceIntent(command))
    }

    private fun getServiceIntent(command: TimerState) =
        Intent(activity, TimerService::class.java).apply {
            putExtra(SERVICE_COMMAND, command)
        }

    private fun toggleUpdates() {
        if (progressService != null) {
            if (progressService?.progress == progressService?.maxProgress) {
                progressService?.resetTask()
                binding.progressButton.text = "Start"
            } else {
                if (progressService?.isPaused == true) {
                    lifecycleScope.launchWhenStarted {
                        progressService?.resumePretendLongRunningTask()?.collectLatest { currentProgress ->
                            if (currentProgress == progressService?.maxProgress)
                                viewModel.setIsProgressBarUpdating(false)
                            binding.progressBar.apply {
                                progress = currentProgress
                                max = progressService?.maxProgress!!
                            }
                            val progress =
                                "${100 * currentProgress / progressService?.maxProgress!!} %"
                            binding.progressTextView.text = progress
                        }
                    }
                    viewModel.setIsProgressBarUpdating(true)
                } else {
                    progressService?.pausePretendLongRunningTask()
                    viewModel.setIsProgressBarUpdating(false)
                }
            }
        }
    }

    private fun startProgressService() {
        val serviceIntent = Intent(activity, ProgressService::class.java)
        context?.apply {
            // Starting service before binding makes sure the service
            // keeps on executing even after activity unbinds it
            startService(serviceIntent)
            // Actually binding the service
            bindService(
                serviceIntent,
                viewModel.serviceConnection,
                Context.BIND_AUTO_CREATE
            )
        }
    }
}
