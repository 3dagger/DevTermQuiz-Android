package com.dagger.devtermquiz.di

import com.dagger.devtermquiz.view.bookmark.detail.model.DetailBookMarkViewModel
import com.dagger.devtermquiz.view.main.bookmark.model.BookMarkFragmentViewModel
import com.dagger.devtermquiz.view.main.quiz.model.QuizFragmentViewModel
import com.dagger.devtermquiz.view.old.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var viewModelModules = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { BookMarkFragmentViewModel(get()) }
    viewModel { DetailBookMarkViewModel()}
    viewModel { QuizFragmentViewModel(get(), get()) }
}