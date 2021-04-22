package com.dagger.devtermquiz.view.bookmark.detail

import com.dagger.devtermquiz.Constants
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.base.BaseActivity
import com.dagger.devtermquiz.databinding.ActivityDetailBoookmarkBinding
import com.dagger.devtermquiz.view.bookmark.detail.model.DetailBookMarkViewModel
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.inject

class DetailBookMarkActivity : BaseActivity<ActivityDetailBoookmarkBinding, DetailBookMarkViewModel>(), DetailBookMarkNavigator.View {
    override val viewModel: DetailBookMarkViewModel by inject()
    override val layoutResourceId: Int get() = R.layout.activity_detail_boookmark

    private var question            : String? = null
    private var firstExample        : String? = null
    private var secondExample       : String? = null
    private var thirdExample        : String? = null
    private var fourthExample       : String? = null
    private var firstCommentary     : String? = null
    private var secondCommentary    : String? = null
    private var thirdCommentary     : String? = null
    private var fourthCommentary    : String? = null
    private var answer              : Int? = null


    override fun initView() {
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
        Logger.d("question :: $question\nanswer :: $answer\nfirstExample :: $firstExample")
    }


    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }
}