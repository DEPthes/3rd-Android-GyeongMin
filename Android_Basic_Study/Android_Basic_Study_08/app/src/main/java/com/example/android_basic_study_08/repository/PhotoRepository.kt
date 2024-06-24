package com.example.android_basic_study_08.repository

import com.example.android_basic_study_08.data.remote.model.PhotoListDTO
import com.example.android_basic_study_08.entity.NewImage

interface PhotoRepository {
    suspend fun getPhotoList(
        page : Int
    ): Result<List<NewImage>>
}