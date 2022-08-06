package com.bronski.android.antortask.users.ui.screen

import com.bronski.android.antortask.core.data.UserEntity
import com.bronski.android.antortask.core.model.IBaseRepo
import com.bronski.android.antortask.core.state.ViewState
import com.bronski.android.antortask.core.ui.BaseViewModel
import com.bronski.android.antortask.users.model.IUsersRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    usersRepoImpl: IUsersRepo,
    compositeDisposable: CompositeDisposable,
) : BaseViewModel<IBaseRepo>(usersRepoImpl, compositeDisposable) {

    private val _viewState = MutableStateFlow<ViewState>(ViewState.DefaultState)
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    private val _usersList = MutableStateFlow<List<UserEntity>>(emptyList())
    val usersList: StateFlow<List<UserEntity>> = _usersList.asStateFlow()

    fun getDataFromRoom() {
        getAllDataFromDatabase(viewState = _viewState, usersListFlow = _usersList)
    }
}