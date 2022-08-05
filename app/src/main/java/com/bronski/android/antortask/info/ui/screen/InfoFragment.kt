package com.bronski.android.antortask.info.ui.screen

import androidx.fragment.app.viewModels
import com.bronski.android.antortask.core.ui.BaseFragment
import com.bronski.android.antortask.databinding.FragmentInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : BaseFragment<FragmentInfoBinding>() {

    private val viewModel by viewModels<InfoViewModel>()

    override fun getViewBinding() = FragmentInfoBinding.inflate(layoutInflater)

}