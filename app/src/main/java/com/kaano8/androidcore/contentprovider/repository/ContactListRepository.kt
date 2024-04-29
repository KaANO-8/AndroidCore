package com.kaano8.androidcore.com.kaano8.androidcore.contentprovider.repository

import com.kaano8.androidcore.com.kaano8.androidcore.contentprovider.repository.model.Contacts

interface ContactListRepository {
    suspend fun getContacts(): List<Contacts>
}