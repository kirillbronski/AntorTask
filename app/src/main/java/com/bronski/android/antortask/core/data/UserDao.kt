package com.bronski.android.antortask.core.data

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: UserEntity): Completable

    @Delete
    fun deleteUser(user: UserEntity): Completable

    @Query("DELETE FROM users")
    fun deleteAllUsers(): Completable

    @Query("SELECT * FROM users ORDER BY id ASC ")
    fun readAllData(): Maybe<List<UserEntity>>
}