package com.bronski.android.antortask.manage.model

import com.bronski.android.antortask.core.data.UserDao
import com.bronski.android.antortask.core.data.UserEntity
import com.bronski.android.antortask.core.model.BaseRepoImpl
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ManageUserRepoImpl @Inject constructor(
    private val userDao: UserDao,
) : BaseRepoImpl(userDao = userDao), IManageUserRepo {

    override fun deleteUser(userEntity: UserEntity): Completable {
        return userDao.deleteUser(user = userEntity)
            .observeOn(Schedulers.io()).subscribeOn(Schedulers.io())
    }

    override fun deleteAllUsers(): Completable {
        return userDao.deleteAllUsers()
            .observeOn(Schedulers.io()).subscribeOn(Schedulers.io())
    }
}