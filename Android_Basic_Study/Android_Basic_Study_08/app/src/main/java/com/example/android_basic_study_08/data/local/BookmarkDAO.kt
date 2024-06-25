package com.example.android_basic_study_08.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookmarkDAO {
    @Query("SELECT * FROM BookmarkImage")
    fun getBookmark(): List<BookmarkImage>

    @Query("SELECT COUNT(*) FROM BookmarkImage WHERE id = :id")
    fun isBookmark(id: String): Int

    @Insert
    fun insertBookmark(bookmarkImage: BookmarkImage)

    @Query("DELETE FROM BookmarkImage WHERE id = :id ")
    fun deleteBookmark(id: String)
}