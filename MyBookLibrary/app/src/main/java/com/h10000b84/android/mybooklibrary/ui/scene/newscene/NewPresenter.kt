package com.h10000b84.android.mybooklibrary.ui.scene.newscene

import com.h10000b84.android.mybooklibrary.api.ApiService
import com.h10000b84.android.mybooklibrary.model.Book
import com.h10000b84.android.mybooklibrary.util.androidThread
import com.h10000b84.android.mybooklibrary.util.ioThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewPresenter: NewContract.Presenter {

    private val subscriptions : CompositeDisposable by lazy { CompositeDisposable() }

    private val api: ApiService = ApiService.create()
    private lateinit var view: NewContract.View

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: NewContract.View) {
        this.view = view
    }

    override fun loadData() {
        val subscribe = api.getNewBookList()
            .subscribeOn(ioThread())
            .map {
                if (it.error.equals("0")) {
                    it.books
                } else {
                    listOf()
                }
            }
            .observeOn(androidThread())
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
}