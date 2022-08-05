package com.bronski.android.antortask.core.model

import com.bronski.android.antortask.core.data.UserDao
import com.bronski.android.antortask.core.data.UserEntity
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers

abstract class BaseRepoImpl(
    private val userDao: UserDao,
) : IBaseRepo {

    override fun getAllDataFromDatabase(): Maybe<List<UserEntity>> {
        return userDao.readAllData()
            .observeOn(Schedulers.io()).subscribeOn(Schedulers.io())
    }
}