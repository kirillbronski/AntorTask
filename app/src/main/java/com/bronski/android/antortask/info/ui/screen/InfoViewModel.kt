package com.bronski.android.antortask.info.ui.screen

import androidx.lifecycle.ViewModel
import com.bronski.android.antortask.info.model.IInfoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val infoRepoImpl: IInfoRepo,
) : ViewModel() {
    // TODO: Implement the ViewModel
}