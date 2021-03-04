package com.dagger.devtermquiz

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.dagger.devtermquiz.di.apiModules
import com.dagger.devtermquiz.di.networkModules
import com.dagger.devtermquiz.di.viewModelModules
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    companion object {
        var DEBUG = false
    }

    override fun onCreate() {
        super.onCreate()

        DEBUG = isDebuggable(this)

        // init Koin
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(apiModules, networkModules, viewModelModules))
        }

        // init Logger
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false) // 쓰레드 보여줄 것인지
            .methodCount(2)        // 몇라인까지 출력할지, 기본값 2
            .methodOffset(5)       // 메서드 오프셋, 기본값 5
            .tag(Constants.TAG_NAME)      // 글로벌 태그
            .build()

        // 디버그에서만 로그출력
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })



    }

    /**
     * @author : 이수현
     * @Date : 2/14/21 8:54 PM
     * @Description : 디버그 여부 Return
     * @History :
     *
     **/
    private fun isDebuggable(context: Context): Boolean {
        var debuggable = false

        val pm = context.packageManager
        try {
            val appinfo = pm.getApplicationInfo(context.packageName, 0)
            debuggable = 0 != appinfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
        } catch (e: PackageManager.NameNotFoundException) {
            /* debuggable variable will remain false */
        }

        return debuggable
    }
}