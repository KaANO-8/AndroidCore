package com.kaano8.androidcore.room.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kaano8.androidcore.R
import com.kaano8.androidcore.com.kaano8.androidcore.room.ui.adapter.WordListAdapter
import com.kaano8.androidcore.databinding.FragmentWordBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WordFragment : Fragment() {

    private val wordViewModel: WordViewModel by viewModels()
    private var binding: FragmentWordBinding? = null

    @Inject
    lateinit var wordAdapter: WordListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding?.recyclerview?.adapter = wordAdapter
    }

}