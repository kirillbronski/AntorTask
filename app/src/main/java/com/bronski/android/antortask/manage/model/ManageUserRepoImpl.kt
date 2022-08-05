package com.bronski.android.antortask.manage.model

import com.bronski.android.antortask.core.data.UserDao
import javax.inject.Inject

class ManageUserRepoImpl @Inject constructor(
    private val userDao: UserDao,
) : IManageUserRepo {
}