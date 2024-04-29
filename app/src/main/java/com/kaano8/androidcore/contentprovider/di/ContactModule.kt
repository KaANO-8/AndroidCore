package com.kaano8.androidcore.com.kaano8.androidcore.contentprovider.di

import com.kaano8.androidcore.com.kaano8.androidcore.contentprovider.repository.ContactListRepository
import com.kaano8.androidcore.com.kaano8.androidcore.contentprovider.repository.ContactListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class ContactModule {
    @Binds
    abstract fun bindContactRepository(contactListRepositoryImpl: ContactListRepositoryImpl): ContactListRepository
}