package com.dagger.devtermquiz.view.main

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.dagger.devtermquiz.Constants
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.base.BaseActivity
import com.dagger.devtermquiz.databinding.ActivityMainBinding
import com.dagger.devtermquiz.ext.gone
import com.dagger.devtermquiz.ext.openActivity
import com.dagger.devtermquiz.ext.show
import com.dagger.devtermquiz.ext.toast
import com.dagger.devtermquiz.listener.AwesomeDialogListener
import com.dagger.devtermquiz.model.fav.Favorite
import com.dagger.devtermquiz.utility.CustomProgressDialog
import com.dagger.devtermquiz.utility.Utility
import com.dagger.devtermquiz.view.bookmark.BookMarkActivity
import com.dagger.devtermquiz.view.main.model.MainViewModel
import com.example.awesomedialog.*
import com.kaushikthedeveloper.doublebackpress.DoubleBackPress
import com.pixplicity.easyprefs.library.Prefs
import com.skydoves.expandablelayout.ExpandableLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading_view.*
import kotlinx.android.synthetic.main.question_item.*
import org.koin.android.ext.android.inject
import uk.co.markormesher.android_fab.SpeedDialMenuAdapter
import uk.co.markormesher.android_fab.SpeedDialMenuItem
import java.util.*


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator.View{
    override val layoutResourceId: Int get() = R.layout.activity_main

    override val viewModel       : MainViewModel    by inject()
    private  val utility         : Utility          by inject()
    private  val doubleBackPress : DoubleBackPress  by inject()

    var totalCountQuestion = Prefs.getInt(Constants.PREFS_TOTAL_QUESTION_COUNT, 1)
    var todayCountQuestion = Prefs.getInt(Constants.PREFS_QUESTION_COUNT, 1)

//    lateinit var expandList: Array<ExpandableLayout>
    lateinit var buttonList: Array<Button>
    lateinit var progress: CustomProgressDialog

    private val fabDialMenuAdapter = object : SpeedDialMenuAdapter() {
        override fun getCount(): Int = 2

        override fun getMenuItem(context: Context, position: Int): SpeedDialMenuItem = when (position) {
            0 -> SpeedDialMenuItem(context, R.drawable.ic_drawer_icon, "즐겨찾기")
            1 -> SpeedDialMenuItem(context, R.drawable.ic_arrow_down, "설정")
            else -> throw IllegalArgumentException("No menu item: $position")
        }

        override fun onMenuItemClick(position: Int): Boolean {
            toast("menu click :: $position")
            when (position) {
                0 -> {
                    openActivity(BookMarkActivity::class.java)
                }
                1 -> {

                }
            }
            return true
        }

    }

    override fun initView() {
        viewModel.setNavigator(this)

        buttonList = arrayOf(btn_ex1, btn_ex2, btn_ex3, btn_ex4)

        fab.speedDialMenuAdapter = fabDialMenuAdapter
        fab.setContentCoverColour(0xcc8b575c.toInt())

        doubleBackPress.setDoubleBackPressAction { finishAffinity() }

        // 최초 접속
        if (Prefs.getBoolean(Constants.PREFS_USER_FIRST_ENTRY, true)) {
            Prefs.putString(Constants.PREFS_NOW_STRING, utility.getNowDate())
            Prefs.putBoolean(Constants.PREFS_USER_FIRST_ENTRY, false)
            Prefs.putInt(Constants.PREFS_QUESTION_COUNT, 1)
        }

        // 00시 된 후 실행했을때
        if (utility.getNowDate() != Prefs.getString(Constants.PREFS_NOW_STRING, "")) {
            Prefs.putInt(Constants.PREFS_QUESTION_COUNT, 1)
            Prefs.putString(Constants.PREFS_NOW_STRING, utility.getNowDate())
        }else {
//            Logger.d("같음")

        }

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

        txt_question_count.text = "$todayCountQuestion / 10"
    }

    override fun onProcess() {
        viewModel.onLoadSearchSingleQuizData(id = totalCountQuestion)

        viewModel.searchSingleQuizData.observe(this@MainActivity, Observer { searchQuiz ->
            for (i in buttonList.indices) {
                buttonList[i].background = ContextCompat.getDrawable(this, R.drawable.book_mark_detail_background)
                if (i == searchQuiz.answer) {
                    // 정답 맞췄을때
                    buttonList[i].setOnClickListener {
                        buttonList[i].background = ContextCompat.getDrawable(this, R.drawable.book_mark_detail_answer_background)
                        utility.answerDialog(
                            activity = this@MainActivity,
                            cancelable = false,
                            listener = object : AwesomeDialogListener {
                                override fun onConfirmClick() {
                                    btn_next.show()
                                    slRealBottom.show()
                                }

                                override fun onAddBookMarkClick() {
                                    viewModel.onInsertFavoriteData(Favorite(
                                        question =  searchQuiz.question,
                                        id = searchQuiz.id,
                                        answer =  searchQuiz.answer,
                                        firstExample = searchQuiz.firstExample,
                                        secondExample = searchQuiz.secondExample,
                                        thirdExample = searchQuiz.thirdExample,
                                        fourthExample = searchQuiz.fourthExample,
                                        firstCommentary = searchQuiz.firstCommentary,
                                        secondCommentary = searchQuiz.secondCommentary,
                                        thirdCommentary = searchQuiz.thirdCommentary,
                                        fourthCommentary = searchQuiz.fourthCommentary
                                    ))
                                    btn_next.show()
                                }
                            }
                        )
                    }

                } else {
                    buttonList[i].setOnClickListener {
                        utility.wrongAnswerDialog(
                            context = this@MainActivity,
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
                    activity = this@MainActivity,
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

    fun onMoveBookMarkActivity() {
        openActivity(BookMarkActivity::class.java)
    }

    /**
     * @author : 이수현
     * @Date : 4/19/21 3:49 PM
     * @Description : 문제 고갈 시 Dialog
     * @History :
     *
     **/
    override fun onExhaustQuiz() {
        utility.exhaustQuestionDialog(activity = this@MainActivity, cancelabel = true)
    }

    override fun onBackPressed() {
        doubleBackPress.onBackPressed()
    }

    override fun dismissProgress() {
        if(progress.isShowing) progress.dismiss()
    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }
}