package com.dagger.devtermquiz.view.bookmark

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dagger.devtermquiz.BR
import com.dagger.devtermquiz.Constants
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.base.BaseActivity
import com.dagger.devtermquiz.base.BaseRecyclerView
import com.dagger.devtermquiz.databinding.ActivityBookmarkBinding
import com.dagger.devtermquiz.databinding.BookmarkItemBinding
import com.dagger.devtermquiz.ext.openActivity
import com.dagger.devtermquiz.ext.toast
import com.dagger.devtermquiz.listener.RecyclerViewItemClickListener
import com.dagger.devtermquiz.model.fav.Favorite
import com.dagger.devtermquiz.utility.RxEventBus
import com.dagger.devtermquiz.utility.SwipeHelper
import com.dagger.devtermquiz.view.bookmark.detail.DetailBookMarkActivity
import com.dagger.devtermquiz.view.bookmark.model.BookMarkViewModel
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_bookmark.*
import org.koin.android.ext.android.inject


class BookMarkActivity : BaseActivity<ActivityBookmarkBinding, BookMarkViewModel>(), BookMarkNavigator.View, RecyclerViewItemClickListener {
    override val viewModel: BookMarkViewModel by inject()
    override val layoutResourceId: Int get() = R.layout.activity_bookmark

    private var arr : MutableList<Favorite> = mutableListOf()
    private var idArray : MutableList<Int> = mutableListOf()

    override fun initView() {
        viewModel.setNavigator(this@BookMarkActivity)
    }

    override fun onProcess() {
        viewDataBinding.run {
            lifecycleOwner = this@BookMarkActivity

            activity = this@BookMarkActivity
            vm = viewModel
            itemClickListener = this@BookMarkActivity

            recyclerView.adapter = object : BaseRecyclerView.Adapter<List<Favorite>, BookmarkItemBinding>(
                layoutResId = R.layout.bookmark_item,
                bindingVariableId = BR.allFavoriteData
            ){}
        }

        viewModel.onLoadAllFavoriteData()
        viewModel.allFavoriteData.observe(this@BookMarkActivity, Observer {
            arr.clear()
            idArray.clear()
            for (i in it.indices) {
                arr.add(it[i])
                idArray.add(it[i].id)
            }

        })

        recyclerView.addItemDecoration(DividerItemDecoration(this@BookMarkActivity, DividerItemDecoration.VERTICAL))
        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(recyclerView){
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                var buttons = listOf<UnderlayButton>()
                val deleteButton = deleteButton(position)
                buttons = listOf(deleteButton)
                return buttons
            }
            })
        itemTouchHelper.attachToRecyclerView(recyclerView)
        }


    private fun deleteButton(position: Int) : SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(this, "Delete", 14.0f, android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    viewModel.onDeleteById(id = idArray[position])
                    viewModel.onLoadAllFavoriteData()
                }
            })
    }

    override fun onRecyclerItemClick(position: Int) {
        openActivity(DetailBookMarkActivity::class.java) {
            putInt(Constants.INTENT_ARGUMENT_BOOK_MARK_ANSWER, arr[position].answer)
            putString(Constants.INTENT_ARGUMENT_BOOK_MARK_QUESTION, arr[position].question)
            putString(Constants.INTENT_ARGUMENT_BOOK_MARK_FIRST_EXAMPLE, arr[position].firstExample)
            putString(
                Constants.INTENT_ARGUMENT_BOOK_MARK_SECOND_EXAMPLE,
                arr[position].secondExample
            )
            putString(Constants.INTENT_ARGUMENT_BOOK_MARK_THIRD_EXAMPLE, arr[position].thirdExample)
            putString(
                Constants.INTENT_ARGUMENT_BOOK_MARK_FOURTH_EXAMPLE,
                arr[position].fourthExample
            )
            putString(
                Constants.INTENT_ARGUMENT_BOOK_MARK_FIRST_COMMENTARY,
                arr[position].firstCommentary
            )
            putString(
                Constants.INTENT_ARGUMENT_BOOK_MARK_SECOND_COMMENTARY,
                arr[position].secondCommentary
            )
            putString(
                Constants.INTENT_ARGUMENT_BOOK_MARK_THIRD_COMMENTARY,
                arr[position].thirdCommentary
            )
            putString(
                Constants.INTENT_ARGUMENT_BOOK_MARK_FOURTH_COMMENTARY,
                arr[position].fourthCommentary
            )
        }

//        (recyclerView.adapter as? BaseRecyclerView.Adapter<*, *>)?.run {
//            Logger.d("onRecyclerItemClick position :: $position")
//        }
    }

    override fun onRecyclerLongItemClick(position: Int) {
//        (recyclerView.adapter as? BaseRecyclerView.Adapter<*, *>)?.run {
//            Logger.d("onRecyclerLongItemClick position :: $position")
//        }
    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }

}