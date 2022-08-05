package com.bronski.android.antortask.core.state

sealed class ViewState {
    object DefaultState : ViewState()
    object LoadingState : ViewState()
    object SuccessState : ViewState()
    class ErrorState(var message: String?) : ViewState()
}
