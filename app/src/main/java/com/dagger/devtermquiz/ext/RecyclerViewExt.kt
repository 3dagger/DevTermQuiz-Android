package com.dagger.devtermquiz.ext

import android.app.Activity
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dagger.devtermquiz.base.BaseRecyclerView
import com.dagger.devtermquiz.listener.RecyclerViewItemClickListener
import com.dagger.devtermquiz.utility.RecyclerViewItemClick

@Suppress("UNCHECKED_CAST")
@BindingAdapter("addItems") // xml의 recyclerview에 선언한 이름
fun RecyclerView.addItems(list: List<Any>?) {
    (this.adapter as? BaseRecyclerView.Adapter<Any, *>)?.run {
        addItems(list)
        addActivity(context as Activity)
        notifyDataSetChanged()
    }
}

@BindingAdapter("itemClick")
fun RecyclerView.itemClick(listener: RecyclerViewItemClickListener) {
    addOnItemTouchListener(RecyclerViewItemClick(context, this, listener))
}