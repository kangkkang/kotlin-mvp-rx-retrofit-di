package com.h10000b84.android.mybooklibrary.ui.scene.searchscene

import com.h10000b84.android.mybooklibrary.model.Book
import com.h10000b84.android.mybooklibrary.ui.base.BaseContract
import io.reactivex.disposables.Disposable

class SearchContract {
    interface View: BaseContract.View {
        fun showProgress(show: Boolean)
        fun showErrorMessage(error: String)
        fun loadDataSuccess(list: List<Book>)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun addDisposable(d: Disposable)
        fun searchData(query: String)
    }
}