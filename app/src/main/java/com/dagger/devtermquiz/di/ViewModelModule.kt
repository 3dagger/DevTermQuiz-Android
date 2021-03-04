package com.dagger.devtermquiz.di

import com.dagger.devtermquiz.view.main.model.MainViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var viewModelModules = module {
    viewModel { MainViewModel(get()) }
}