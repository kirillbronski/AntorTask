package com.bronski.android.antortask.core.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.bronski.android.antortask.core.data.UserEntity
import com.bronski.android.antortask.core.model.IBaseRepo
import com.bronski.android.antortask.core.state.ViewState
import io.reactivex.MaybeObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel<T: IBaseRepo>(
    private val repository: T,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    protected fun getAllDataFromDatabase(
        viewState: MutableStateFlow<ViewState>,
        usersListFlow: MutableStateFlow<List<UserEntity>>,
    ) {
        viewState.value = ViewState.LoadingState
        repository.getAllDataFromDatabase()
            .subscribe(object : MaybeObserver<List<UserEntity>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(usersList: List<UserEntity>) {
                    usersListFlow.value = usersList
                    viewState.value = ViewState.SuccessState
                }

                override fun onError(e: Throwable) {
                    viewState.value = ViewState.ErrorState(e.message)
                }

                override fun onComplete() {
                    viewState.value = ViewState.SuccessState
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}