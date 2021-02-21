package com.dagger.devtermquiz.base

import android.os.Bundle
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

        initView()

        onProcess()
    }

    override fun onDestroy() {
        onViewModelCleared()
        super.onDestroy()
    }
}