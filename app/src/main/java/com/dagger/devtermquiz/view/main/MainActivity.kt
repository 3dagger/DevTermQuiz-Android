package com.dagger.devtermquiz.view.main

import androidx.lifecycle.Observer
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.base.BaseActivity
import com.dagger.devtermquiz.databinding.ActivityMainBinding
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


    override fun initView() {
        viewModel.setNavigator(this)

        val database = Firebase.database.reference
        val myRef = database.root.child("quiz")

        myRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Logger.d(error)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
            }
        })


        btn_send.setOnClickListener {
            myRef.push().setValue(txt_firebase.text.toString())
        }


    }

    override fun onProcess() {
        viewModel.onLoadSingleQuizData()
        viewModel.singleQuizData.observe(this@MainActivity, Observer {
            val splitArray = it.results[0].body.split("/")
            Logger.d("splitArray :: $splitArray")
            for(i in splitArray.indices) {
               Logger.d("splitArray :: ${splitArray[i]}")
            }
        })

    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }
}