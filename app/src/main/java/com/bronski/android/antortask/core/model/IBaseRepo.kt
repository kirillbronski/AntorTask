package com.bronski.android.antortask.core.model

import com.bronski.android.antortask.core.data.UserEntity
import io.reactivex.Maybe

interface IBaseRepo {

    fun getAllDataFromDatabase(): Maybe<List<UserEntity>>

}