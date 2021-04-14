package com.dagger.devtermquiz.utility

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.listener.AwesomeDialogListener
import com.example.awesomedialog.*
import com.orhanobut.logger.Logger
import java.text.SimpleDateFormat
import java.util.*

class Utility(private val context: Context) {

    fun getNowDate(): String {
//        // 시스템으로부터 현재시간(ms) 가져오기
//        val now = System.currentTimeMillis()
//        // Data 객체에 시간을 저장한다.
//        val date = Date(now)
//        // 각자 사용할 포맷을 정하고 문자열로 만든다.
//        val sdfNow = SimpleDateFormat("yyyy년 MM월 dd일 EE요일")
//        return sdfNow.format(date)
        val currentDateTime = Calendar.getInstance().time
        return SimpleDateFormat("MMdd", Locale.KOREA).format(currentDateTime)
    }

    fun answerDialog(activity: Activity, cancelable: Boolean, listener: AwesomeDialogListener) {
        AwesomeDialog.build(context = activity)
            .title("축하합니다.")
            .body("다음 문제를 풀어보세요")
            .icon(R.drawable.ic_congrts)
            .onPositive("다음 문제로 이동하기") {
                listener.onConfirmClick()
            }
            .position(AwesomeDialog.POSITIONS.CENTER)
            .setCancelable(cancelable)
    }

    fun wrongAnswerDialog(activity: Activity, cancelable: Boolean) {
        AwesomeDialog.build(context = activity)
            .title("아쉬워요")
            .body("다른 답을 골라보세요")
            .icon(R.drawable.ic_congrts)
   }


}