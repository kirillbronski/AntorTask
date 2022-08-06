package com.bronski.android.antortask.users.model

import com.bronski.android.antortask.core.data.UserDao
import com.bronski.android.antortask.core.model.BaseRepoImpl
import javax.inject.Inject

class UsersRepoImpl @Inject constructor(
    userDao: UserDao,
) : BaseRepoImpl(userDao = userDao), IUsersRepo