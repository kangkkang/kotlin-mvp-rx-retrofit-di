package com.h10000b84.android.mybooklibrary.ui.scene.newscene

import android.app.Activity
import android.content.Context
import android.util.Log
import com.h10000b84.android.mybooklibrary.api.ApiService
import com.h10000b84.android.mybooklibrary.model.Book
import com.h10000b84.android.mybooklibrary.model.DetailBook
import com.h10000b84.android.mybooklibrary.model.favoriteList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailPresenter: DetailContract.Presenter {

    private val subscriptions : CompositeDisposable by lazy { CompositeDisposable() }

    private val api: ApiService = ApiService.create()
    private lateinit var view: DetailContract.View

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: DetailContract.View) {
        this.view = view
    }

    override fun loadData(isbn13: String) {
        val subscribe = api.getDetailBook(isbn13)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                view.showProgress(false)
            }
            .doOnError {
                view.showProgress(false)
            }
            .subscribe({ detailBook: DetailBook? ->
                view.loadDataSuccess(detailBook!!)
            }, { error ->
                view.showErrorMessage(error.localizedMessage)
            })

        subscriptions.add(subscribe)
    }

    override fun setFavorite(isFavorite: Boolean, book: DetailBook) {
        favoriteList.add(Book(book.title, book.subtitle, book.isbn13, book.price, book.image, book.url))
        Log.e("tag", "add favroriri ${favoriteList.size}")
    }
}