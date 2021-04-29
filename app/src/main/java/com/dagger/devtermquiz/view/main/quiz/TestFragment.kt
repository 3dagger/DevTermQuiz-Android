package com.dagger.devtermquiz.view.main.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.base.BaseFragment
import com.dagger.devtermquiz.databinding.FragmentTestBinding
import kotlinx.android.synthetic.main.fragment_test.*
import org.koin.android.ext.android.inject

class TestFragment : BaseFragment<FragmentTestBinding, TestFragmentViewModel>(), TestFragmentNavigator.View{
    override val layoutResourceId: Int get() = R.layout.fragment_test
    override val viewModel: TestFragmentViewModel by inject()

    override fun initView() {
        viewModel.setNavigator(this)

        mActivity.setSupportActionBar(toorbar)
        collpasebar.title = "테스트 타이틀"
    }

    override fun onProcess() {
    }

    companion object {
        fun newInstance(position: Int): TestFragment {
            val instance = TestFragment()
            val args = Bundle()
            args.putInt("position", position)
            instance.arguments = args
            return instance
        }
    }

    override fun onViewModelCleared() {
    }
}