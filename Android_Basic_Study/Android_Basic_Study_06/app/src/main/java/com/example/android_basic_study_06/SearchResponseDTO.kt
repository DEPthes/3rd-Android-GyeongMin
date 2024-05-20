package com.example.android_basic_study_06

data class SearchResponseDTO (
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

data class Product (
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>,
)
