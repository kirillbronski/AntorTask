package com.bronski.android.antortask.createuser.model

import com.bronski.android.antortask.core.data.UserDao
import com.bronski.android.antortask.core.data.UserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CreateUserRepoImpl @Inject constructor(
    private val userDao: UserDao,
) : ICreateUserRepo {

    override fun addUser(userEntity: UserEntity): Completable {
        return userDao.addUser(userEntity)
            .observeOn(Schedulers.io()).subscribeOn(Schedulers.io())
    }

}