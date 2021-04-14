package com.dagger.devtermquiz.view.main

import android.graphics.Color
import android.widget.TextView
import androidx.core.view.size
import androidx.lifecycle.Observer
import com.dagger.devtermquiz.Constants
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.base.BaseActivity
import com.dagger.devtermquiz.databinding.ActivityMainBinding
import com.dagger.devtermquiz.ext.toast
import com.dagger.devtermquiz.listener.AwesomeDialogListener
import com.dagger.devtermquiz.utility.CustomProgressDialog
import com.dagger.devtermquiz.utility.Utility
import com.dagger.devtermquiz.view.main.model.MainViewModel
import com.example.awesomedialog.*
import com.kaushikthedeveloper.doublebackpress.DoubleBackPress
import com.orhanobut.logger.Logger
import com.pixplicity.easyprefs.library.Prefs
import com.skydoves.expandablelayout.ExpandableAnimation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading_view.*
import kotlinx.android.synthetic.main.question_item.*
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator.View {

    override val layoutResourceId: Int get() = R.layout.activity_main
    override val viewModel: MainViewModel by inject()
    private val utility: Utility by inject()
    private val doubleBackPress : DoubleBackPress by inject()
    lateinit var progress: CustomProgressDialog
    
    fun onLoadNowDate() {
        Logger.d(Prefs.getString(Constants.PREFS_NOW_STRING, ""))
        Prefs.putBoolean(Constants.PREFS_USER_FIRST_ENTRY , false)
    }

    override fun initView() {
        viewModel.setNavigator(this)


        //최초 1회 접속 코드 작성해야 됨
        if (Prefs.getBoolean(Constants.PREFS_USER_FIRST_ENTRY, true)) {
            Prefs.putString(Constants.PREFS_NOW_STRING, utility.getNowDate())
            Prefs.putBoolean(Constants.PREFS_USER_FIRST_ENTRY, false)
//            Logger.d("최초접속")
        }

        doubleBackPress.setDoubleBackPressAction { finishAffinity() }

        // Kotlin
//        fab.setOnClickListener { v ->
//            Logger.d("fab click")
//        }
//        fab.setContentCoverColour(0xffff9900.toInt())
//        fab.setButtonIconResource(R.drawable.ic_drawer_icon)
//        fab.cardView
//        fab.contentCoverView
//        fab.iconWrapper


        if (utility.getNowDate() != Prefs.getString(Constants.PREFS_NOW_STRING, "")) {
//            Logger.d("다름")

            // Add Logic
            Prefs.putString(Constants.PREFS_NOW_STRING, utility.getNowDate())

        }else {
//            Logger.d("같음")

//            utility.answerDialog(
//                activity = this@MainActivity,
//                cancelable = false,
//                listener = object : AwesomeDialogListener {
//                    override fun onConfirmClick() {
//                    }
//                })

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
    }

    override fun onProcess() {
        viewModel.onLoadSingleQuizData()

        expandable1.setOnExpandListener {
            if (it) {
                toast("expanded")
//                expandable.spinnerColor = Color.YELLOW
            } else {
                toast("collapse")
//                expandable.spinnerColor = Color.BLACK
            }
        }

//        expandable.parentLayout.setOnClickListener {
//            if (!expandable.isExpanded) {
//                expandable.expand()
//            } else {
//                expandable.collapse()
//            }
//        }
        expandable1.secondLayout.setOnClickListener { expandable1.collapse() }

//        expandable2.parentLayout.setOnClickListener {
//            if (!expandable2.isExpanded) {
//                expandable2.expand()
//            } else {
//                expandable2.collapse()
//            }
//        }

        val expandList = arrayOf(expandable1, expandable2, expandable3, expandable4)

        viewModel.singleQuizData.observe(this@MainActivity, Observer {

            for (i in expandList.indices) {
                if (i == it.results[0].answer) {
                    expandList[i].parentLayout.setOnClickListener {
                        utility.answerDialog(
                            activity = this@MainActivity,
                            cancelable = true,
                            listener = object : AwesomeDialogListener {
                                override fun onConfirmClick() {
                                    expandList[i].expand()
                                }
                            })
                    }
                } else {
                    expandList[i].parentLayout.setOnClickListener {
                        utility.wrongAnswerDialog(
                            activity = this@MainActivity,
                            cancelable = true
                            )
                    }
                }
            }


            expandable1.parentLayout.findViewById<TextView>(R.id.firstExample).text = it.results[0].ex[0].firstExample
            expandable1.secondLayout.findViewById<TextView>(R.id.txt_commentary_1).text = it.results[0].comm[0].firstCommentary
        })

        // 정답 0, 1, 2, 3
        // ClickListener 새로 등록
        // 정답일때 AnswerDialog 표출 -> 확인버튼 터치 시 잔여 문제 갯수 갱신 -> 문제 초기화

    }


    fun onRepeatRequestSingleQuizData() {
        viewModel.onLoadSingleQuizData()
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