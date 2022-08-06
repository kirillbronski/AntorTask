package com.bronski.android.antortask.users.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bronski.android.antortask.R
import com.bronski.android.antortask.core.data.UserEntity
import com.bronski.android.antortask.core.state.ViewState
import com.bronski.android.antortask.core.ui.BaseFragment
import com.bronski.android.antortask.core.ui.adapter.RecyclerItemListener
import com.bronski.android.antortask.core.ui.adapter.UsersAdapter
import com.bronski.android.antortask.databinding.FragmentUsersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : BaseFragment<FragmentUsersBinding>() {

    private val viewModel by viewModels<UsersViewModel>()
    private val usersAdapter = UsersAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        checkViewState()
        observeOnUsersList()
        binding.addUserButton.setOnClickListener { displayCreateUserFragment() }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getDataFromRoom()
    }

    private fun observeOnUsersList() {
        lifecycleScope.launchWhenStarted {
            viewModel.usersList.collect {
                usersAdapter.submitList(it)
            }
        }
    }

    private fun checkViewState() {
        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect {
                when (it) {
                    is ViewState.SuccessState -> {
                        hideProgressIndicator(binding.progressBarId.commonPb)
                    }
                    is ViewState.LoadingState -> {
                        showProgressIndicator(binding.progressBarId.commonPb)
                    }
                    is ViewState.ErrorState -> {
                        showToastMessage(it.message)
                        hideProgressIndicator(binding.progressBarId.commonPb)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setupAdapter() = with(binding) {
        usersRecycler.apply {
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(requireContext())
            (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        }
    }

    override fun getViewBinding() = FragmentUsersBinding.inflate(layoutInflater)

    private fun displayCreateUserFragment() {
        findNavController().navigate(R.id.action_usersFragment_to_createUserFragment)
    }

}