package com.dagger.devtermquiz.view.main.setting.model

import com.dagger.devtermquiz.base.BaseViewModel
import com.dagger.devtermquiz.view.main.setting.SettingFragmentNavigator

class SettingFragmentViewModel: BaseViewModel<SettingFragmentNavigator.View>(), SettingFragmentNavigator.ViewModel{
    override fun disposableClear() {
        onCleared()
    }
}