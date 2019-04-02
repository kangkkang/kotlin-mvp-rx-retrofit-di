package com.h10000b84.android.mybooklibrary.ui.scene.bookmarkscene

import com.h10000b84.android.mybooklibrary.api.ApiService
import com.h10000b84.android.mybooklibrary.model.Book
import com.h10000b84.android.mybooklibrary.model.favoriteList
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BookmarkPresenter: BookmarkContract.Presenter {

    private val subscriptions : CompositeDisposable by lazy { CompositeDisposable() }

    private lateinit var view: BookmarkContract.View

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: BookmarkContract.View) {
        this.view = view
    }

    override fun loadData() {
        val subscribe = Single.just(favoriteList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                view.showRefreshing(false)
                view.showProgress(false)
            }
            .doOnError {
                view.showRefreshing(false)
                view.showProgress(false)
            }
            .subscribe({ list: List<Book>? ->
                view.loadDataSuccess(list!!)
            }, { error ->
                view.showErrorMessage(error.localizedMessage)
            })

        subscriptions.add(subscribe)
    }

    override fun deleteItem(book: Book) {
        favoriteList.any { b -> b.isbn13.equals(book.isbn13) }.let {
            if (it) favoriteList.remove(book)
        }
    }
}