package com.h10000b84.android.mybooklibrary.ui.scene.historyscene

import com.h10000b84.android.mybooklibrary.model.Book
import com.h10000b84.android.mybooklibrary.model.historyList
import com.h10000b84.android.mybooklibrary.util.androidThread
import com.h10000b84.android.mybooklibrary.util.ioThread
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class HistoryPresenter : HistoryContract.Presenter {

    private val subscriptions: CompositeDisposable by lazy { CompositeDisposable() }

    private lateinit var view: HistoryContract.View

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: HistoryContract.View) {
        this.view = view
    }

    override fun loadData() {
        val subscribe = Single.just(historyList)
            .subscribeOn(ioThread())
            .observeOn(androidThread())
            .doOnSuccess {
                view.showProgress(false)
            }
            .doOnError {
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
        historyList.any { b -> b.isbn13.equals(book.isbn13) }.let {
            if (it) historyList.remove(book)
        }
    }
}