package com.bronski.android.antortask.manage.ui.screen

import androidx.lifecycle.ViewModel
import com.bronski.android.antortask.manage.model.IManageUserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManageUserViewModel @Inject constructor(
    private val manageUserRepoImpl: IManageUserRepo,
) : ViewModel() {
    // TODO: Implement the ViewModel
}