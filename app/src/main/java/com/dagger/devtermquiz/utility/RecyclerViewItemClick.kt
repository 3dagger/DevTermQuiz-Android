package com.dagger.devtermquiz.utility

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.dagger.devtermquiz.listener.RecyclerViewItemClickListener

class RecyclerViewItemClick(context: Context, recyclerView: RecyclerView, listener: RecyclerViewItemClickListener) : RecyclerView.OnItemTouchListener {
    private var mListener: RecyclerViewItemClickListener = listener

    private var mGestureDetector: GestureDetector

    init {
        mGestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    val child = recyclerView.findChildViewUnder(e.x, e.y)
                    if (child != null) {
                        mListener.onRecyclerLongItemClick(recyclerView.getChildAdapterPosition(child))
                    }
                }
            })
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
        val childView = view.findChildViewUnder(e.x, e.y)
        if (childView != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onRecyclerItemClick(view.getChildAdapterPosition(childView))
            return true
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

}