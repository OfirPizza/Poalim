package com.example.poalimtest.ui.movieList.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class MovieListUiModel(
    val movieList: List<MovieUiItemModel>,
    val currentPage: Int,
    val totalPages: Int
)

@Parcelize
data class MovieUiItemModel(
    val movieImage: ImageUiModel,
    val id: String,
    val title: String,
    val rating: Double,
    val description: String,
    val date: String,
    var isFavorite: Boolean
): Parcelable

@Parcelize
data class ImageUiModel(
    val url: String
): Parcelable

