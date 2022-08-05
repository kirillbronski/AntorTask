package com.bronski.android.antortask.manage.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.bronski.android.antortask.core.data.UserEntity
import com.bronski.android.antortask.core.state.ViewState
import com.bronski.android.antortask.manage.model.IManageUserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.CompletableObserver
import io.reactivex.MaybeObserver
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
) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState>(ViewState.DefaultState)
    val viewState: StateFlow<ViewState?> = _viewState.asStateFlow()

    private val _usersList = MutableStateFlow<List<UserEntity>>(emptyList())
    val usersList: StateFlow<List<UserEntity>> = _usersList.asStateFlow()

    fun getAllDataFromDatabase() {
        _viewState.value = ViewState.LoadingState
        manageUserRepoImpl.getAllDataFromDatabase()
            .subscribe(object : MaybeObserver<List<UserEntity>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(usersList: List<UserEntity>) {
                    Log.d("AAAAAAAA", "onSuccess: ${usersList.size}")
                    _usersList.value = usersList
                    _viewState.value = ViewState.SuccessState
                }

                override fun onError(e: Throwable) {
                    _viewState.value = ViewState.ErrorState(e.message)
                }

                override fun onComplete() {
                    _viewState.value = ViewState.SuccessState
                }

            })
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