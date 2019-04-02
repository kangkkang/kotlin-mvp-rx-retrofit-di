package com.h10000b84.android.mybooklibrary.ui.scene.historyscene

import com.h10000b84.android.mybooklibrary.model.Book
import com.h10000b84.android.mybooklibrary.model.favoriteList
import com.h10000b84.android.mybooklibrary.model.historyList
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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

    override fun deleteItem(item: Book) {
        historyList.remove(item)
    }
}