package com.kaano8.androidcore.com.kaano8.androidcore.di

import com.kaano8.androidcore.BuildConfig
import com.kaano8.androidcore.com.kaano8.androidcore.coroutine.network.CoroutineGithubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().also {
            // Add logging interceptor for only debug builds
            if (BuildConfig.DEBUG)
                it.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideOkHttpNetworkClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideGithubService(retrofit: Retrofit): CoroutineGithubService =
        retrofit.create(CoroutineGithubService::class.java)

    companion object {
        private const val BASE_URL = "https://api.github.com/"
    }
}
