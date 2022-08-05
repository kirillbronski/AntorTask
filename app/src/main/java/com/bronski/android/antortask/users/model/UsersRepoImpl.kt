package com.bronski.android.antortask.users.model

import com.bronski.android.antortask.core.data.UserDao
import javax.inject.Inject

class UsersRepoImpl @Inject constructor(
    private val userDao: UserDao,
) : IUsersRepo {

}