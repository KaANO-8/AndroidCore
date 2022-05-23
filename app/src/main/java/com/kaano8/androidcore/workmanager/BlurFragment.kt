package com.kaano8.androidcore.com.kaano8.androidcore.workmanager

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kaano8.androidcore.R
import com.kaano8.androidcore.databinding.FragmentBlurBinding

class BlurFragment : Fragment() {

    private val viewModel: BlurViewModel by viewModels { BlurViewModel.BlurViewModelFactory(activity?.application!!) }

    private lateinit var binding: FragmentBlurBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlurBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goButton.setOnClickListener { viewModel.applyBlur(blurLevel) }

        // Setup view output image file button
        binding.seeFileButton.setOnClickListener {
            viewModel.outputUri?.let { currentUri ->
                val actionView = Intent(Intent.ACTION_VIEW, currentUri)
                actionView.resolveActivity(activity?.packageManager!!)?.run {
                    startActivity(actionView)
                }
            }
        }

        binding.cancelButton.setOnClickListener { viewModel.cancelWork() }

        viewModel.outputWorkInfos.observe(viewLifecycleOwner) { workInfos ->
            if (workInfos.isNotEmpty()) {
                if (workInfos.first().state.isFinished) {
                    showWorkFinished()

                    // Normally this processing, which is not directly related to drawing views on
                    // screen would be in the ViewModel. For simplicity we are keeping it here.
                    val outputImageUri = workInfos.first().outputData.getString(KEY_IMAGE_URI)

                    // If there is an output file show "See File" button
                    if (!outputImageUri.isNullOrEmpty()) {
                        viewModel.setOutputUri(outputImageUri)
                        binding.seeFileButton.visibility = View.VISIBLE
                    }
                }
                else
                    showWorkInProgress()
            }
        }
    }

    /**
     * Shows and hides views for when the Activity is processing an image
     */
    private fun showWorkInProgress() {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            cancelButton.visibility = View.VISIBLE
            goButton.visibility = View.GONE
            seeFileButton.visibility = View.GONE
        }
    }

    /**
     * Shows and hides views for when the Activity is done processing an image
     */
    private fun showWorkFinished() {
        with(binding) {
            progressBar.visibility = View.GONE
            cancelButton.visibility = View.GONE
            goButton.visibility = View.VISIBLE
        }
    }

    private val blurLevel: Int
        get() =
            when (binding.radioBlurGroup.checkedRadioButtonId) {
                R.id.radio_blur_lv_1 -> 1
                R.id.radio_blur_lv_2 -> 2
                R.id.radio_blur_lv_3 -> 3
                else -> 1
            }
}