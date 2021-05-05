package com.dagger.devtermquiz.adapter

import android.content.Context
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dagger.devtermquiz.Constants
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.view.main.bookmark.BookMarkFragment
import com.dagger.devtermquiz.view.main.calendar.CalenderFragment
import com.dagger.devtermquiz.view.main.quiz.QuizFragment
import com.dagger.devtermquiz.view.main.setting.SettingFragment

/**
 * <pre>
  * com.dagger.devtermquiz.adapter
  * </pre>
  *
  * @author : 이수현
  * @Date : 4/28/21 10:09 AM
  * @Version : 1.0.0
  * @Description :  Fragment
 *                  1   ->  Main Quiz Fragment
 *                  2   ->  BookMark Fragment
 *                  3   ->  Setting Fragment
  * @History :
  *
  **/
class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = Constants.VIEWPAGER_ITEM_COUNT

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            Constants.FRAGMENT_POSITION_QUIZ -> {
                QuizFragment.newInstance(position)
            }
            Constants.FRAGMENT_POSITION_CALENDAR -> {
                CalenderFragment.newInstance(position)
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