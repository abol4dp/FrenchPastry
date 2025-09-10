package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.db

import android.content.Context
import androidx.compose.ui.unit.IntSize
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LoginEntity::class], version = 1, exportSchema = false)
abstract class LoginDataBase : RoomDatabase() {

    abstract fun userDao():LoginDao

    companion object {
        @Volatile
        private var INSTANCE:LoginDataBase? = null


        fun getDataBase(context: Context): LoginDataBase{

            return INSTANCE ?: synchronized(this){

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LoginDataBase::class.java,
                    "user_database"

                ).build()
                INSTANCE = instance
                instance

            }


        }


    }



}