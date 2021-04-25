package com.dagger.devtermquiz.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


abstract class BaseFragment<T: ViewDataBinding, R : BaseViewModel<*>>: Fragment() {
    lateinit var mActivity: BaseActivity<*, *>

    lateinit var viewDataBinding: T

    /**
     * setContentView로 호출할 Layout의 리소스 Id.
     * ex) R.layout.activity_sbs_main
     */
    abstract val layoutResourceId: Int

    /**
     * viewModel 로 쓰일 변수.
     */
    abstract val viewModel: R

//    abstract val bindingVariableVM: Int

//    abstract val bindingVariableActivity: Int

    /**
     * 레이아웃을 띄운 직후 호출.
     * 뷰나 액티비티의 속성 등을 초기화.
     * ex) 리사이클러뷰, 툴바, 드로어뷰..
     */
    abstract fun initView()

    abstract fun onProcess()

    abstract fun onViewModelCleared()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            this.mActivity = context
        }
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)

//        viewDataBinding.setVariable(bindingVariableVM, viewModel)
//        viewDataBinding.setVariable(bindingVariableActivity, mActivity)
        viewDataBinding.lifecycleOwner = this

        // viewDataBinding.executePendingBindings()

        return viewDataBinding.root
    }

    override fun onDetach() {
        onViewModelCleared()
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        onProcess()
    }

    fun getBaseActivity(): BaseActivity<*, *> {
        return mActivity
    }
}