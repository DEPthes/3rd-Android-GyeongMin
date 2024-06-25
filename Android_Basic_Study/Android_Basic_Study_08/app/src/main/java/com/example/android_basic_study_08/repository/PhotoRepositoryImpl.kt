package com.example.android_basic_study_08.repository

import android.util.Log
import com.example.android_basic_study_08.data.remote.RetrofitClient
import com.example.android_basic_study_08.data.remote.UnsplashService
import com.example.android_basic_study_08.entity.DetailImage
import com.example.android_basic_study_08.entity.NewImage
import com.example.android_basic_study_08.entity.RandomImage

class PhotoRepositoryImpl: PhotoRepository {
    private val service = RetrofitClient.getInstance().create(UnsplashService::class.java)
    override suspend fun getPhotoList(page: Int): Result<List<NewImage>> {
        return try {
            val res = service.getPhotoList(page)

            if(res.isSuccessful){
                val data = res.body()
                if(data != null){
                    val photoEntities = data.map { NewImage(it.id,it.urls.thumb,it.description) }
                    Result.success(photoEntities)
                } else {
                    Result.failure(Exception("Received null data"))
                }
            } else {
                Result.failure(Exception("실패"))
            }
        } catch (e: Exception){
            Log.e("error",e.message.toString())
            Result.failure(Exception("런타임 에러"))
        }
    }

    override suspend fun getPhotoDetail(id: String): Result<DetailImage> {
        return try {
            val res = service.getPhotoDetail(id)

            if (res.isSuccessful) {
                val data = res.body()
                if (data != null) {
                    val photoEntity = DetailImage(
                        title = data.id,
                        description = data.description ?: "",
                        urls = data.urls.full,
                        tag = data.tags.map { it.title },
                        user = data.user.username,
                        downloadLink = data.links.download
                    )
                    Result.success(photoEntity)
                } else {
                    Result.failure(Exception("Received null data"))
                }
            } else {
                Result.failure(Exception("Failed to fetch data: ${res.code()}"))
            }
        } catch (e: Exception) {
            Log.e("error", e.message.toString())
            Result.failure(Exception("런타임 에러"))
        }

    }

    override suspend fun getPhotoRandom(count: Int): Result<List<RandomImage>> {
        return try {
            val res = service.getPhotoRandom(count)

            if (res.isSuccessful) {
                val data = res.body()
                if(data != null){
                    val photoEntities = data.map { RandomImage(it.id, it.urls.thumb) }
                    Result.success(photoEntities)
                } else {
                    Result.failure(Exception("Received null data"))
                }
            } else {
                Result.failure(Exception("실패"))
            }
        } catch (e: Exception) {
            Log.e("error", e.message.toString())
            Result.failure(Exception("런타임 에러"))
        }
    }
}