package com.dagger.devtermquiz.view.main.calendar.model

import com.dagger.devtermquiz.base.BaseViewModel
import com.dagger.devtermquiz.view.main.calendar.CalenderFragmentNavigator

class CalendarFragmentViewModel : BaseViewModel<CalenderFragmentNavigator.View>(), CalenderFragmentNavigator.ViewModel {
    override fun disposableClear() {
        onCleared()
    }
}