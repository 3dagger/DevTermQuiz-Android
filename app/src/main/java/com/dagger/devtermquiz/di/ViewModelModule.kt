package com.dagger.devtermquiz.di

import com.dagger.devtermquiz.view.main.bookmark.detail.model.DetailBookMarkViewModel
import com.dagger.devtermquiz.view.main.bookmark.model.BookMarkFragmentViewModel
import com.dagger.devtermquiz.view.main.calendar.model.CalendarFragmentViewModel
import com.dagger.devtermquiz.view.main.quiz.model.QuizFragmentViewModel
import com.dagger.devtermquiz.view.main.setting.model.SettingFragmentViewModel
import com.dagger.devtermquiz.view.splash.model.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var viewModelModules = module {
    viewModel { BookMarkFragmentViewModel(get()) }
    viewModel { DetailBookMarkViewModel() }
    viewModel { SettingFragmentViewModel() }
    viewModel { QuizFragmentViewModel(get(), get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { CalendarFragmentViewModel() }
}