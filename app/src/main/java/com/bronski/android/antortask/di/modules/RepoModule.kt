package com.bronski.android.antortask.di.modules

import com.bronski.android.antortask.core.data.UserDao
import com.bronski.android.antortask.createuser.model.CreateUserRepoImpl
import com.bronski.android.antortask.createuser.model.ICreateUserRepo
import com.bronski.android.antortask.info.model.IInfoRepo
import com.bronski.android.antortask.info.model.InfoRepoImpl
import com.bronski.android.antortask.manage.model.IManageUserRepo
import com.bronski.android.antortask.manage.model.ManageUserRepoImpl
import com.bronski.android.antortask.users.model.IUsersRepo
import com.bronski.android.antortask.users.model.UsersRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    fun createUserRepo(userDao: UserDao): ICreateUserRepo {
        return CreateUserRepoImpl(userDao)
    }

    @Provides
    fun usersRepo(userDao: UserDao): IUsersRepo {
        return UsersRepoImpl(userDao)
    }

    @Provides
    fun manageUsersRepo(userDao: UserDao): IManageUserRepo {
        return ManageUserRepoImpl(userDao)
    }

    @Provides
    fun infoRepo(userDao: UserDao): IInfoRepo {
        return InfoRepoImpl(userDao)
    }

}