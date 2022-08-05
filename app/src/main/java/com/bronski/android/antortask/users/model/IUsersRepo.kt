package com.bronski.android.antortask.users.model

import com.bronski.android.antortask.core.data.UserEntity
import io.reactivex.Maybe

interface IUsersRepo {
    fun getAllDataFromDatabase(): Maybe<List<UserEntity>>
}
