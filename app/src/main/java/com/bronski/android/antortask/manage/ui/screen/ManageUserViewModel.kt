package com.bronski.android.antortask.manage.ui.screen

import com.bronski.android.antortask.core.data.UserEntity
import com.bronski.android.antortask.core.model.IBaseRepo
import com.bronski.android.antortask.core.state.ViewState
import com.bronski.android.antortask.core.ui.BaseViewModel
import com.bronski.android.antortask.manage.model.IManageUserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ManageUserViewModel @Inject constructor(
    private val manageUserRepoImpl: IManageUserRepo,
    private val compositeDisposable: CompositeDisposable,
) : BaseViewModel<IBaseRepo>(manageUserRepoImpl, compositeDisposable) {

    private val _viewState = MutableStateFlow<ViewState>(ViewState.DefaultState)
    var viewState: StateFlow<ViewState?> = _viewState.asStateFlow()

    private val _usersList = MutableStateFlow<List<UserEntity>>(emptyList())
    val usersList: StateFlow<List<UserEntity>> = _usersList.asStateFlow()

    fun getDataFromRoom() {
        getAllDataFromDatabase(_viewState, _usersList)
    }

    fun deleteUser(userEntity: UserEntity){
        manageUserRepoImpl.deleteUser(userEntity).subscribe(object : CompletableObserver{
            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }

            override fun onComplete() {
                _viewState.value = ViewState.SuccessState
            }

            override fun onError(e: Throwable) {
                _viewState.value = ViewState.ErrorState(e.message)
            }

        })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}