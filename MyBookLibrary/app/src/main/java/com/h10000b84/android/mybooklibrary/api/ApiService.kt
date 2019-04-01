package com.h10000b84.android.mybooklibrary.api

import com.h10000b84.android.mybooklibrary.model.Book
import com.h10000b84.android.mybooklibrary.util.Constants
import io.reactivex.Single
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("books")
    fun getNewBookList(): Single<List<Book>>

    @GET("books/{isbn13}")
    fun getDetailBook(): Single<Book>

    @GET("search/{query}")
    fun getSearchBook(@Path("query") query: String): Single<List<Book>>

    @GET("search/{query}/{page}")
    fun getSearchBook(@Path("query") query: String, @Path("page") page: Int): Single<List<Book>>

    companion object Factory {
        fun create(): ApiService {
            val retrofit = retrofit2.Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
