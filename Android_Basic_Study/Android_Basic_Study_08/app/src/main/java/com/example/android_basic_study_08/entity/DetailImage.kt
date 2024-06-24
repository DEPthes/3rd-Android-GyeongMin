package com.example.android_basic_study_08.entity

data class DetailImage (
    val title : String,
    val description : String,
    val urls : String,
    val tag: List<String>,
    val user: String,
    val downloadLink: String
)