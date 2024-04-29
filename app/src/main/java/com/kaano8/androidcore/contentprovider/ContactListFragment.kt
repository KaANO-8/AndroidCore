package com.kaano8.androidcore.contentprovider

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kaano8.androidcore.databinding.FragmentContactListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ContactListFragment : Fragment() {

    @Inject
    lateinit var contactListViewModelFactory: ContactListViewModelFactory

    private lateinit var fragmentContactListBinding: FragmentContactListBinding

    private val viewModel: ContactListViewModel by viewModels(factoryProducer = { contactListViewModelFactory })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentContactListBinding = FragmentContactListBinding.inflate(inflater, container, false)
        return fragmentContactListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS) , 0)

        viewModel.getContacts()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contactList.collect {
                    fragmentContactListBinding.contactList.text = it.joinToString(",")
                }
            }
        }

    }
}
