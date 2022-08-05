package com.bronski.android.antortask.manage.ui.screen

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bronski.android.antortask.core.data.UserEntity
import com.bronski.android.antortask.core.state.ViewState
import com.bronski.android.antortask.core.ui.BaseFragment
import com.bronski.android.antortask.core.ui.adapter.RecyclerItemListener
import com.bronski.android.antortask.core.ui.adapter.UsersAdapter
import com.bronski.android.antortask.databinding.FragmentManageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageUserFragment : BaseFragment<FragmentManageBinding>() {

    private val viewModel by viewModels<ManageUserViewModel>()

    private val recyclerItemListener = object : RecyclerItemListener {

        override fun deleteUser(itemUser: UserEntity) {
            showAlertMessageDeleteUser(itemUser)
        }
    }

    private val usersAdapter = UsersAdapter(recyclerItemListener, true)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        checkViewState()
        observeOnUsersList()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllDataFromDatabase()
    }

    private fun showAlertMessageDeleteUser(userEntity: UserEntity) =
        AlertDialog.Builder(requireContext())
            .setTitle("Delete user from DB")
            .setMessage("Are you sure you want to delete ${userEntity.name}?")
            .setCancelable(false)
            .setPositiveButton("Apply") { _, _ ->
                viewModel.deleteUser(userEntity)
            }
            .setNegativeButton("Cancel") { _, _ ->
                showToastMessage("Cancel")
            }
            .create()
            .show()

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

    override fun getViewBinding() = FragmentManageBinding.inflate(layoutInflater)

}