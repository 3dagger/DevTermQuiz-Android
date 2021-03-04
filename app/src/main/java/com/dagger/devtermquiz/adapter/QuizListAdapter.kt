package com.dagger.devtermquiz.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.model.QuizList

class QuizListAdapter (private val context: Context, private val quizList: ArrayList<QuizList> ) : BaseAdapter() {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.main_quiz_list_item, null)

        val quizName = view.findViewById<TextView>(R.id.quizName)
        val quiz = quizList[position]

        quizName.text = quiz.key

        return view
    }

    override fun getItem(position: Int): Any {
        return quizList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return quizList.size
    }

}