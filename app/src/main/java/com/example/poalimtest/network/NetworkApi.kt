package com.example.poalimtest.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface NetworkApi {

    companion object {
        const val API_KEY = "4e0be2c22f7268edffde97481d49064a"
        const val CONTENT_TYPE = "Content-Type:application/json"
        const val LANGUAGE = "en-US"
    }

    @Headers(CONTENT_TYPE)
    @GET("movie/now_playing")
    suspend fun getMovieList(
        @Query("api_key") key: String? = API_KEY,
        @Query("language") lang: String? = LANGUAGE,
        @Query("page") pageNumber: Int
    ): MovieResponse
}