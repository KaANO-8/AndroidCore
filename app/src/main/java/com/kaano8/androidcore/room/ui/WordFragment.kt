package com.kaano8.androidcore.room.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kaano8.androidcore.R
import com.kaano8.androidcore.com.kaano8.androidcore.room.entity.Word
import com.kaano8.androidcore.com.kaano8.androidcore.room.ui.adapter.WordListAdapter
import com.kaano8.androidcore.databinding.FragmentWordBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WordFragment() : Fragment() {

    private val wordViewModel: WordViewModel by viewModels()
    private var binding: FragmentWordBinding? = null
    private val args: WordFragmentArgs by navArgs()

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
        setupObservers()
        setupClickListeners()
        val newWord = args.newWord
        if (newWord?.isNotBlank() == true)
            wordViewModel.insert(Word(word = newWord))

    }

    private fun setupRecyclerView() {
        binding?.recyclerview?.adapter = wordAdapter
    }

    private fun setupObservers() {
        wordViewModel.allWords.observe(viewLifecycleOwner) { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { wordAdapter.submitList(it) }
        }
    }

    private fun setupClickListeners() {
        binding?.fabAdd?.setOnClickListener {
            findNavController().navigate(R.id.action_wordFragment_to_newWordFragment)
        }
        binding?.fabDelete?.setOnClickListener {
            wordViewModel.deleteAll()
        }
    }
}
