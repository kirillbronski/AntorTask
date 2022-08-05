package com.bronski.android.antortask.createuser.ui.screen

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bronski.android.antortask.R
import com.bronski.android.antortask.core.state.ViewState
import com.bronski.android.antortask.core.ui.BaseFragment
import com.bronski.android.antortask.databinding.FragmentCreateUserBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateUserFragment : BaseFragment<FragmentCreateUserBinding>() {

    private val viewModel by viewModels<CreateUserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createButton.setOnClickListener {
            viewModel.addUser()
        }

        textFieldsListener()
        checkButtonState()
        checkViewState()
    }

    private fun checkViewState() {
        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect {
                when (it) {
                    is ViewState.SuccessState -> {
                        showToastMessage("Success")
                        hideProgressIndicator(binding.progressBarId.commonPb)
                        displayUsersFragment()
                    }
                    is ViewState.LoadingState -> {
                        showProgressIndicator(binding.progressBarId.commonPb)
                    }
                    is ViewState.ErrorState -> {
                        showToastMessage(it.message)
                        hideProgressIndicator(binding.progressBarId.commonPb)
                    }
                    else -> {
                        ViewState.DefaultState
                    }
                }
            }
        }
    }

    private fun textFieldsListener() {

        binding.nameEditText.addTextChangedListener {
            viewModel.setFieldsValue(name = it.toString())
        }

        binding.emailEditText.addTextChangedListener {
            viewModel.setFieldsValue(email = it.toString())
        }

        binding.phoneEditText.addTextChangedListener {
            viewModel.setFieldsValue(phone = it.toString())
        }
    }

    private fun checkButtonState() {
        lifecycleScope.launchWhenStarted {
            viewModel.buttonState.collect {
                when (it) {
                    true -> {
                        binding.createButton.isEnabled = true
                    }
                    false -> {
                        binding.createButton.isEnabled = false
                    }
                }
            }
        }
    }

    private fun displayUsersFragment() {
        findNavController().navigate(R.id.action_createUserFragment_to_usersFragment)
    }

    override fun getViewBinding() = FragmentCreateUserBinding.inflate(layoutInflater)

}