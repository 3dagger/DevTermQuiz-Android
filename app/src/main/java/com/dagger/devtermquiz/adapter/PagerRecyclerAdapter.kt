package com.dagger.devtermquiz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.model.django.quiz.SingleQuizResults

class PagerRecyclerAdapter(private val result: List<SingleQuizResults>) : RecyclerView.Adapter<PagerRecyclerAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textExampleOne: TextView = itemView.findViewById(R.id.txt_ex_one)
        private val textExampleTwo: TextView = itemView.findViewById(R.id.txt_ex_two)
        private val textExampleThree: TextView = itemView.findViewById(R.id.txt_ex_three)
        private val textExampleFour: TextView = itemView.findViewById(R.id.txt_ex_four)

        fun bind(position: Int) {
            textExampleOne.apply {
                text = result[0].ex[0].firstExample
            }
            textExampleTwo.text = result[0].ex[0].secondExample
            textExampleThree.text = result[0].ex[0].thirdExample
            textExampleFour.text = result[0].ex[0].fourthExample
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerRecyclerAdapter.PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_view,
            parent,
            false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerRecyclerAdapter.PagerViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return result.size
    }

}