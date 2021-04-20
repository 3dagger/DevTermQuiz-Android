package com.dagger.devtermquiz.view.main

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.lifecycle.Observer
import com.dagger.devtermquiz.Constants
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.base.BaseActivity
import com.dagger.devtermquiz.databinding.ActivityMainBinding
import com.dagger.devtermquiz.ext.gone
import com.dagger.devtermquiz.ext.show
import com.dagger.devtermquiz.ext.toast
import com.dagger.devtermquiz.listener.AwesomeDialogListener
import com.dagger.devtermquiz.model.favorite.Favorite
import com.dagger.devtermquiz.utility.CustomProgressDialog
import com.dagger.devtermquiz.utility.Utility
import com.dagger.devtermquiz.view.main.model.MainViewModel
import com.example.awesomedialog.*
import com.kaushikthedeveloper.doublebackpress.DoubleBackPress
import com.orhanobut.logger.Logger
import com.pixplicity.easyprefs.library.Prefs
import com.skydoves.expandablelayout.ExpandableAnimation
import com.skydoves.expandablelayout.ExpandableLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.example_first_view.view.*
import kotlinx.android.synthetic.main.loading_view.*
import kotlinx.android.synthetic.main.question_item.*
import org.koin.android.ext.android.inject
import org.w3c.dom.Text
import uk.co.markormesher.android_fab.SpeedDialMenuAdapter
import uk.co.markormesher.android_fab.SpeedDialMenuItem
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator.View{
    override val layoutResourceId: Int get() = R.layout.activity_main

    override val viewModel       : MainViewModel    by inject()
    private  val utility         : Utility          by inject()
    private  val doubleBackPress : DoubleBackPress  by inject()

    var totalCountQuestion = Prefs.getInt(Constants.PREFS_TOTAL_QUESTION_COUNT, 1)
    var todayCountQuestion = Prefs.getInt(Constants.PREFS_QUESTION_COUNT, 1)

    lateinit var expandList: Array<ExpandableLayout>
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

                }
                1 -> {

                }
            }
            return true
        }

    }

    fun onLoadNowDate() {
//        Logger.d(Prefs.getString(Constants.PREFS_NOW_STRING, ""))
        Prefs.putBoolean(Constants.PREFS_USER_FIRST_ENTRY , false)
    }

    override fun initView() {
        viewModel.setNavigator(this)

        expandList = arrayOf(expandable1, expandable2, expandable3, expandable4)

        fab.speedDialMenuAdapter = fabDialMenuAdapter
        fab.setContentCoverColour(0xcc8b575c.toInt())

        // 최초 접속
        if (Prefs.getBoolean(Constants.PREFS_USER_FIRST_ENTRY, true)) {
            Prefs.putString(Constants.PREFS_NOW_STRING, utility.getNowDate())
            Prefs.putBoolean(Constants.PREFS_USER_FIRST_ENTRY, false)
            Prefs.putInt(Constants.PREFS_QUESTION_COUNT, 1)
        }

        doubleBackPress.setDoubleBackPressAction { finishAffinity() }


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

        viewModel.searchSingleQuizData.observe(this@MainActivity, Observer {
            viewModel.onInsertFavoriteData(Favorite(
                question = it.question,
                answer = it.answer,
                firstExample = it.firstExample,
                secondExample = it.secondExample,
                thirdExample = it.thirdExample,
                fourthExample = it.fourthExample,
                firstCommentary = it.firstCommentary,
                secondCommentary = it.secondCommentary,
                thirdCommentary = it.thirdCommentary,
                fourthCommentary = it.fourthCommentary
            ))


            for (i in expandList.indices) {
                if (i == it.answer) {
                    // 정답 맞췄을때
                    expandList[i].parentLayout.setOnClickListener {
                        btn_next.show()

                        utility.answerDialog(
                            activity = this@MainActivity,
                            cancelable = false,
                            listener = object : AwesomeDialogListener {
                                override fun onConfirmClick() {
                                    expandList[i].expand()
                                    expandList[i].parentLayout.setBackgroundColor(Color.GREEN)
                                    afterAnswerQuestion()
                                }
                            })
                    }
                } else {
                    expandList[i].parentLayout.setOnClickListener {
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
                    })
            }


            expandable1.parentLayout.findViewById<TextView>(R.id.firstExample).text = it.firstExample
            expandable1.secondLayout.findViewById<TextView>(R.id.txt_commentary_1).text = it.firstCommentary

            expandable2.parentLayout.findViewById<TextView>(R.id.firstExample2).text = it.secondExample
            expandable2.secondLayout.findViewById<TextView>(R.id.txt_commentary_2).text = it.secondCommentary

            expandable3.parentLayout.findViewById<TextView>(R.id.firstExample3).text = it.thirdExample
            expandable3.secondLayout.findViewById<TextView>(R.id.txt_commentary_3).text = it.thirdCommentary

            expandable4.parentLayout.findViewById<TextView>(R.id.firstExample4).text = it.fourthExample
            expandable4.secondLayout.findViewById<TextView>(R.id.txt_commentary_4).text = it.fourthCommentary

        })

    }

    private fun afterAnswerQuestion() {
        for(i in expandList.indices) {
            expandList[i].showSpinner = true
            expandList[i].parentLayout.setOnClickListener {
                if(!expandList[i].isExpanded) {
                    expandList[i].expand()
                } else {
                    expandList[i].collapse()
                }
                expandList[i].secondLayout.setOnClickListener {
                    if(!expandList[i].isExpanded) {
                        expandList[i].expand()
                    } else {
                        expandList[i].collapse()
                    }
                }
            }
        }
    }

    /**
     * @author : 이수현
     * @Date : 4/19/21 3:54 PM
     * @Description : 정답 후 ExpandalbeLayout Collapse
     * @History :
     *
     **/
    private fun allExpandableLayoutCollapse() {
        expandable1.apply {
            collapse()
            parentLayout.setBackgroundColor(Color.WHITE)
        }
        expandable2.apply {
            collapse()
            parentLayout.setBackgroundColor(Color.WHITE)
        }
        expandable3.apply {
            collapse()
            parentLayout.setBackgroundColor(Color.WHITE)
        }
        expandable4.apply {
            collapse()
            parentLayout.setBackgroundColor(Color.WHITE)
        }


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
        todayCountQuestion++
        totalCountQuestion++

//        viewModel.favoriteAllData.observe(this@MainActivity, Observer {
//            Logger.d("DB ISERT RESULT :: $it")
//        })
        viewModel.onLoadLiveFavoriteData().observe(this@MainActivity, Observer {
            Logger.d("DB INSERT RESULT :: $it")
        })

        btn_next.gone()
        allExpandableLayoutCollapse()
        viewModel.onLoadSearchSingleQuizData(id = totalCountQuestion)
        Prefs.putInt(Constants.PREFS_QUESTION_COUNT, todayCountQuestion)
        Prefs.putInt(Constants.PREFS_TOTAL_QUESTION_COUNT, totalCountQuestion)

        txt_question_count.text = "$todayCountQuestion / 10"
    }

    /**
     * @author : 이수현
     * @Date : 4/19/21 3:49 PM
     * @Description : 문제 고갈 시 Dialog
     * @History :
     *
     **/
    override fun onExhaustQuiz() {
        utility.exhaustQuestionDialog(activity = this@MainActivity, cancelabel = false)
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