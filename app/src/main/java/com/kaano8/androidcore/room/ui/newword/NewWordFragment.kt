package com.kaano8.androidcore.room.ui.newword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kaano8.androidcore.R
import com.kaano8.androidcore.databinding.FragmentNewWordBinding


class NewWordFragment : Fragment() {

    private var binding: FragmentNewWordBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewWordBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.buttonSave?.setOnClickListener {
            val newWord = binding?.editWord?.text?.toString()
            findNavController().navigate(
                R.id.action_newWordFragment_to_wordFragment,
                bundleOf(NEW_WORD to newWord)
            )
        }
    }

    companion object {
        const val NEW_WORD = "NEW_WORD"
    }

}