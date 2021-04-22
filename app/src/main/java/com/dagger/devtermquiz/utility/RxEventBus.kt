package com.dagger.devtermquiz.utility

import com.dagger.devtermquiz.model.fav.Favorite
import io.reactivex.subjects.PublishSubject

object RxEventBus {
    val favoriteSubject = PublishSubject.create<MutableList<Favorite>>()

    fun sentFavoriteEvent(arr: MutableList<Favorite>) {
        favoriteSubject.onNext(arr)
    }
}