package com.dagger.devtermquiz.utility

import android.content.Context
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

        // 현재 접속 어제 밤11시 12분 접속 -> 20210413   2400 2312 88
        // 다음 접속 내일 새벽 2시 15분 접속 -> 2021041  2400 0215
    }
}