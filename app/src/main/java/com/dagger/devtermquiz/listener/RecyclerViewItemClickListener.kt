package com.dagger.devtermquiz.listener

interface RecyclerViewItemClickListener {
    fun onRecyclerItemClick(position : Int)

    fun onRecyclerLongItemClick(position : Int)
}