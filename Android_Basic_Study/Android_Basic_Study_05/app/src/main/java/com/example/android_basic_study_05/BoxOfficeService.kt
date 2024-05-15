package com.example.android_basic_study_05

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BoxOfficeService {
    @GET("/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json")
    fun getBoxOfficeList(
        @Query("key") key: String,
        @Query("targetDt") targetDt: String
    ): Call<SearchDailyBoxOfficeDTO>

}