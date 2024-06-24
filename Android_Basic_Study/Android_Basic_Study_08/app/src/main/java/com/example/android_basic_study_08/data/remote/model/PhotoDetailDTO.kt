package com.example.android_basic_study_08.data.remote.model

data class PhotoDetailDTO(
    val blur_hash: String,
    val color: String,
    val created_at: String,
    val current_user_collections: List<CurrentUserCollection>,
    val description: String,
    val downloads: Int,
    val exif: Exif,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: Links,
    val location: Location,
    val public_domain: Boolean,
    val tags: List<Tag>,
    val updated_at: String,
    val urls: Urls,
    val user: User,
    val width: Int
)

data class Exif(
    val aperture: String,
    val exposure_time: String,
    val focal_length: String,
    val iso: Int,
    val make: String,
    val model: String,
    val name: String
)

data class Location(
    val city: String,
    val country: String,
    val position: Position
)

data class Tag(
    val title: String
)

data class Position(
    val latitude: Double,
    val longitude: Double
)