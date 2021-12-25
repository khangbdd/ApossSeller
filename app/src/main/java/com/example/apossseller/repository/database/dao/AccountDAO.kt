package com.example.apossseller.repository.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.apossseller.model.entity.Account

@Dao
interface AccountDAO {
    @Insert
    fun insertAccount(account: Account)

    @Delete
    fun deleteAccount(account: Account)

    @Query("UPDATE account SET accessToken = :accessToken")
    fun updateAccessToken(accessToken: String)

    @Query("SELECT * FROM account Limit 1")
    fun getAccount(): Account?
}