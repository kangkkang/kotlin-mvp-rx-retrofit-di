package com.h10000b84.android.mybooklibrary.ui.scene.searchscene

import com.h10000b84.android.mybooklibrary.api.ApiService
import com.h10000b84.android.mybooklibrary.model.Book
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchPresenter: SearchContract.Presenter {

    val subscriptions : CompositeDisposable by lazy { CompositeDisposable() }

    private val api: ApiService = ApiService.create()
    private lateinit var view: SearchContract.View

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: SearchContract.View) {
        this.view = view
    }

    override fun searchData(query: String) {
        val subscribe = api.getSearchBook(query)
            .subscribeOn(Schedulers.io())
            .map {
                if (it.error.equals("0")) {
                    it.books
                } else {
                    listOf()
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                view.showProgress(true)
            }
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

    override fun addDisposable(d: Disposable) {
        subscriptions.add(d)
    }
}