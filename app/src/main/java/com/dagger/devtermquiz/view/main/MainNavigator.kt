package com.dagger.devtermquiz.view.main

interface MainNavigator {
    interface View{
        fun dismissProgress()
    }
    interface ViewModel{
        fun disposableClear()
    }
}