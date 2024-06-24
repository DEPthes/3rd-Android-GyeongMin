package com.example.android_basic_study_08.data.remote

import com.example.android_basic_study_08.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().run {
            addHeader("Accept-Version", "v1")
            addHeader("Authorization", "Client-ID ${BuildConfig.ACCESSKEY}")
            build()
        }

        return chain.proceed(request)
    }
}