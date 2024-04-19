package com.kaano8.androidcore.com.kaano8.androidcore.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.kaano8.androidcore.R
import com.kaano8.androidcore.com.kaano8.androidcore.adapter.MainAdapter
import com.kaano8.androidcore.com.kaano8.androidcore.adapter.data.MainListData
import com.kaano8.androidcore.compose.ComposeActivity
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
        val itemDecorator = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        binding?.recyclerView?.apply {
            adapter = mainAdapter
            addItemDecoration(itemDecorator)
            setHasFixedSize(true)
        }
        mainAdapter.submitList(prepareList())
    }

    private fun prepareList(): List<MainListData> {
        val list = mutableListOf<MainListData>()

        list.apply {
            add(MainListData.HeaderItem(header = "Background"))
            add(
                MainListData.DataItem(
                    title = "Background threads",
                    detail = "All about background threads",
                    action = { findNavController().navigate(R.id.action_mainFragment_to_backgroundThread) })
            )
            add(
                MainListData.DataItem(
                    title = "Android handlers",
                    detail = "All about android handlers",
                    action = { findNavController().navigate(R.id.action_mainFragment_to_handlerFragment) })
            )
            add(
                MainListData.DataItem(
                    title = "Work Manager",
                    detail = "Work manager in action",
                    action = { findNavController().navigate(R.id.action_mainFragment_to_blurFragment) })
            )
            add(
                MainListData.DataItem(
                    title = "Services",
                    detail = "Experiment with foreground service",
                    action = { findNavController().navigate(R.id.action_mainFragment_to_memoFragment) })
            )
            add(
                MainListData.DataItem(
                    title = "Coroutines Demo",
                    detail = "Experiment with coroutines",
                    action = { findNavController().navigate(R.id.action_mainFragment_to_coroutineDemoFragment) })
            )
            add(MainListData.HeaderItem(header = "UI"))
            add(
                MainListData.DataItem(
                    title = "DiffUtil",
                    detail = "Experiment with RV's diffUtil",
                    action = { findNavController().navigate(R.id.action_mainFragment_to_diffUtilFragment) })
            )
            add(
                MainListData.DataItem(
                    title = "Settings Pref",
                    detail = "Play with settings prefs",
                    action = {
                        val action =
                            MainFragmentDirections.actionMainFragmentToSettingsFragment("hello, world")
                        findNavController().navigate(action)
                    })
            )
            add(MainListData.HeaderItem(header = "Database"))
            add(
                MainListData.DataItem(
                    title = "Room",
                    detail = "Experiment with Room database",
                    action = { findNavController().navigate(R.id.action_mainFragment_to_wordFragment) })
            )
            add(
                MainListData.DataItem(
                    title = "Compose",
                    detail = "Experiment with compose",
                    action = {
                        startActivity(Intent(activity, ComposeActivity::class.java))
                    })
            )
        }
        return list
    }
}
