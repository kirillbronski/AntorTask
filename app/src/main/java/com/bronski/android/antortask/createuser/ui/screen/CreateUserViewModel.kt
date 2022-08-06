package com.bronski.android.antortask.createuser.ui.screen

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.bronski.android.antortask.core.data.UserEntity
import com.bronski.android.antortask.core.state.ViewState
import com.bronski.android.antortask.createuser.model.ICreateUserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CreateUserViewModel @Inject constructor(
    private val createUserRepoImpl: ICreateUserRepo,
    private val compositeDisposable: CompositeDisposable,
) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState>(ViewState.DefaultState)
    val viewState: StateFlow<ViewState?> = _viewState.asStateFlow()

    private val _buttonState = MutableStateFlow(false)
    val buttonState: StateFlow<Boolean> = _buttonState.asStateFlow()

    private var name = MutableStateFlow("")
    private var email = MutableStateFlow("")
    private var phone = MutableStateFlow("")

    fun setFieldsValue(
        name: String = "",
        email: String = "",
        phone: String = "",
    ) {
        name.also {
            if (it != "") {
                this.name.value = it
            }
        }
        email.also {
            if (it != "") {
                this.email.value = it
            }
        }
        phone.also {
            if (it != "") {
                this.phone.value = it
            }
        }
        _buttonState.value = (isValidEmail()
                && isValidPhone() && isValidName())
    }

    fun addUser() {
        _viewState.value = ViewState.LoadingState
        createUserRepoImpl.addUser(
            UserEntity(
                name = name.value,
                email = email.value,
                phone = phone.value,
                dateAndTime = getDataAndTime()
            )
        )
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onComplete() {
                    _viewState.value = ViewState.SuccessState
                }

                override fun onError(e: Throwable) {
                    _viewState.value = ViewState.ErrorState(message = e.message)
                }
            })
    }

    private fun getDataAndTime(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val current = LocalDateTime.now()
        return current.format(formatter)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    private fun isValidEmail(): Boolean =
        !TextUtils.isEmpty(email.value) && Patterns.EMAIL_ADDRESS.matcher(email.value).matches()

    private fun isValidPhone(): Boolean =
        !TextUtils.isEmpty(phone.value) && Patterns.PHONE.matcher(phone.value)
            .matches() && phone.value.length >= 12

    private fun isValidName(): Boolean =
        !TextUtils.isEmpty(name.value) && name.value.length >= 2
}