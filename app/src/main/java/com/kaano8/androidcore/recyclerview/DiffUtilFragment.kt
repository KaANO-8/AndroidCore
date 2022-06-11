package com.kaano8.androidcore.com.kaano8.androidcore.recyclerview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.kaano8.androidcore.R
import com.kaano8.androidcore.com.kaano8.androidcore.recyclerview.adapter.ActorAdapter
import com.kaano8.androidcore.com.kaano8.androidcore.recyclerview.repository.ActorRepository
import com.kaano8.androidcore.databinding.FragmentDiffUtilBinding

class DiffUtilFragment : Fragment() {

    private var binding: FragmentDiffUtilBinding? = null
    private val adapter = ActorAdapter(ActorRepository.getActorListSortedByName().toMutableList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiffUtilBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding?.diffUtilRecyclerView?.apply {
            adapter = this@DiffUtilFragment.adapter
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.sort_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.sort_by_name -> adapter.swapItems(ActorRepository.getActorListSortedByName())
            R.id.sort_by_rating -> adapter.swapItems(ActorRepository.getActorListSortedByRating())
            R.id.sort_by_birth -> adapter.swapItems(ActorRepository.getActorListSortedByYearOfBirth())
        }

        return super.onOptionsItemSelected(item)

    }
}