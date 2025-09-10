package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynotes.mynotes.mynotes.frenchpastry_kh.ext.SealedClassNavName

@Dao
interface LoginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: LoginEntity)

    @Query("SELECT * FROM user_table LIMIT 1")
    suspend fun getUser(): LoginEntity?


    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

}