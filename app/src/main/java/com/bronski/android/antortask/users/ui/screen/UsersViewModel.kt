package com.bronski.android.antortask.users.ui.screen

import androidx.lifecycle.ViewModel
import com.bronski.android.antortask.users.model.IUsersRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersRepoImpl: IUsersRepo,
) : ViewModel() {
    // TODO: Implement the ViewModel
}