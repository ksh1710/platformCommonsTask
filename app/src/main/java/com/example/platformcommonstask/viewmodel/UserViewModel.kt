package com.example.platformcommonstask.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.platformcommonstask.data.model.User
import com.example.platformcommonstask.data.repository.UserRepository
import com.example.platformcommonstask.utils.NetworkUtils
import com.example.platformcommonstask.utils.SyncStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository,
    private val workManager: WorkManager,
    private val networkUtils: NetworkUtils
) : ViewModel() {


    val users = repository.getUsers().cachedIn(viewModelScope)

    private val _addUsers = MutableStateFlow<List<User>>(emptyList())
    val addUsers: StateFlow<List<User>> = _addUsers.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isOnline = MutableStateFlow(false)
    val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()

    private val _syncStatus = MutableStateFlow<SyncStatus>(SyncStatus.NOT_SYNCED)
    val syncStatus: StateFlow<SyncStatus> = _syncStatus.asStateFlow()

    private fun observeSyncWork() {
        viewModelScope.launch {
            workManager.getWorkInfosForUniqueWorkFlow("sync_users")
                .collect { workInfoList ->
                    val isSyncing = workInfoList.any { workInfo ->
                        workInfo.state == WorkInfo.State.RUNNING
                    }
                    _syncStatus.value = when {
                        isSyncing && isOnline.value -> SyncStatus.SYNCING
                        !isSyncing && isOnline.value -> SyncStatus.SYNCED
                        else -> SyncStatus.NOT_SYNCED
                    }
                    Log.d("idk", "Sync Status: ${_syncStatus.value}")
                }
        }
    }

    init {
        getUsers()

        observeSyncWork()

        observeNetworkState()
        scheduleSync()
    }

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    fun updateName(name: String) {
        _name.value = name
    }

    private val _job = MutableStateFlow("")
    val job: StateFlow<String> = _job.asStateFlow()

    fun updateJob(name: String) {
        _job.value = name
    }

    fun addUser(name: String, job: String) {
        if (name.isBlank() || job.isBlank()) {
            return
        }

        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.addUser(name, job)
            } catch (e: Exception) {
                throw e
            } finally {
                _isLoading.value = false
            }
        }
    }


    private fun observeNetworkState() {
        viewModelScope.launch {
            networkUtils.observeNetworkState().collect { isOnline ->
                _isOnline.value = isOnline
            }
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getAllUsers()
                .catch { e ->
                    _isLoading.value = false
                }
                .collect { userList ->
                    _addUsers.value = userList
                    _isLoading.value = false
                }
        }
    }

    private fun scheduleSync() {
        Log.d("idk", "Scheduling immediate sync from ViewModel")
        repository.scheduleImmediateSyncWork()
    }

}

