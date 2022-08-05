package com.bronski.android.antortask.users.model

import com.bronski.android.antortask.core.data.UserDao
import com.bronski.android.antortask.core.data.UserEntity
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UsersRepoImpl @Inject constructor(
    private val userDao: UserDao,
) : IUsersRepo {

    override fun getAllDataFromDatabase(): Maybe<List<UserEntity>> {
        return userDao.readAllData()
            .observeOn(Schedulers.io()).subscribeOn(Schedulers.io())
    }

}