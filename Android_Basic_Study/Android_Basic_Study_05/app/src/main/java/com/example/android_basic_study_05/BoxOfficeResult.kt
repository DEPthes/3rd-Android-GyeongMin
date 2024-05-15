package com.example.android_basic_study_05

data class BoxOfficeResult(
    val boxofficeType: String,
    val showRange: String,
    val dailyBoxOfficeList: List<DailyBoxOfficeList>
)
