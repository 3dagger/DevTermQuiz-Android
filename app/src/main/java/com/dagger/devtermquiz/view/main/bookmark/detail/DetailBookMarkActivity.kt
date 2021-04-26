package com.dagger.devtermquiz.view.main.bookmark.detail

import androidx.core.content.ContextCompat
import com.dagger.devtermquiz.Constants
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.base.BaseActivity
import com.dagger.devtermquiz.databinding.ActivityDetailBoookmarkBinding
import com.dagger.devtermquiz.view.main.bookmark.detail.model.DetailBookMarkViewModel
import kotlinx.android.synthetic.main.activity_detail_boookmark.*
import org.koin.android.ext.android.inject

class DetailBookMarkActivity : BaseActivity<ActivityDetailBoookmarkBinding, DetailBookMarkViewModel>(),
    DetailBookMarkNavigator.View {
    override val viewModel: DetailBookMarkViewModel by inject()
    override val layoutResourceId: Int get() = R.layout.activity_detail_boookmark

    var question            : String? = null
    var firstExample        : String? = null
    var secondExample       : String? = null
    var thirdExample        : String? = null
    var fourthExample       : String? = null
    var firstCommentary     : String? = null
    var secondCommentary    : String? = null
    var thirdCommentary     : String? = null
    var fourthCommentary    : String? = null
    var answer              : Int? = null

    override fun initView() {
        viewModel.setNavigator(this@DetailBookMarkActivity)
        viewDataBinding.run {
            lifecycleOwner = this@DetailBookMarkActivity
            activity = this@DetailBookMarkActivity
            vm = viewModel
        }
        if(intent != null) {
            question = intent.getStringExtra(Constants.INTENT_ARGUMENT_BOOK_MARK_QUESTION)
            answer = intent.getIntExtra(Constants.INTENT_ARGUMENT_BOOK_MARK_ANSWER, 0)
            firstExample = intent.getStringExtra(Constants.INTENT_ARGUMENT_BOOK_MARK_FIRST_EXAMPLE)
            secondExample = intent.getStringExtra(Constants.INTENT_ARGUMENT_BOOK_MARK_SECOND_EXAMPLE)
            thirdExample = intent.getStringExtra(Constants.INTENT_ARGUMENT_BOOK_MARK_THIRD_EXAMPLE)
            fourthExample = intent.getStringExtra(Constants.INTENT_ARGUMENT_BOOK_MARK_FOURTH_EXAMPLE)
            firstCommentary = intent.getStringExtra(Constants.INTENT_ARGUMENT_BOOK_MARK_FIRST_COMMENTARY)
            secondCommentary = intent.getStringExtra(Constants.INTENT_ARGUMENT_BOOK_MARK_SECOND_COMMENTARY)
            thirdCommentary = intent.getStringExtra(Constants.INTENT_ARGUMENT_BOOK_MARK_THIRD_COMMENTARY)
            fourthCommentary = intent.getStringExtra(Constants.INTENT_ARGUMENT_BOOK_MARK_FOURTH_COMMENTARY)
        }
    }

    override fun onProcess() {
        val btnArray = arrayOf(btn_dbm_ex1, btn_dbm_ex2, btn_dbm_ex3, btn_dbm_ex4)
        for(i in btnArray.indices) {
            btnArray[answer!!].background = ContextCompat.getDrawable(this@DetailBookMarkActivity, R.drawable.book_mark_detail_answer_background)
        }
    }


    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }
}