package com.bronski.android.antortask.createuser.model

import com.bronski.android.antortask.core.data.UserEntity
import io.reactivex.rxjava3.core.Completable

interface ICreateUserRepo {
        fun addUser(userEntity: UserEntity) : Completable
}
