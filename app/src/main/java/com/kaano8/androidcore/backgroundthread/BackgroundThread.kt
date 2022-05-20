package com.kaano8.androidcore.backgroundthread

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kaano8.androidcore.backgroundthread.repository.Repository
import com.kaano8.androidcore.databinding.FragmentBackgroundThreadBinding
import com.kaano8.androidcore.extensions.disabled
import com.kaano8.androidcore.extensions.enabled
import com.kaano8.androidcore.extensions.gone
import com.kaano8.androidcore.extensions.visible

class BackgroundThread : Fragment() {

    private lateinit var binding: FragmentBackgroundThreadBinding
    private lateinit var viewModel: BackgroundThreadViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBackgroundThreadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BackgroundThreadViewModel::class.java)

        observeChanges()

        binding.startButton.setOnClickListener {
            viewModel.makeRequest()
        }
    }

    private fun observeChanges() {
        viewModel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Repository.Result.Loading -> {
                    binding.apply {
                        backgroundThreadSpinner.visible()
                        startButton.disabled()
                        stopButton.enabled()
                        responseTextView.gone()
                    }
                }
                is Repository.Result.Success -> {
                    binding.apply {
                        backgroundThreadSpinner.gone()
                        responseTextView.text = response.data
                        responseTextView.visible()
                        startButton.enabled()
                        stopButton.disabled()
                    }
                }
                else -> {
                    // do nothing
                }
            }
        }
    }
}
