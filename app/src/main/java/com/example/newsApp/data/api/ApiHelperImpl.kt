package com.example.newsApp.data.api

import com.example.newsApp.data.model.ApiNews
import java.time.temporal.TemporalQuery
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private  val newsApiService: NewsApiService): ApiHelper {
    override suspend fun getNews(query:String): ApiNews =newsApiService.getApiNews(query=query)


}