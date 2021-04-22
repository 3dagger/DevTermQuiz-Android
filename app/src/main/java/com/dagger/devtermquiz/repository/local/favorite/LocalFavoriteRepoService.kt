package com.dagger.devtermquiz.repository.local.favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dagger.devtermquiz.base.BaseDao
import com.dagger.devtermquiz.model.fav.Favorite
import io.reactivex.Single

@Dao
interface LocalFavoriteRepoService {
    @Query("SELECT * FROM userFavorite")
    fun getAll(): Single<MutableList<Favorite>>

    @Query("SELECT * FROM userFavorite ORDER BY num DESC")
    fun getAllFavorite(): LiveData<Favorite>

    @Insert
    fun insertAll(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("DELETE FROM userFavorite WHERE id = :id")
    fun deleteById(id: Int)

}