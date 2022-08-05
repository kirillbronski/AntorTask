package com.bronski.android.antortask.users.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bronski.android.antortask.R
import com.bronski.android.antortask.core.ui.BaseFragment
import com.bronski.android.antortask.databinding.FragmentUsersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : BaseFragment<FragmentUsersBinding>() {

    private val viewModel by viewModels<UsersViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addUserButton.setOnClickListener { displayCreateUserFragment() }
    }

    override fun getViewBinding() = FragmentUsersBinding.inflate(layoutInflater)

    private fun displayCreateUserFragment() {
        findNavController().navigate(R.id.action_usersFragment_to_createUserFragment)
    }

}