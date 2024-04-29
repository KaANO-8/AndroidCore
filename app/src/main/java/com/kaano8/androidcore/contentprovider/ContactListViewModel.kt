package com.kaano8.androidcore.contentprovider

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kaano8.androidcore.com.kaano8.androidcore.contentprovider.repository.ContactListRepository
import com.kaano8.androidcore.com.kaano8.androidcore.contentprovider.repository.model.Contacts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(private val contactListRepository: ContactListRepository) :
    ViewModel() {

    private val _contactList: MutableStateFlow<List<Contacts>> = MutableStateFlow(listOf())

    val contactList: StateFlow<List<Contacts>>
        get() = _contactList

    fun getContacts() {
        viewModelScope.launch {
            _contactList.value = contactListRepository.getContacts()
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ContactListViewModelFactory @Inject constructor(private val contactListRepository: ContactListRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactListViewModel(contactListRepository) as T
    }
}