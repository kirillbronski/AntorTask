package com.bronski.android.antortask.di.modules

import android.content.Context
import androidx.room.Room
import com.bronski.android.antortask.core.data.UserDao
import com.bronski.android.antortask.core.data.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.disposables.CompositeDisposable

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    fun providesUserDao(@ApplicationContext appContext: Context): UserDao {
        return Room.databaseBuilder(appContext, UserDatabase::class.java, DATABASE_NAME).build()
            .userDao()
    }

    @Provides
    fun providesCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    companion object {
        private const val DATABASE_NAME = "database"
    }
}