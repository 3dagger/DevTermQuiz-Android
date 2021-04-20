package com.dagger.devtermquiz.repository.local.favorite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dagger.devtermquiz.model.favorite.Favorite
import io.reactivex.rxjava3.core.Single

@Dao
interface LocalFavoriteRepoService {
    @Query("SELECT * FROM favorite ORDER BY num DESC")
    fun getAllFavorite(): Single<MutableList<Favorite>>

    @Insert
    fun insertAll(favorite: Favorite)
}