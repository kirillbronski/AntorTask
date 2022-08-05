package com.bronski.android.antortask.manage.model

import com.bronski.android.antortask.core.data.UserEntity
import com.bronski.android.antortask.core.model.IBaseRepo
import io.reactivex.Completable

interface IManageUserRepo : IBaseRepo {

    fun deleteUser(userEntity: UserEntity): Completable
}
