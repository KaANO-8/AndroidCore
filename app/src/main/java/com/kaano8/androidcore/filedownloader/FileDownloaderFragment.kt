package com.kaano8.androidcore.filedownloader

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.FileDownloadViewModelFactory
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.adapter.FileDownloadAdapter
import com.kaano8.androidcore.databinding.FragmentFileDownloaderBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FileDownloaderFragment : Fragment() {

    @Inject
    lateinit var fileDownloadViewModelFactory: FileDownloadViewModelFactory

    private val viewModel: FileDownloaderViewModel by viewModels(factoryProducer = { fileDownloadViewModelFactory })

    private lateinit var binding: FragmentFileDownloaderBinding

    private lateinit var fileDownloadAdapter: FileDownloadAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFileDownloaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeForChanges()
        with(binding) {
            scheduleTask.setOnClickListener {
                viewModel.scheduleTask(
                    requestName.text.toString(),
                    requestTime.text.toString().toInt()
                )
            }
        }
    }

    private fun setupRecyclerView() {
        fileDownloadAdapter = FileDownloadAdapter(onPause = {
            Toast.makeText(context, "Following item is paused: {$it}", Toast.LENGTH_LONG)
                .show()
        }, onStop = { downloadId ->
            viewModel.markItemCompleted(downloadId)
        })
        with(binding.downloadList) {
            adapter = fileDownloadAdapter
        }
    }

    private fun observeForChanges() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getAllItems().collect {
                    fileDownloadAdapter.submitList(it)
                }
            }
        }
    }
}