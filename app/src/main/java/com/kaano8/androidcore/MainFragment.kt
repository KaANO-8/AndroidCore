package com.kaano8.androidcore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kaano8.androidcore.com.kaano8.androidcore.adapter.MainAdapter
import com.kaano8.androidcore.com.kaano8.androidcore.adapter.data.MainDataHolder
import com.kaano8.androidcore.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null
    private val mainAdapter = MainAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding?.recyclerView?.adapter = mainAdapter
        mainAdapter.submitList(prepareList())
    }

    private fun prepareList(): List<MainDataHolder> {
        val list = mutableListOf<MainDataHolder>()

        list.apply {
            add(
                MainDataHolder(
                    title = "Background threads",
                    detail = "All about background threads",
                    action = { findNavController().navigate(R.id.action_mainFragment_to_backgroundThread) })
            )
            add(
                MainDataHolder(
                    title = "Android handlers",
                    detail = "All about android handlers",
                    action = { findNavController().navigate(R.id.action_mainFragment_to_handlerFragment) })
            )
            add(
                MainDataHolder(
                    title = "Work Manager",
                    detail = "Work manager in action",
                    action = { findNavController().navigate(R.id.action_mainFragment_to_blurFragment) })
            )
        }
        return list
    }
}