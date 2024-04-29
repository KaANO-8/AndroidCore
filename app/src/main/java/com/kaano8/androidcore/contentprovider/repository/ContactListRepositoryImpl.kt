package com.kaano8.androidcore.com.kaano8.androidcore.contentprovider.repository

import android.content.Context
import android.provider.ContactsContract
import com.kaano8.androidcore.com.kaano8.androidcore.contentprovider.repository.model.Contacts
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class ContactListRepositoryImpl @Inject constructor(@ApplicationContext private val appContext: Context) :
    ContactListRepository {

    override suspend fun getContacts(): List<Contacts> = supervisorScope {
        val contacts = mutableListOf<Contacts>()

        val projections = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        )

        appContext.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            projections,
            null,
            null
        )?.use { cursor ->
            val idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            val lookupKeyIndex = cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY)
            val displayNameIndex =
                cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)

            while (cursor.moveToNext()) {
                contacts.add(
                    Contacts(
                        cursor.getString(displayNameIndex),
                        cursor.getString(lookupKeyIndex)
                    )
                )
            }
        }

        return@supervisorScope contacts
    }
}