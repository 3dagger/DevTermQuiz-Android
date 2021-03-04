package com.dagger.devtermquiz.view.main

interface MainNavigator {
    interface View{

    }
    interface ViewModel{
        fun disposableClear()
    }
}