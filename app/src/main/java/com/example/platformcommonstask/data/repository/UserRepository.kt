package com.example.platformcommonstask.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.platformcommonstask.data.model.AddUserRequest
import com.example.platformcommonstask.data.model.User
import com.example.platformcommonstask.data.network.UserApiService
import com.example.platformcommonstask.data.model.UserResponseData
import com.example.platformcommonstask.db.dao.UserDao
import com.example.platformcommonstask.db.entities.UserEntity
import com.example.platformcommonstask.paging.UserPagingSource
import com.example.platformcommonstask.utils.NetworkUtils
import com.example.platformcommonstask.utils.SyncWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: UserApiService,
    private val userDao: UserDao,
    private val networkUtils: NetworkUtils,
    private val workManager: WorkManager

) {
    fun getUsers(): Flow<PagingData<UserResponseData>> {
        return Pager(
            config = PagingConfig(pageSize = 6, enablePlaceholders = false),
            pagingSourceFactory = { UserPagingSource(apiService) }
        ).flow
    }

    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers().map { entities ->
            entities.map { entity ->
                User(
                    id = entity.id,
                    name = entity.name,
                    job = entity.job,
                    synced = entity.synced
                )
            }
        }
    }

    suspend fun addUser(name: String, job: String) {
        if (networkUtils.isNetworkAvailable()) {
            try {
                val response = apiService.createUser(AddUserRequest(name = name, job = job))
                Log.d("idk", "addUser repo response: $response ")
                if (response.isSuccessful) {
                    response.body()?.let { userResponse ->
                        userDao.insertUser(
                            UserEntity(
                                id = userResponse.id.toInt(),
                                name = name,
                                job = job,
                                synced = true
                            )
                        )
                    }
                } else {
                    saveUserLocally(name, job)
                    scheduleImmediateSyncWork()
                }
            } catch (e: Exception) {
                Log.d("idk", "addUser repo exception: $e ")
                saveUserLocally(name, job)
                scheduleImmediateSyncWork()
            }
        } else {
            saveUserLocally(name, job)
            scheduleImmediateSyncWork()
        }
    }

    private suspend fun saveUserLocally(name: String, job: String) {
        userDao.insertUser(
            UserEntity(
                name = name,
                job = job,
                synced = false
            )
        )
    }

    fun scheduleImmediateSyncWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val oneTimeSyncWorkRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .addTag("sync_users")
            .build()

        workManager.enqueue(oneTimeSyncWorkRequest)
    }

}



