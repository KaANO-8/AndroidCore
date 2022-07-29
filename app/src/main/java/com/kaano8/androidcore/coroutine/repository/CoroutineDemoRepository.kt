package com.kaano8.androidcore.com.kaano8.androidcore.coroutine.repository

import android.util.Log
import com.kaano8.androidcore.com.kaano8.androidcore.coroutine.network.CoroutineGithubService
import com.kaano8.androidcore.com.kaano8.androidcore.coroutine.network.models.Repo
import com.kaano8.androidcore.com.kaano8.androidcore.coroutine.network.models.RequestData
import com.kaano8.androidcore.com.kaano8.androidcore.coroutine.network.models.User
import com.kaano8.androidcore.com.kaano8.androidcore.extensions.aggregate
import kotlinx.coroutines.*
import retrofit2.Response
import javax.inject.Inject


class CoroutineDemoRepository@Inject constructor(private val service: CoroutineGithubService) {

    companion object {
        private val TAG = "CoroutineDemoRepository"
    }

    suspend fun loadContributorBlocking(requestData: RequestData): List<User> = coroutineScope {
        val repos = service.getOrgReposCall(requestData.username)
            .also {logRepos(requestData, it) }
            .body() ?: listOf()


        val deferredList: List<Deferred<List<User>>> = repos.subList(0,5).map { repo ->
            async(Dispatchers.IO) {
                service.getRepoContributorsCall(requestData.username, repo.name)
                    .also { logUsers(repo, it) }
                    .body() ?: listOf()
            }
        }

        deferredList.awaitAll().flatten().aggregate()
    }

    private fun logRepos(req: RequestData, response: Response<List<Repo>>) {
        val repos = response.body()
        if (!response.isSuccessful || repos == null) {
            Log.e(TAG, "Failed loading repos for ${req.username} with response: '${response.code()}: ${response.message()}'")
        }
        else {
            Log.i(TAG, "logRepos: ${Thread.currentThread().name}")
            Log.i(TAG, "${req.username}: loaded ${repos.size} repos")
        }
    }

    private fun logUsers(repo: Repo, response: Response<List<User>>) {
        val users = response.body()
        if (!response.isSuccessful || users == null) {
            Log.e(TAG, "Failed loading contributors for ${repo.name} with response '${response.code()}: ${response.message()}'" )
        }
        else {
            Log.i(TAG, "logUsers: ${Thread.currentThread().name}")
            Log.i(TAG, "${repo.name}: loaded ${users.size} contributors")
        }
    }

}