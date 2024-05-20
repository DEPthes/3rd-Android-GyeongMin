package com.example.android_basic_study_06

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepositoryImpl(): ProductRepository {
    private val service = RetrofitClient.getInstance().create(ProductService::class.java)
    override suspend fun searchProducts(searchWord: String): List<Product> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.searchProducts(searchWord).execute()
                if (response.isSuccessful) {
                    response.body()?.products ?: emptyList()
                } else {
                    Log.e("Failure", "Failed to fetch data: ${response.code()}")
                    emptyList()
                }
            } catch (e: Exception) {
                Log.e("Failure", "Error: ${e.message}", e)
                emptyList()
            }
        }
    }

}