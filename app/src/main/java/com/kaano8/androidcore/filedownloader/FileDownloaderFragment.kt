package com.kaano8.androidcore.filedownloader

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.FileDownloadViewModelFactory
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.adapter.FileDownloadAdapter
import com.kaano8.androidcore.databinding.FragmentFileDownloaderBinding
import com.kaano8.androidcore.filedownloader.service.FileDownloaderService
import com.kaano8.androidcore.filedownloader.service.FileDownloaderService.Companion.ACTION
import com.kaano8.androidcore.filedownloader.service.FileDownloaderService.Companion.REQUEST_NAME
import com.kaano8.androidcore.filedownloader.service.FileDownloaderService.Companion.TASK_ID
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
                scheduleWork(requestName.text.toString())
            }
            cleanAll.setOnClickListener {
                viewModel.clearAll()
            }
        }
    }

    private fun setupRecyclerView() {
        fileDownloadAdapter = FileDownloadAdapter(
            onPause = { pauseWork(it) },
            onStop = { downloadId -> cancelWork(downloadId) }
        )

        with(binding.downloadList) {
            adapter = fileDownloadAdapter
        }
    }
    private fun scheduleWork(requestName: String) {
        val intent = Intent(requireActivity(), FileDownloaderService::class.java).also {
            it.putExtra(ACTION, FileDownloaderService.Companion.Action.START.name)
            it.putExtra(REQUEST_NAME, requestName)
        }

        context?.startService(intent)
    }

    private fun pauseWork(taskId: Long) {
        val intent = Intent(requireActivity(), FileDownloaderService::class.java).also {
            it.putExtra(ACTION, FileDownloaderService.Companion.Action.PAUSE.name)
            it.putExtra(TASK_ID, taskId)
        }

        context?.startService(intent)
    }

    private fun cancelWork(taskId: Long) {
        val intent = Intent(requireActivity(), FileDownloaderService::class.java).also {
            it.putExtra(ACTION, FileDownloaderService.Companion.Action.CANCEL.name)
            it.putExtra(TASK_ID, taskId)
        }

        context?.startService(intent)
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