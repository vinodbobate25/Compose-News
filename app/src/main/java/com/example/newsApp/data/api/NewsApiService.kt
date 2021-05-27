package com.example.newsApp.data.api

import com.example.newsApp.data.model.ApiNews
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getApiNews(  @Query("q") query:String="",@Query("apiKey") apiKey:String="2df1e5a618b9443dbc156f63c646fd20"): ApiNews
}