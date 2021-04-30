package com.dagger.devtermquiz.view.main.quiz

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import com.dagger.devtermquiz.Constants
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.base.BaseFragment
import com.dagger.devtermquiz.databinding.FragmentMainBinding
import com.dagger.devtermquiz.ext.gone
import com.dagger.devtermquiz.ext.show
import com.dagger.devtermquiz.listener.AwesomeDialogListener
import com.dagger.devtermquiz.model.fav.Favorite
import com.dagger.devtermquiz.model.request.fail.RequestFail
import com.dagger.devtermquiz.utility.CustomProgressDialog
import com.dagger.devtermquiz.utility.Utility
import com.dagger.devtermquiz.view.main.quiz.model.QuizFragmentViewModel
import com.kaushikthedeveloper.doublebackpress.DoubleBackPress
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject


class QuizFragment : BaseFragment<FragmentMainBinding, QuizFragmentViewModel>(),
    QuizFragmentNavigator.View {
    override val layoutResourceId: Int get() = R.layout.fragment_main
    override val viewModel: QuizFragmentViewModel by inject()
    private  val utility         : Utility by inject()
    private  val doubleBackPress : DoubleBackPress by inject()

    lateinit var buttonList: Array<Button>
    lateinit var progress: CustomProgressDialog
    var totalCountQuestion = Prefs.getInt(Constants.PREFS_TOTAL_QUESTION_COUNT, 1)
    var todayCountQuestion = Prefs.getInt(Constants.PREFS_QUESTION_COUNT, 1)

    @SuppressLint("SetTextI18n")
    override fun initView() {
        viewModel.setNavigator(this)

        buttonList = arrayOf(btn_ex1, btn_ex2, btn_ex3, btn_ex4)

        doubleBackPress.setDoubleBackPressAction { finishAffinity(mActivity) }

        mActivity.setSupportActionBar(toolbar)
        if(mActivity.supportActionBar != null) {
            mActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            mActivity.supportActionBar!!.setDisplayShowTitleEnabled(false);
        }

//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.setStatusBarColor(Color.BLUE)


        progress = CustomProgressDialog(
            context = mActivity,
            message = "잠시만 기다려주세요.",
            cancelable = false
        )
        progress.show()

        viewDataBinding.run {
            lifecycleOwner = this@QuizFragment
            activity = this@QuizFragment
            vm = viewModel
        }

        txt_question_count.text = "$todayCountQuestion / 10"
    }

    override fun onProcess() {
        viewModel.onLoadSearchSingleQuizData(id = totalCountQuestion)

        viewModel.searchSingleQuizData.observe(mActivity, Observer { searchQuiz ->
            for (i in buttonList.indices) {
                buttonList[i].background = ContextCompat.getDrawable(
                    mActivity,
                    R.drawable.book_mark_detail_background
                )
                if (i == searchQuiz.answer) {
                    // 정답 맞췄을때
                    buttonList[i].setOnClickListener {
                        buttonList[i].background = ContextCompat.getDrawable(
                            mActivity,
                            R.drawable.book_mark_detail_answer_background
                        )
                        utility.answerDialog(
                            activity = mActivity,
                            cancelable = false,
                            listener = object : AwesomeDialogListener {
                                override fun onConfirmClick() {
                                    btn_next.show()
                                    slRealBottom.show()
                                }

                                override fun onAddBookMarkClick() {
                                    viewModel.onInsertFavoriteData(
                                        Favorite(
                                            question = searchQuiz.question,
                                            id = searchQuiz.id,
                                            answer = searchQuiz.answer,
                                            firstExample = searchQuiz.firstExample,
                                            secondExample = searchQuiz.secondExample,
                                            thirdExample = searchQuiz.thirdExample,
                                            fourthExample = searchQuiz.fourthExample,
                                            firstCommentary = searchQuiz.firstCommentary,
                                            secondCommentary = searchQuiz.secondCommentary,
                                            thirdCommentary = searchQuiz.thirdCommentary,
                                            fourthCommentary = searchQuiz.fourthCommentary
                                        )
                                    )
                                    btn_next.show()
                                }
                            }
                        )
                    }

                } else {
                    buttonList[i].setOnClickListener {
                        utility.wrongAnswerDialog(
                            context = mActivity,
                            title = "오답입니다.",
                            cancelable = true,
                            message = "다른 답을 찾아보세요.",
                            buttonText = "확인"
                        )
                    }
                }
            }

            if (todayCountQuestion == 10) {
                utility.answerDialog(
                    activity = mActivity,
                    cancelable = true,
                    listener = object : AwesomeDialogListener {
                        override fun onConfirmClick() {
                        }

                        override fun onAddBookMarkClick() {
                        }
                    })
            }

        })
    }

    /**
     * @author : 이수현
     * @Date : 4/14/21 11:30 PM
     * @Description : 다음 문제 호출 && 문제 카운팅
     * @History :
     *
     **/
    @SuppressLint("SetTextI18n")
    fun onRepeatRequestSingleQuizData() {
        progress.show()
        todayCountQuestion++
        totalCountQuestion++

        btn_next.gone()
        slRealBottom.gone()

        viewModel.onLoadSearchSingleQuizData(id = totalCountQuestion)
        Prefs.putInt(Constants.PREFS_QUESTION_COUNT, todayCountQuestion)
        Prefs.putInt(Constants.PREFS_TOTAL_QUESTION_COUNT, totalCountQuestion)

        txt_question_count.text = "$todayCountQuestion / 10"
    }

    companion object {
        fun newInstance(position: Int): QuizFragment {
            val instance = QuizFragment()
            val args = Bundle()
            args.putInt("position", position)
            instance.arguments = args
            return instance
        }
    }

    override fun dismissProgress() {
        if(progress.isShowing) progress.dismiss()
    }

    override fun onExhaustQuiz() {
        utility.exhaustQuestionDialog(activity = mActivity, cancelabel = true)
    }

    override fun onRequestFailed(requestFail: RequestFail) {
    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }
}