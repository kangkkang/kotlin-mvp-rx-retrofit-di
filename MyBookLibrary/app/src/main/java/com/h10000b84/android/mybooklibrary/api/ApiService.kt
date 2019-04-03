package com.h10000b84.android.mybooklibrary.api

import com.h10000b84.android.mybooklibrary.model.DetailBook
import com.h10000b84.android.mybooklibrary.api.response.BookResponse
import com.h10000b84.android.mybooklibrary.util.Constants
import io.reactivex.Single
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("new")
    fun getNewBookList(): Single<BookResponse>

    @GET("books/{isbn13}")
    fun getDetailBook(@Path("isbn13") isbn13: String): Single<DetailBook>

    @GET("search/{query}")
    fun getSearchBook(@Path("query") query: String): Single<BookResponse>

    @GET("search/{query}/{page}")
    fun getSearchBook(@Path("query") query: String, @Path("page") page: Int): Single<BookResponse>

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
