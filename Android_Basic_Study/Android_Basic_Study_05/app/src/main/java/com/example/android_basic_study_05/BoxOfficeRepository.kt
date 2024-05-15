package com.example.android_basic_study_05

import retrofit2.Call
import retrofit2.http.Query

interface BoxOfficeRepository {
    suspend fun getBoxOfficeList(
        key: String,
        targetDt: String
    ): List<DailyBoxOfficeList>
}