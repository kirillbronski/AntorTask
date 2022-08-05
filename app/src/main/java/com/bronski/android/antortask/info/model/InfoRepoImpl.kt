package com.bronski.android.antortask.info.model

import com.bronski.android.antortask.core.data.UserDao
import javax.inject.Inject

class InfoRepoImpl @Inject constructor(
    private val userDao: UserDao,
) : IInfoRepo {
}