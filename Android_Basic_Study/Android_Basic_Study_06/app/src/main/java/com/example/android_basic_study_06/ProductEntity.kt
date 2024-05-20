package com.example.android_basic_study_06

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "JjimInfo")
data class ProductEntity(
    @PrimaryKey
    val id : Int,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val description: String,
    @ColumnInfo
    val price: Int,
    @ColumnInfo
    val discountPercentage: Double,
    @ColumnInfo
    val rating: Double,
    @ColumnInfo
    val stock: Int,
    @ColumnInfo
    val brand: String,
    @ColumnInfo
    val category: String,
    @ColumnInfo
    val thumbnail: String,
    @ColumnInfo
    val images: List<String>,
)
