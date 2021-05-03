package com.dagger.devtermquiz.view.main.calendar

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.base.BaseFragment
import com.dagger.devtermquiz.databinding.FragmentCalendarBinding
import com.dagger.devtermquiz.view.main.calendar.model.CalendarFragmentViewModel
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.koin.android.ext.android.inject
import java.time.YearMonth

class CalenderFragment : BaseFragment<FragmentCalendarBinding, CalendarFragmentViewModel>(), CalenderFragmentNavigator.View {
    override val layoutResourceId: Int get() = R.layout.fragment_calendar
    override val viewModel: CalendarFragmentViewModel by inject()

    override fun initView() {
        viewModel.setNavigator(this@CalenderFragment)



    }

    override fun onProcess() {
    }

    companion object {
        fun newInstance(position: Int): CalenderFragment {
            val instance = CalenderFragment()
            val args = Bundle()
            args.putInt("position", position)
            instance.arguments = args
            return instance
        }
    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }
}