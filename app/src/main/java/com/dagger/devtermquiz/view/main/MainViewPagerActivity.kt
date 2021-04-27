package com.dagger.devtermquiz.view.main

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dagger.devtermquiz.Constants
import com.dagger.devtermquiz.R
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

class MainViewPagerActivity : BaseActivity<ActivityViewpagerBinding, MainViewPagerViewModel>(), MainViewPagerNavigator.View{
    override val layoutResourceId: Int get() = R.layout.activity_viewpager
    override val viewModel: MainViewPagerViewModel by inject()
    private  val doubleBackPress : DoubleBackPress by inject()


    override fun initView() {
        view_pager.adapter = ViewPager2Adapter(supportFragmentManager, lifecycle)
        bottom_bar.setupWithViewPager2(view_pager)
        bottom_bar.apply {
            onTabSelected = {
                Log.i("ViewPagerActivity", "onTabSelected: ${it.title}")
            }
            onTabReselected = {
                Log.i("ViewPagerActivity", "onTabReselected: ${it.title}")
            }
        }

        doubleBackPress.setDoubleBackPressAction { finishAffinity() }
    }

    override fun onProcess() {

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            Logger.d("task exception:: ${task.exception}")
            Logger.d("task result:: ${task.result}")
        })
        
        Firebase.messaging.subscribeToTopic(Constants.FIREBASE_SUBSCRIBE_KEY).addOnCompleteListener { task ->
            Logger.d("task result :: ${task.isSuccessful}")
            Logger.d("task result :: ${task.result}")
            var msg = "구독" 
            if (!task.isSuccessful) {
                Logger.d("여기 들어오면 실패임")
                msg = "실패"
            }
        }
    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }

    override fun onBackPressed() {
        doubleBackPress.onBackPressed()
    }


    class ViewPager2Adapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                Constants.FRAGMENT_POSITION_QUIZ -> {
                    QuizFragment.newInstance(position)
                }
                Constants.FRAGMENT_POSITION_BOOKMARK -> {
                    BookMarkFragment.newInstance(position)
                }
                Constants.FRAGMENT_POSITION_SETTING -> {
                    SettingFragment.newInstance(position)
                }
                else -> QuizFragment.newInstance(position)
            }
        }
    }
}