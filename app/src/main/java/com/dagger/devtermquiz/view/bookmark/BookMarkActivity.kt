package com.dagger.devtermquiz.view.bookmark

import androidx.lifecycle.Observer
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.base.BaseActivity
import com.dagger.devtermquiz.databinding.ActivityBookmarkBinding
import com.dagger.devtermquiz.repository.local.favorite.LocalFavoriteRepoService
import com.dagger.devtermquiz.view.bookmark.model.BookMarkViewModel
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.inject

class BookMarkActivity : BaseActivity<ActivityBookmarkBinding, BookMarkViewModel>(), BookMarkNavigator.View {
    override val viewModel: BookMarkViewModel by inject()
    override val layoutResourceId: Int get() = R.layout.activity_bookmark

    override fun initView() {
        viewModel.setNavigator(this@BookMarkActivity)

    }

    override fun onProcess() {
        viewModel.onLoadLiveFavoriteData().observe(this@BookMarkActivity, Observer {
            Logger.d("DB INSERT RESULT :: $it")
        })

        viewModel.onLoadAllFavoriteData()
//        Logger.d("--\n${viewModel.onLoadLiveFavoriteData()}\n--")


    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }

}