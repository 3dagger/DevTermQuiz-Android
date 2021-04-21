package com.dagger.devtermquiz.utility

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog
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
            .onPositive("해설 보기") {
                listener.onConfirmClick()
            }
            .onNegative("즐겨찾기에 추가하기") {
                listener.onAddBookMarkClick()

            }
            .position(AwesomeDialog.POSITIONS.CENTER)
            .setCancelable(cancelable)
    }
//
//    fun wrongAnswerDialog(activity: Activity, cancelable: Boolean) {
//        AwesomeDialog.build(context = activity)
//            .title("아쉬워요")
//            .body("다른 답을 골라보세요")
//            .icon(R.drawable.ic_congrts)
//            .setCancelable(cancelable)
//   }
//
    fun exhaustQuestionDialog(activity: Activity, cancelabel: Boolean) {
        AwesomeDialog.build(context = activity)
            .title("죄송합니다")
            .body("준비한 문제를 다 푸셨어요.\n문제 업데이트 예정입니다!")
            .icon(R.drawable.ic_congrts)
            .position(AwesomeDialog.POSITIONS.CENTER)
            .setCancelable(cancelabel)
    }

    fun wrongAnswerDialog(context: Context,
                          title: String,
                          message: String,
                          buttonText: String,
                          cancelable: Boolean) {
        AwesomeErrorDialog(context)
            .setTitle(title)
            .setMessage(message)
            .setColoredCircle(R.color.dialogErrorBackgroundColor)
            .setDialogIconAndColor(R.drawable.ic_dialog_error, R.color.white)
            .setCancelable(cancelable)
            .setButtonBackgroundColor(R.color.dialogErrorBackgroundColor)
            .setButtonText(buttonText)
            .show()
    }


}