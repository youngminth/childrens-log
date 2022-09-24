package com.youngminth.childrensLog.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.youngminth.childrensLog.database.UsersDatabase
import com.youngminth.childrensLog.database.asDomainModel
import com.youngminth.childrensLog.domain.UserDetails
import com.youngminth.childrensLog.network.UserDetailsService
import com.youngminth.childrensLog.network.model.asDatabaseModel
import timber.log.Timber
import javax.inject.Inject

class UserDetailsRepository @Inject constructor(
    private val userDetailsService: UserDetailsService,
    private val database: UsersDatabase
) {

    fun getUserDetails(user: String): LiveData<UserDetails> {
        return Transformations.map(database.usersDao.getUserDetails(user)) {
            it?.asDomainModel()
        }
    }


    suspend fun refreshUserDetails(user: String) {
        try {
            val userDetails = userDetailsService.getUserDetails(user)
            database.usersDao.insertUserDetails(userDetails.asDatabaseModel())
        } catch (e: Exception) {
            Timber.w(e)
        }
    }

}