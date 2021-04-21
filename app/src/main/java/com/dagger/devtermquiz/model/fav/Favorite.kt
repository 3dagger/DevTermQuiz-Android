package com.dagger.devtermquiz.model.fav

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userFavorite")
data class Favorite(
     @ColumnInfo(name = "question") val question: String,
     @ColumnInfo(name = "answer") var answer: Int,
     @ColumnInfo(name = "firstExample") var firstExample: String,
     @ColumnInfo(name = "secondExample") var secondExample: String,
     @ColumnInfo(name = "thirdExample") var thirdExample: String,
     @ColumnInfo(name = "fourthExample") var fourthExample: String,
     @ColumnInfo(name = "firstCommentary") var firstCommentary: String,
     @ColumnInfo(name = "secondCommentary") var secondCommentary: String,
     @ColumnInfo(name = "thirdCommentary") var thirdCommentary: String,
     @ColumnInfo(name = "fourthCommentary") var fourthCommentary: String)
{
    @PrimaryKey(autoGenerate = true) var num: Int? = null
}
