package com.dagger.devtermquiz.view.main

import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.adapter.PagerRecyclerAdapter
import com.dagger.devtermquiz.base.BaseActivity
import com.dagger.devtermquiz.databinding.ActivityMainBinding
import com.dagger.devtermquiz.utility.CustomProgressDialog
import com.dagger.devtermquiz.view.main.model.MainViewModel
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading_view.*
import kotlinx.android.synthetic.main.question_item.*
import org.koin.android.ext.android.inject


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator.View {

    override val layoutResourceId: Int get() = R.layout.activity_main
    override val viewModel: MainViewModel by inject()
    lateinit var progress: CustomProgressDialog

    override fun initView() {
        viewModel.setNavigator(this)

        progress = CustomProgressDialog(
            context = this@MainActivity,
            message = "잠시만 기다려주세요.",
            cancelable = false
        )
        progress.show()

        viewDataBinding.run {
            lifecycleOwner = this@MainActivity
            activity = this@MainActivity
            vm = viewModel
        }
    }

    override fun onProcess() {
        viewModel.onLoadSingleQuizData()

        viewModel.singleQuizData.observe(this@MainActivity, Observer {
//            viewPager.apply {
//                adapter = PagerRecyclerAdapter(result = it.results)
//                orientation = ViewPager2.ORIENTATION_HORIZONTAL
//            }
        })

//        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                if(position > 0) viewModel.onLoadSingleQuizData()
//            }
//        })

        viewModel.onLoadSingleQuizData()

    }

    fun onRepeatRequestSingleQuizData() {
        viewModel.onLoadSingleQuizData()
    }

    override fun dismissProgress() {
        if(progress.isShowing) progress.dismiss()
    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }
}