package com.h10000b84.android.mybooklibrary.ui.scene.newscene

import com.h10000b84.android.mybooklibrary.model.Book
import com.h10000b84.android.mybooklibrary.ui.base.BaseContract

class NewContract {
    interface View: BaseContract.View {
        fun showProgress(show: Boolean)
        fun showRefreshing(isRefreshing: Boolean)
        fun showErrorMessage(error: String)
        fun loadDataSuccess(list: List<Book>)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun loadData()
    }
}