package com.bronski.android.antortask.info.model

import com.bronski.android.antortask.core.data.UserDao
import com.bronski.android.antortask.core.model.BaseRepoImpl
import javax.inject.Inject

class InfoRepoImpl @Inject constructor(
    userDao: UserDao,
) : BaseRepoImpl(userDao), IInfoRepo {
}