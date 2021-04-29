package com.dagger.devtermquiz.view.main.bookmark

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import com.dagger.devtermquiz.BR
import com.dagger.devtermquiz.Constants
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.base.BaseFragment
import com.dagger.devtermquiz.base.BaseRecyclerView
import com.dagger.devtermquiz.databinding.BookmarkItemBinding
import com.dagger.devtermquiz.databinding.FragmentBookmarkBinding
import com.dagger.devtermquiz.ext.gone
import com.dagger.devtermquiz.ext.show
import com.dagger.devtermquiz.listener.RecyclerViewItemClickListener
import com.dagger.devtermquiz.model.fav.Favorite
import com.dagger.devtermquiz.utility.CustomProgressDialog
import com.dagger.devtermquiz.utility.SwipeHelper
import com.dagger.devtermquiz.view.main.bookmark.detail.DetailBookMarkActivity
import com.dagger.devtermquiz.view.main.bookmark.model.BookMarkFragmentViewModel
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_bookmark.*
import org.koin.android.ext.android.inject

class BookMarkFragment : BaseFragment<FragmentBookmarkBinding, BookMarkFragmentViewModel>(), BookMarkFragmentNavigator.View,
    RecyclerViewItemClickListener {
    override val layoutResourceId: Int get() = R.layout.fragment_bookmark
    override val viewModel: BookMarkFragmentViewModel by inject()

    private var arr : MutableList<Favorite> = mutableListOf()
    private var idArray : MutableList<Int> = mutableListOf()

    lateinit var progress: CustomProgressDialog

    override fun initView() {
        viewModel.setNavigator(this@BookMarkFragment)

        progress = CustomProgressDialog(
            context = mActivity,
            message = "잠시만 기다려주세요.",
            cancelable = false
        )
        progress.show()
    }

    override fun onProcess() {
        viewDataBinding.run {
            lifecycleOwner = this@BookMarkFragment

            activity = this@BookMarkFragment
            vm = viewModel
            itemClickListener = this@BookMarkFragment

            recyclerView.adapter = object : BaseRecyclerView.Adapter<List<Favorite>, BookmarkItemBinding>(
                layoutResId = R.layout.bookmark_item,
                bindingVariableId = BR.allFavoriteData
            ){}
        }




        viewModel.allFavoriteData.observe(this@BookMarkFragment, Observer {
            arr.clear()
            idArray.clear()
            for (i in it.indices) {
                arr.add(it[i])
                idArray.add(it[i].id)
            }
        })

        recyclerView.addItemDecoration(DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL))
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
        return SwipeHelper.UnderlayButton(mActivity, "Delete", 14.0f, android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    viewModel.onDeleteById(id = idArray[position])
                    viewModel.onLoadAllFavoriteData()
                }
            })
    }

    override fun onRecyclerItemClick(position: Int) {
        val intent = Intent(mActivity, DetailBookMarkActivity::class.java)
        intent.apply {
            putExtra(Constants.INTENT_ARGUMENT_BOOK_MARK_ANSWER, arr[position].answer)
            putExtra(Constants.INTENT_ARGUMENT_BOOK_MARK_QUESTION, arr[position].question)
            putExtra(Constants.INTENT_ARGUMENT_BOOK_MARK_FIRST_EXAMPLE, arr[position].firstExample)
            putExtra(Constants.INTENT_ARGUMENT_BOOK_MARK_SECOND_EXAMPLE, arr[position].secondExample)
            putExtra(Constants.INTENT_ARGUMENT_BOOK_MARK_THIRD_EXAMPLE, arr[position].thirdExample)
            putExtra(Constants.INTENT_ARGUMENT_BOOK_MARK_FOURTH_EXAMPLE, arr[position].fourthExample)
            putExtra(Constants.INTENT_ARGUMENT_BOOK_MARK_FIRST_COMMENTARY, arr[position].firstCommentary)
            putExtra(Constants.INTENT_ARGUMENT_BOOK_MARK_SECOND_COMMENTARY, arr[position].secondCommentary)
            putExtra(Constants.INTENT_ARGUMENT_BOOK_MARK_THIRD_COMMENTARY, arr[position].thirdCommentary)
            putExtra(Constants.INTENT_ARGUMENT_BOOK_MARK_FOURTH_COMMENTARY, arr[position].fourthCommentary)
        }
        startActivity(intent)

    }

    override fun onRecyclerLongItemClick(position: Int) {
    }

    override fun onRecyclerViewEmpty() {
        txt_empty_bookmark_msg.show()
        recyclerView.gone()
    }

    override fun onRecyclerViewNotEmpty() {
        txt_empty_bookmark_msg.gone()
        recyclerView.show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onLoadAllFavoriteData()
    }

    override fun dismissProgress() {
        if(progress.isShowing) progress.dismiss()
    }

    companion object {
        fun newInstance(position: Int): BookMarkFragment {
            val instance = BookMarkFragment()
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