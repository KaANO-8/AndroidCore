package com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.di

import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.FileDownloadRepository
import com.kaano8.androidcore.com.kaano8.androidcore.filedownloader.repository.FileDownloadRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FileDownloadModule {

    @Binds
    abstract fun bindFileDownloadRepository(fileDownloadRepositoryImpl: FileDownloadRepositoryImpl): FileDownloadRepository
}