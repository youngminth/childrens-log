package com.youngminth.childrensLog.di

import android.content.Context
import androidx.room.Room
import com.youngminth.childrensLog.database.UsersDao
import com.youngminth.childrensLog.database.UsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): UsersDatabase {
        return Room.databaseBuilder(
            appContext,
            UsersDatabase::class.java,
            "Users"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideChannelDao(usersDatabase: UsersDatabase): UsersDao {
        return usersDatabase.usersDao
    }

}