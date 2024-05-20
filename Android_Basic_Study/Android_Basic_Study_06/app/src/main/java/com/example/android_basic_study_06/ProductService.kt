package com.example.android_basic_study_06

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("products/search")
    fun searchProducts(
        @Query("q") searchWord : String
    ): Call<SearchResponseDTO>

}