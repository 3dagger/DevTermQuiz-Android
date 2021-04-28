package com.dagger.devtermquiz.view.main

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dagger.devtermquiz.Constants
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.adapter.ViewPagerAdapter
import com.dagger.devtermquiz.base.BaseActivity
import com.dagger.devtermquiz.databinding.ActivityViewpagerBinding
import com.dagger.devtermquiz.view.main.bookmark.BookMarkFragment
import com.dagger.devtermquiz.view.main.model.MainViewPagerViewModel
import com.dagger.devtermquiz.view.main.quiz.QuizFragment
import com.dagger.devtermquiz.view.main.setting.SettingFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import com.kaushikthedeveloper.doublebackpress.DoubleBackPress
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_viewpager.*
import org.koin.android.ext.android.inject
import java.lang.RuntimeException

class MainViewPagerActivity : BaseActivity<ActivityViewpagerBinding, MainViewPagerViewModel>(), MainViewPagerNavigator.View{
    override val layoutResourceId: Int get() = R.layout.activity_viewpager
    override val viewModel: MainViewPagerViewModel by inject()
    private  val doubleBackPress : DoubleBackPress by inject()

    override fun initView() {
        view_pager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        bottom_bar.setupWithViewPager2(view_pager)
        bottom_bar.apply {
            onTabSelected = {
                Log.i("ViewPagerActivity", "onTabSelected: ${it.title}")
            }
            onTabReselected = {
                Log.i("ViewPagerActivity", "onTabReselected: ${it.title}")
                Firebase.messaging.unsubscribeFromTopic(Constants.FIREBASE_SUBSCRIBE_KEY)
            }
        }

        doubleBackPress.setDoubleBackPressAction { finishAffinity() }
    }

    /**
     * @author : 이수현
     * @Date : 4/28/21 9:58 AM
     * @Description : FCM 구독 
     * @History : 
     *
     **/
    override fun onProcess() {
        Firebase.messaging.subscribeToTopic(Constants.FIREBASE_SUBSCRIBE_KEY)
    }
    
    override fun onBackPressed() {
        doubleBackPress.onBackPressed()
    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }
}