package com.example.bjsdk.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataResponse(
    val dates: Dates? = null,
    val page: Long,
    val results: List<Result>,
    @SerializedName("total_pages")
    @Expose
    val totalPages: Long,
    @SerializedName("total_results")
    @Expose
    val totalResults: Long,
)

data class Dates(
    val maximum: String,
    val minimum: String,
)

data class Result(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String,
    @SerializedName("genre_ids")
    @Expose
    val genreIds: List<Long>,
    val id: Long,
    @SerializedName("original_language")
    @Expose
    val originalLanguage: String,
    @SerializedName("original_title")
    @Expose
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    @Expose
    val posterPath: String,
    @SerializedName("release_date")
    @Expose
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double,
    @SerializedName("vote_count")
    @Expose
    val voteCount: Long,
)