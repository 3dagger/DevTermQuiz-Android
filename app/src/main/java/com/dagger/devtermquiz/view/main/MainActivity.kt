package com.dagger.devtermquiz.view.main

import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.adapter.QuizListAdapter
import com.dagger.devtermquiz.base.BaseActivity
import com.dagger.devtermquiz.databinding.ActivityMainBinding
import com.dagger.devtermquiz.model.QuizList
import com.dagger.devtermquiz.view.main.model.MainViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator.View {

    override val layoutResourceId: Int get() = R.layout.activity_main
    override val viewModel: MainViewModel by inject()

    val quizList = ArrayList<QuizList>()

    override fun initView() {
        viewModel.setNavigator(this)



        val database = Firebase.database.reference
        val myRef = database.root.child("quiz")

        myRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Logger.d(error)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val a = QuizList(key = snapshot.key.toString(), value = snapshot.value.toString() as String?)

                quizList.add(a)

                val quizAdapter = QuizListAdapter(this@MainActivity, quizList)
                list_item.adapter = quizAdapter


//                Logger.d("snapshot :: $snapshot")
//                Logger.d("snapshot.key ::${snapshot.key}")
//                Logger.d("snapshot.value ::${snapshot.value}")
//                Logger.d("snapshot.childrenCount :: ${snapshot.childrenCount}")
            }
        })


        btn_send.setOnClickListener {
            myRef.push().setValue(txt_firebase.text.toString())
        }

//        list_item.

    }

    override fun onProcess() {
        viewModel.onLoadQuitTestData()


//        viewModel.onLoadAllQuizListData()
//        viewModel.onLoadAllQuizListData()
//        viewModel.allQuizListData.observe(this@MainActivity, Observer {
//            Logger.d("MainActivity it? :: $it")
//        })
    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }
}