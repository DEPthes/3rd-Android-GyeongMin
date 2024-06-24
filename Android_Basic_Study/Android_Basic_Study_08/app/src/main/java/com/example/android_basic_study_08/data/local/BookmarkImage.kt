package com.example.android_basic_study_08.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BookmarkImage")

data class BookmarkImage (
    @PrimaryKey
    val id : String,
    @ColumnInfo
    val urls: String
)