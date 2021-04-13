package com.dagger.devtermquiz.view.main

import android.graphics.Color
import android.widget.TextView
import androidx.lifecycle.Observer
import com.dagger.devtermquiz.Constants
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.base.BaseActivity
import com.dagger.devtermquiz.databinding.ActivityMainBinding
import com.dagger.devtermquiz.ext.toast
import com.dagger.devtermquiz.utility.CustomProgressDialog
import com.dagger.devtermquiz.utility.Utility
import com.dagger.devtermquiz.view.main.model.MainViewModel
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
    lateinit var progress: CustomProgressDialog

    private var firstInit: Boolean = true

    private val loadingDuration: Long
        get() = (Constants.LOADING_ANIMATION_DURATION / Constants.ANIMATION_PLAYBACK_SPEED).toLong()

    fun onLoadNowDate() {
        Logger.d(Prefs.getString(Constants.PREFS_NOW_STRING, ""))
    }

    override fun initView() {
        viewModel.setNavigator(this)

        Logger.d("firstInit :: $firstInit")
        //최초 1회 접속 코드 작성해야 됨
        if (firstInit) {
            Prefs.putString(Constants.PREFS_NOW_STRING, utility.getNowDate())
            !firstInit
        }

        Logger.d("firstInit2 :: $firstInit")

        if( utility.getNowDate() != Prefs.getString(Constants.PREFS_NOW_STRING, "")) {
            Logger.d("다름")
        }else {
            Logger.d("같음")
        }

        Prefs.putString(Constants.PREFS_NOW_STRING, utility.getNowDate())

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

        expandable.setOnExpandListener {
            if (it) {
                toast("expanded")
                expandable.spinnerColor = Color.YELLOW
            } else {
                toast("collapse")
                expandable.spinnerColor = Color.BLACK
            }
        }

        expandable.parentLayout.setOnClickListener {
            if (!expandable.isExpanded) {
                expandable.expand()
            } else {
                expandable.collapse()
            }
        }
        expandable.secondLayout.setOnClickListener { expandable.collapse() }

        expandable2.parentLayout.setOnClickListener {
            if (!expandable2.isExpanded) {
                expandable2.expand()
            } else {
                expandable2.collapse()
            }
        }

        viewModel.singleQuizData.observe(this@MainActivity, Observer {
            expandable.parentLayout.findViewById<TextView>(R.id.firstExample).text = it.results[0].ex[0].firstExample
            expandable.secondLayout.findViewById<TextView>(R.id.txt_commentary_1).text = it.results[0].comm[0].firstCommentary
        })


    }
//    }

    fun onRepeatRequestSingleQuizData() {
        viewModel.onLoadSingleQuizData()
    }

    override fun dismissProgress() {
        if(progress.isShowing) progress.dismiss()
    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }
}