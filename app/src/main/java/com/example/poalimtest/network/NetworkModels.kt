package com.example.poalimtest.network

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results") val movieList: List<MovieItemResponse>?,
    @SerializedName("page") val pageNumber: Int?,
    @SerializedName("total_pages") val totalPages: Int?
)

data class MovieItemResponse(
    @SerializedName("poster_path") val poster: String?,
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String?,
    @SerializedName("vote_average") val rating: Double?,
    @SerializedName("overview") val description: String?,
    @SerializedName("release_date") val date: String?
)