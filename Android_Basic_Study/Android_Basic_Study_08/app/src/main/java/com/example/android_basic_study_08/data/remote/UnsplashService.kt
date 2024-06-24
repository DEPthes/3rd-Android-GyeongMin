package com.example.android_basic_study_08.data.remote

import com.example.android_basic_study_08.data.remote.model.PhotoListDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashService {
    @GET("/photos")
    suspend fun getPhotoList(
        @Query("page") page: Int
    ): Response<PhotoListDTO>
}