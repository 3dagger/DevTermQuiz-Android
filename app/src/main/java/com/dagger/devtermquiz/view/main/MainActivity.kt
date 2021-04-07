package com.dagger.devtermquiz.view.main

import androidx.lifecycle.Observer
import com.dagger.devtermquiz.BR
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.base.BaseActivity
import com.dagger.devtermquiz.base.BaseRecyclerView
import com.dagger.devtermquiz.databinding.ActivityMainBinding
import com.dagger.devtermquiz.databinding.QuestionItemBinding
import com.dagger.devtermquiz.model.django.quiz.SingleQuizResults
import com.dagger.devtermquiz.view.main.model.MainViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.question_item.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator.View {

    override val layoutResourceId: Int get() = R.layout.activity_main
    override val viewModel: MainViewModel by inject()


    override fun initView() {
        viewModel.setNavigator(this)

        viewDataBinding.run {
            lifecycleOwner = this@MainActivity

            activity = this@MainActivity
            vm = viewModel

            recyclerView.adapter = object : BaseRecyclerView.Adapter<List<SingleQuizResults>, QuestionItemBinding>(
               layoutResId = R.layout.question_item,
               bindingVariableId = BR.questionListData
            ){}
        }
    }

    override fun onProcess() {
        viewModel.onLoadSingleQuizData()
        viewModel.singleQuizData.observe(this@MainActivity, Observer {
            val splitArray = it.results[0].body.split("/")
            for(i in splitArray.indices) {
            }

        })
    }

    fun onRepeatRequestSingleQuizData() {
        viewModel.onLoadSingleQuizData()
    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }
}