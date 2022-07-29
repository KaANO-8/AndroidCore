package com.kaano8.androidcore.com.kaano8.androidcore.coroutine.network

import com.kaano8.androidcore.com.kaano8.androidcore.coroutine.network.models.Repo
import com.kaano8.androidcore.com.kaano8.androidcore.coroutine.network.models.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoroutineGithubService {
    @GET("users/{owner}/repos")
    suspend fun getOrgReposCall(
        @Path("owner") owner: String
    ): Response<List<Repo>>

    @GET("repos/{owner}/{repo}/contributors?per_page=100")
    suspend fun getRepoContributorsCall(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<List<User>>
}