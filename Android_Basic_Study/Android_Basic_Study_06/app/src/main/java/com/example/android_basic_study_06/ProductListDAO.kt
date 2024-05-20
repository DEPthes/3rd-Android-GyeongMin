package com.example.android_basic_study_06

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao

interface ProductListDAO {
    @Query("SELECT * FROM JjimInfo")
    fun getProductList(): List<ProductEntity>

    @Insert
    fun insertProduct(JjimInfo: ProductEntity) // 상품을 찜 목록에 등록

    @Query("DELETE FROM JjimInfo WHERE id = :id")
    fun deleteProductById(id: Int) // 찜 목록에서 삭제
    @Query("SELECT * FROM JjimInfo WHERE id = :id LIMIT 1")
    fun getProductById(id: Int): ProductEntity?
}