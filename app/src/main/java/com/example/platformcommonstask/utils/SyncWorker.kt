package com.example.platformcommonstask.utils

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.platformcommonstask.data.model.AddUserRequest
import com.example.platformcommonstask.data.network.UserApiService
import com.example.platformcommonstask.db.dao.UserDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val userDao: UserDao,
    private val apiService: UserApiService,
    private val networkUtils: NetworkUtils
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        if (!networkUtils.isNetworkAvailable()) {
            return@withContext Result.retry()
        }

        try {
            val unsyncedUsers = userDao.getUnsyncedUsers()

            unsyncedUsers.forEach { userEntity ->
                val response = apiService.createUser(
                    AddUserRequest(name = userEntity.name, job = userEntity.job)
                )

                if (response.isSuccessful) {
                    response.body()?.let { userResponse ->
                        userDao.updateUser(
                            userEntity.copy(
                                remoteId = userResponse.id,
                                synced = true
                            )
                        )
                    }
                }
            }

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}