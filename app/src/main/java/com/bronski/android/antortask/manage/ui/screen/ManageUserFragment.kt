package com.bronski.android.antortask.manage.ui.screen

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
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
import com.bronski.android.antortask.databinding.FragmentManageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ManageUserFragment : BaseFragment<FragmentManageBinding>() {

    private val viewModel by viewModels<ManageUserViewModel>()

    private val recyclerItemListener = object : RecyclerItemListener {
        override fun deleteUser(itemUser: UserEntity) {
            showAlertMessageDeleteUser(userEntity = itemUser)
        }
    }

    private val usersAdapter = UsersAdapter(recyclerItemListener, true)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOptionsMenu()
        setupAdapter()
        checkViewState()
        observeOnUsersList()
    }

    private fun setupOptionsMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.delete_all_data -> {
                        viewModel.deleteAllUsers()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getDataFromRoom()
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
                usersAdapter.submitList(usersList = it)
            }
        }
    }

    private fun checkViewState() {
        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collectLatest {
                when (it) {
                    is ViewState.SuccessState -> {
                        hideProgressIndicator(progressBar = binding.progressBarId.commonPb)
                    }
                    is ViewState.DeleteUserState -> {
                        hideProgressIndicator(progressBar = binding.progressBarId.commonPb)
                        displayInfoFragment()
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

    private fun setupAdapter() = with(binding) {
        usersRecycler.apply {
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(requireContext())
            (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        }
    }

    private fun displayInfoFragment() {
        findNavController().navigate(R.id.action_manageFragment_to_infoFragment)
    }

    override fun getViewBinding() = FragmentManageBinding.inflate(layoutInflater)

}