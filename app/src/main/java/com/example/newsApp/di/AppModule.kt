package com.example.newsApp.di



import com.example.newsApp.data.api.ApiHelper
import com.example.newsApp.data.api.ApiHelperImpl
import com.example.newsApp.data.api.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private val BASE_URL:String="https://newsapi.org/v2/"


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
       return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(NewsApiService::class.java)!!

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper
}