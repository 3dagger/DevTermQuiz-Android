package com.dagger.devtermquiz.view.main.setting

import android.os.Bundle
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.base.BaseFragment
import com.dagger.devtermquiz.databinding.FragmentSettingBinding
import com.dagger.devtermquiz.view.main.bookmark.BookMarkFragment
import com.dagger.devtermquiz.view.main.setting.model.SettingFragmentViewModel
import org.koin.android.ext.android.inject

class SettingFragment : BaseFragment<FragmentSettingBinding, SettingFragmentViewModel>(), SettingFragmentNavigator.View {
    override val layoutResourceId: Int get() = R.layout.fragment_setting
    override val viewModel: SettingFragmentViewModel by inject()

    override fun initView() {
        viewModel.setNavigator(this@SettingFragment)
    }

    override fun onProcess() {
    }

    companion object {
        fun newInstance(position: Int): SettingFragment {
            val instance = SettingFragment()
            val args = Bundle()
            args.putInt("position", position)
            instance.arguments = args
            return instance
        }
    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }
}