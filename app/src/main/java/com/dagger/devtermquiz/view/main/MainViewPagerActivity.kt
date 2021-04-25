package com.dagger.devtermquiz.view.main

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.base.BaseActivity
import com.dagger.devtermquiz.databinding.ActivityViewpagerBinding
import com.dagger.devtermquiz.view.main.bookmark.BookMarkFragment
import com.dagger.devtermquiz.view.main.model.MainViewPagerViewModel
import com.dagger.devtermquiz.view.main.quiz.QuizFragment
import kotlinx.android.synthetic.main.activity_viewpager.*
import org.koin.android.ext.android.inject

class MainViewPagerActivity : BaseActivity<ActivityViewpagerBinding, MainViewPagerViewModel>(){

    override val viewModel: MainViewPagerViewModel by inject()

    override val layoutResourceId: Int get() = R.layout.activity_viewpager

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
    }

    override fun onProcess() {
    }

    override fun onViewModelCleared() {
    }

    class ViewPager2Adapter(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle
    ) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> {
                    QuizFragment.newInstance(position)
                }
                1 -> {
                    BookMarkFragment.newInstance(position)
                }

                else -> QuizFragment.newInstance(position)
            }
        }
    }
}