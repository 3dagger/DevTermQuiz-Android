package com.dagger.devtermquiz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.model.django.quiz.SingleQuizResults
import com.github.florent37.expansionpanel.ExpansionLayout
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import java.util.*

class RecyclerAdapter(private val result: List<SingleQuizResults>) : RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder>(){
//    private val list: MutableList<Any> = ArrayList()

    private val expansionsCollection = ExpansionLayoutCollection()

    inner class RecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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


//    fun RecyclerAdapter() {
//        expansionsCollection.openOnlyOne(true)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder? {
        return RecyclerHolder.buildFor(parent)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.RecyclerHolder, position: Int) {
        holder.bind(position)
        expansionsCollection.add(holder.expansionLayout)
    }

    override fun getItemCount(): Int {
        return result.size
    }


}