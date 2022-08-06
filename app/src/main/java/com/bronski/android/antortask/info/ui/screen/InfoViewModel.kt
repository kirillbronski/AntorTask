package com.bronski.android.antortask.info.ui.screen

import com.bronski.android.antortask.core.data.UserEntity
import com.bronski.android.antortask.core.model.IBaseRepo
import com.bronski.android.antortask.core.state.ViewState
import com.bronski.android.antortask.core.ui.BaseViewModel
import com.bronski.android.antortask.info.model.IInfoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    infoRepoImpl: IInfoRepo,
    compositeDisposable: CompositeDisposable,
) : BaseViewModel<IBaseRepo>(infoRepoImpl, compositeDisposable) {

    private val _viewState = MutableStateFlow<ViewState>(ViewState.DefaultState)
    var viewState: StateFlow<ViewState?> = _viewState.asStateFlow()

    private val _usersList = MutableStateFlow<List<UserEntity>>(emptyList())
    val usersList: StateFlow<List<UserEntity>> = _usersList.asStateFlow()

    fun getDataFromRoom() {
        getAllDataFromDatabase(viewState = _viewState, usersListFlow = _usersList)
    }
}