package com.dagger.devtermquiz.base

import android.os.Handler
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.lang.ref.WeakReference

open class BaseViewModel<N> : ViewModel() {
    private lateinit var mNavigator: WeakReference<N>

    fun getNavigator(): N {
        return mNavigator.get()!!
    }

    fun setNavigator(navigator: N) {
        this.mNavigator = WeakReference(navigator)
    }

    private lateinit var handler: Handler

    fun getHandler(): Handler {
        return handler
    }

    fun setHandler(handler: Handler) {
        this.handler = handler
    }
    /**
     * RxJava 의 observing을 위한 부분.
     * addDisposable을 이용하여 추가하기만 하면 된다
     *
    Model에서 들어오는 Single<>과 같은 RxJava 객체들의 Observing을 위한 부분입니다.
    기본적으로 RxJava의 Observable들은 compositeDisposable에 추가를 해주고, 뷰모델이 없어질 때 추가했던 것들을 지워줘야합니다.
    이 부분은 그러한 동작을 수행하는 코드로서, Observable들을 옵저빙할때 addDisposable()을 쓰게 됩니다.
    또한 ViewModel은 View와의 생명주기를 공유하기 때문에 View가 부서질 때 ViewModel의 onCleared()가 호출되게 되며, 그에 따라 옵저버블들이 전부 클리어 되게 됩니다.
     */
    private val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}