package com.dagger.devtermquiz.base

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding, R : BaseViewModel<*>> : AppCompatActivity() {
    lateinit var viewDataBinding    : T
    abstract val viewModel          : R
    abstract val layoutResourceId   : Int

    abstract fun initView()
    abstract fun onProcess()
    abstract fun onViewModelCleared()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, layoutResourceId)

        // 이부분은 StatusBar 부분을 투명하게 고정시킬 수 있음

//        val window = window
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        initView()

        onProcess()
    }

    override fun onDestroy() {
        onViewModelCleared()
        super.onDestroy()
    }
}