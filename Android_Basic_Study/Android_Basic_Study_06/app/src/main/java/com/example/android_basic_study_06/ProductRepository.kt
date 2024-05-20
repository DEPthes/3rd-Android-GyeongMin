package com.example.android_basic_study_06

import retrofit2.Call
import retrofit2.http.Query

interface ProductRepository {
    suspend fun searchProducts(
        searchWord : String
    ): List<Product>
}