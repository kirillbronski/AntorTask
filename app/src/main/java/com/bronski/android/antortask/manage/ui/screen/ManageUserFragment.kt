package com.bronski.android.antortask.manage.ui.screen

import androidx.fragment.app.viewModels
import com.bronski.android.antortask.core.ui.BaseFragment
import com.bronski.android.antortask.databinding.FragmentManageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageUserFragment : BaseFragment<FragmentManageBinding>() {

    private val viewModel by viewModels<ManageUserViewModel>()

    override fun getViewBinding() = FragmentManageBinding.inflate(layoutInflater)

}