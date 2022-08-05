package com.bronski.android.antortask.core.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: UserEntity) : Completable

    @Query("SELECT*FROM users ORDER BY id ASC ")
    fun readAllData(): Flowable<MutableList<UserEntity>>
}