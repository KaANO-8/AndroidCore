package com.kaano8.androidcore.service

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kaano8.androidcore.com.kaano8.androidcore.extensions.secondsToTime
import com.kaano8.androidcore.com.kaano8.androidcore.service.model.TimerState
import com.kaano8.androidcore.com.kaano8.androidcore.service.receiver.TimerReceiver
import com.kaano8.androidcore.databinding.FragmentMemoBinding
import com.kaano8.androidcore.service.timerservice.TimerService
import com.kaano8.androidcore.service.timerservice.TimerService.Companion.SERVICE_COMMAND

class MemoFragment : Fragment() {

    companion object {
        const val TIMER_ACTION = "TimerAction"
        const val NOTIFICATION_TEXT = "NotificationText"
    }

    private val viewModel: MemoViewModel by activityViewModels()

    private lateinit var binding: FragmentMemoBinding

    // Foreground receiver
    private val timerReceiver: TimerReceiver by lazy { TimerReceiver() }

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
            activity?.registerReceiver(timerReceiver, IntentFilter(TIMER_ACTION))
            viewModel.isReceiverRegistered = true
        }
    }

    override fun onPause() {
        super.onPause()
        if (viewModel.isReceiverRegistered) {
            activity?.unregisterReceiver(timerReceiver)
            viewModel.isReceiverRegistered = false
        }
    }

    private fun observeForChanges() {
        viewModel.elapsedTime.observe(viewLifecycleOwner) { elapsedTime ->
            binding.timerTextView.text = elapsedTime.secondsToTime()
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
        }
    }

    private fun sendCommandToForegroundService(command: TimerState) {
        ContextCompat.startForegroundService(context!!, getServiceIntent(command))
    }

    private fun getServiceIntent(command: TimerState) =
        Intent(activity, TimerService::class.java).apply {
            putExtra(SERVICE_COMMAND, command)
        }
}
