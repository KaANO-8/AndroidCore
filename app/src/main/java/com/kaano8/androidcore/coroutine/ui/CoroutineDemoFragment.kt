package com.kaano8.androidcore.com.kaano8.androidcore.coroutine.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kaano8.androidcore.databinding.FragmentCoroutineDemoBinding
import com.kaano8.androidcore.extensions.gone
import com.kaano8.androidcore.extensions.visible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CoroutineDemoFragment : Fragment() {

    private var _binding: FragmentCoroutineDemoBinding? = null
    private val binding: FragmentCoroutineDemoBinding
        get() = _binding!!

    private val  coroutineDemoViewModel: CoroutineDemoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCoroutineDemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeForChanges()
        binding.fetchRepoButton.setOnClickListener {
            coroutineDemoViewModel.loadContributors()
            it.gone()
            binding.coroutineProgressBar.visible()
        }
    }

    private fun observeForChanges() {
        coroutineDemoViewModel.users.observe(viewLifecycleOwner) {
            binding.apply {
                resultTextView.text = it.toString()
                fetchRepoButton.visible()
                coroutineProgressBar.gone()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}