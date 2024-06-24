package com.example.android_basic_study_08.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BookmarkImage::class], version = 1, exportSchema = false)
abstract class BookmarkDB: RoomDatabase() {
    abstract fun getBookmarkDAO(): BookmarkDAO

    companion object {
        @Volatile
        private var INSTANCE: BookmarkDB? = null

        private fun buildDatabase(context: Context): BookmarkDB =
            Room.databaseBuilder(
                context.applicationContext,
                BookmarkDB::class.java,
                "Bookmark"
            ).build()

        fun getInstance(context: Context): BookmarkDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
    }
}