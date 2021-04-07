package com.dagger.devtermquiz.base

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerView {
    abstract class Adapter<ITEM : Any, B : ViewDataBinding>(@LayoutRes private val layoutResId: Int, private val bindingVariableId: Int? = null,
                                                            private val bindingActivityId: Int? = null)
        : RecyclerView.Adapter<Adapter.ViewHolder<B>> () {

        private val items = mutableListOf<ITEM>()
        private lateinit var activity : Activity

        fun addItems(items : List<ITEM>?){
            items?.let {
                this.items.run {
                    clear()
                    addAll(it)
                }
            }
        }

        fun getItems(position: Int) : ITEM {
            return items[position]
        }

        fun addActivity(activity: Activity?){
            activity?.let {
                this.activity = activity
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = object : ViewHolder<B>(
                layoutResId = layoutResId,
                parent = parent,
                bindingVariableId = bindingVariableId,
                bindingActivityId = bindingActivityId
            ){}

        override fun getItemCount() = items.size

        override fun onBindViewHolder(holder: ViewHolder<B>, position: Int) {
            holder.onBindViewHolder(items[position], activity)
        }

        abstract class ViewHolder<B : ViewDataBinding>(@LayoutRes layoutResId: Int, parent: ViewGroup, private val bindingVariableId: Int?,
                                                       private val bindingActivityId: Int?)
            : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)){

            private val binding: B = DataBindingUtil.bind(itemView)!!

            fun onBindViewHolder(item : Any?, activity: Activity?){
                try{
                    bindingVariableId?.let {
                        binding.setVariable(it, item)
                    }

                    bindingActivityId?.let {
                        binding.setVariable(it, activity)
                    }
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}