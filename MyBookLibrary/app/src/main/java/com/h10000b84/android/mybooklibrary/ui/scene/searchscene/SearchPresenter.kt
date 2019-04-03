package com.h10000b84.android.mybooklibrary.ui.scene.searchscene

import com.h10000b84.android.mybooklibrary.api.ApiService
import com.h10000b84.android.mybooklibrary.model.Book
import com.h10000b84.android.mybooklibrary.model.searchList
import com.h10000b84.android.mybooklibrary.util.androidThread
import com.h10000b84.android.mybooklibrary.util.ioThread
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class SearchPresenter: SearchContract.Presenter {

    val subscriptions : CompositeDisposable by lazy { CompositeDisposable() }

    private val api: ApiService = ApiService.create()
    private lateinit var view: SearchContract.View

    private var query: String = ""
    private var currentPage: Int = 1
    private var endOfPage: Boolean = false

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: SearchContract.View) {
        this.view = view
    }

    override fun searchData(query: String) {
        this.endOfPage = false
        this.currentPage = 1
        this.query = query

        val subscribe = api.getSearchBook(query)
            .subscribeOn(ioThread())
            .map {
                if (it.error.equals("0")) {
                    it.books
                } else {
                    listOf()
                }
            }
            .observeOn(androidThread())
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

    override fun loadNextPage() {
        if (endOfPage) {
            return
        }

        currentPage++

        val subscribe = api.getSearchBook(query, currentPage)
            .subscribeOn(ioThread())
            .map {
                if (it.error.equals("0")) {
                    it.books
                } else {
                    listOf()
                }
            }
            .observeOn(androidThread())
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
                list?.let { endOfPage = it.isEmpty() }

                view.loadNextPageSuccess(list!!)
            }, { error ->
                view.showErrorMessage(error.localizedMessage)
            })

        subscriptions.add(subscribe)
    }

    override fun addDisposable(d: Disposable) {
        subscriptions.add(d)
    }

    override fun loadData() {
        if (searchList.size == 0) {
            return
        }

        val subscribe = Single.just(searchList)
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
                searchList.clear()
            }, { error ->
                view.showErrorMessage(error.localizedMessage)
            })

        subscriptions.add(subscribe)
    }

    override fun saveList(list: List<Book>) {
        searchList.clear()
        searchList.addAll(list)
    }
}