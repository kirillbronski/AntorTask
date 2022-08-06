package com.bronski.android.antortask.info.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bronski.android.antortask.R
import com.bronski.android.antortask.core.state.ViewState
import com.bronski.android.antortask.core.ui.BaseFragment
import com.bronski.android.antortask.databinding.FragmentInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : BaseFragment<FragmentInfoBinding>() {

    private val viewModel by viewModels<InfoViewModel>()

    override fun getViewBinding() = FragmentInfoBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkViewState()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getDataFromRoom()
    }

    @SuppressLint("SetTextI18n")
    private fun observeOnUsersList() {
        lifecycleScope.launchWhenStarted {
            viewModel.usersList.collect { listUser ->
                if (listUser.isNotEmpty()) {
                    with(binding) {
                        totalEntriesTextView.text =
                            getString(R.string.total_entries) + listUser.size.toString()
                        firstCreationTextView.text =
                            getString(R.string.first_entry) + listUser.first().dateAndTime
                        lastCreationTextView.text =
                            getString(R.string.last_entry) + listUser.last().dateAndTime
                    }
                }
            }
        }
    }

    private fun checkViewState() {
        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect {
                when (it) {
                    is ViewState.SuccessState -> {
                        observeOnUsersList()
                        hideProgressIndicator(progressBar = binding.progressBarId.commonPb)
                    }
                    is ViewState.LoadingState -> {
                        showProgressIndicator(progressBar = binding.progressBarId.commonPb)
                    }
                    is ViewState.ErrorState -> {
                        showToastMessage(text = it.message)
                        hideProgressIndicator(progressBar = binding.progressBarId.commonPb)
                    }
                    else -> {}
                }
            }
        }
    }
}