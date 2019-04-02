package com.h10000b84.android.mybooklibrary.ui.scene.common

import com.h10000b84.android.mybooklibrary.model.DetailBook
import com.h10000b84.android.mybooklibrary.ui.base.BaseContract

class DetailContract {
    interface View: BaseContract.View {
        fun showProgress(show: Boolean)
        fun showErrorMessage(error: String)
        fun loadDataSuccess(detailBook: DetailBook)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun loadData(isbn13: String)
        fun setFavorite(isFavorite: Boolean, book: DetailBook)
    }
}