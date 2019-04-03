package com.h10000b84.android.mybooklibrary.ui.scene.common

import android.util.Log
import com.h10000b84.android.mybooklibrary.api.ApiService
import com.h10000b84.android.mybooklibrary.model.Book
import com.h10000b84.android.mybooklibrary.model.DetailBook
import com.h10000b84.android.mybooklibrary.model.favoriteList
import com.h10000b84.android.mybooklibrary.model.historyList
import com.h10000b84.android.mybooklibrary.util.androidThread
import com.h10000b84.android.mybooklibrary.util.ioThread
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
            .subscribeOn(ioThread())
            .observeOn(androidThread())
            .doOnSuccess {
                view.showProgress(false)
            }
            .doOnError {
                view.showProgress(false)
            }
            .subscribe({ detailBook: DetailBook? ->
                historyList.any { book -> book.isbn13.equals(detailBook?.isbn13) }.let {
                    if (!it) historyList.add(Book(detailBook!!.title, detailBook!!.subtitle, detailBook!!.isbn13, detailBook!!.price, detailBook!!.image, detailBook!!.url))
                }

                view.loadDataSuccess(detailBook!!)
            }, { error ->
                view.showErrorMessage(error.localizedMessage)
            })

        subscriptions.add(subscribe)
    }

    override fun setFavorite(isFavorite: Boolean, book: DetailBook) {
        favoriteList.any { b -> b.isbn13.equals(book.isbn13) }.let {
            if (!it) favoriteList.add(Book(book.title, book.subtitle, book.isbn13, book.price, book.image, book.url))
        }

        Log.e("tag", "add favroriri ${favoriteList.size}")
    }
}