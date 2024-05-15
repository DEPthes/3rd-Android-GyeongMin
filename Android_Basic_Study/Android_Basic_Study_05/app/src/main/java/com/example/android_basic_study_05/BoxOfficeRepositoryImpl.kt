package com.example.android_basic_study_05
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class BoxOfficeRepositoryImpl() : BoxOfficeRepository {
    private var service: BoxOfficeService = RetrofitClient.getInstance().create(BoxOfficeService::class.java)

    override suspend fun getBoxOfficeList(key: String, targetDt: String): List<DailyBoxOfficeList> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getBoxOfficeList(key, targetDt).execute()
                if (response.isSuccessful) {
                    response.body()?.boxOfficeResult?.dailyBoxOfficeList ?: emptyList()
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