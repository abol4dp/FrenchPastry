package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.db.LocalRoomRepository
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.db.LoginDao
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.db.LoginDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object LocalDatabaseModule {
@Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): LoginDataBase {
        return Room.databaseBuilder(
            context,
            LoginDataBase::class.java,
            "user_database"
        ).build()
    }

    @Provides
    fun provideLoginDao(db: LoginDataBase): LoginDao {
        return db.userDao()
    }

    @Provides
    fun provideLocalRepository(dao: LoginDao): LocalRoomRepository {
        return LocalRoomRepository(dao)
    }
}

