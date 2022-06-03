package com.kaano8.androidcore.timertoast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.kaano8.androidcore.databinding.FragmentTimerToastBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class TimerToastFragment : Fragment() {

    private lateinit var binding: FragmentTimerToastBinding
    private var isActive = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimerToastBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startButton.setOnClickListener {
            isActive = true
            startTimer(0)
        }
        binding.stopButton.setOnClickListener {
            stopTimer()
        }
    }

    private fun startTimer(startFrom: Int) {
        lifecycleScope.launch {
            var start = startFrom
            while (isActive) {
                delay(1000)
                Toast.makeText(activity, "Time elapsed: $startFrom secs", Toast.LENGTH_SHORT).show()
                start++
            }
        }
    }

    private fun stopTimer() {
        isActive = false
    }
}