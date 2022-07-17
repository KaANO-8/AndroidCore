package com.kaano8.androidcore.com.kaano8.androidcore.di

import android.content.Context
import com.kaano8.androidcore.com.kaano8.androidcore.room.dao.WordDao
import com.kaano8.androidcore.com.kaano8.androidcore.room.database.WordRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class, ViewModelComponent::class)
class RoomModule {

    @Provides
    fun provideWordDao(@ApplicationContext context: Context, coroutineScope: CoroutineScope): WordDao =
        WordRoomDatabase.getDatabase(context, coroutineScope).wordDao()
}
