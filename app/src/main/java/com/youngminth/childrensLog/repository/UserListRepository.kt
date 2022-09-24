package com.youngminth.childrensLog.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.youngminth.childrensLog.database.UsersDatabase
import com.youngminth.childrensLog.database.asDomainModel
import com.youngminth.childrensLog.domain.UserListItem
import com.youngminth.childrensLog.network.UserListService
import com.youngminth.childrensLog.network.model.asDatabaseModel
import timber.log.Timber
import javax.inject.Inject

class UserListRepository @Inject constructor(
    private val userListService: UserListService,
    private val database: UsersDatabase
) {

    val users: LiveData<List<UserListItem>> =
        Transformations.map(database.usersDao.getDatabaseUsers()) {
            it.asDomainModel()
        }

    suspend fun refreshUserList() {
        try {
            val users = userListService.getUserList()
            database.usersDao.insertAll(users.asDatabaseModel())
        } catch (e: Exception) {
            Timber.w(e)
        }
    }
}