package com.example.android_basic_study_05

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.isSuccessful) {
            val responseData = response.peekBody(Long.MAX_VALUE).string() // 응답 바디를 복사하여 사용
            val boxOfficeList = parseResponse(responseData)

            boxOfficeList.take(5).forEach { movie ->
                Log.d("MovieInfo", "${movie.rank}: ${movie.movieNm}, 개봉일: ${movie.openDt}, 누적 관객 수: ${movie.audiAcc}")
            }
        } else {
            Log.e("Failure", "Failed to fetch data: ${response.code}")
        }

        return response
    }

    private fun parseResponse(responseData: String?): List<DailyBoxOfficeList> {
        // Assuming Gson is used for JSON parsing
        return Gson().fromJson(responseData, SearchDailyBoxOfficeDTO::class.java).boxOfficeResult.dailyBoxOfficeList
    }
}

object RetrofitClient {
    private var instance : Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()
    private val baseUrl = "http://www.kobis.or.kr"

    // LoggingInterceptor를 생성하여 OkHttpClient에 추가
    private val loggingInterceptor = LoggingInterceptor()
    private val httpLoggingInterceptor = HttpLoggingInterceptor(PrettyJsonLogger())
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .build()

    fun getInstance() : Retrofit {
        if(instance == null){
            instance = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return instance!!
    }
}
