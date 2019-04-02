package com.h10000b84.android.mybooklibrary.di.module

import com.h10000b84.android.mybooklibrary.api.ApiService
import com.h10000b84.android.mybooklibrary.ui.scene.bookmarkscene.BookmarkContract
import com.h10000b84.android.mybooklibrary.ui.scene.bookmarkscene.BookmarkPresenter
import com.h10000b84.android.mybooklibrary.ui.scene.common.DetailContract
import com.h10000b84.android.mybooklibrary.ui.scene.common.DetailPresenter
import com.h10000b84.android.mybooklibrary.ui.scene.historyscene.HistoryContract
import com.h10000b84.android.mybooklibrary.ui.scene.historyscene.HistoryPresenter
import com.h10000b84.android.mybooklibrary.ui.scene.newscene.NewContract
import com.h10000b84.android.mybooklibrary.ui.scene.newscene.NewPresenter
import com.h10000b84.android.mybooklibrary.ui.scene.searchscene.SearchContract
import com.h10000b84.android.mybooklibrary.ui.scene.searchscene.SearchPresenter
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {
    @Provides
    fun provideNewPresenter(): NewContract.Presenter {
        return NewPresenter()
    }

    @Provides
    fun provideDetailPresenter(): DetailContract.Presenter {
        return DetailPresenter()
    }

    @Provides
    fun provideBookmarkPresenter(): BookmarkContract.Presenter {
        return BookmarkPresenter()
    }

    @Provides
    fun provideSearchPresenter(): SearchContract.Presenter {
        return SearchPresenter()
    }

    @Provides
    fun provideHistoryPresenter(): HistoryContract.Presenter {
        return HistoryPresenter()
    }

    @Provides
    fun provideApiService(): ApiService {
        return ApiService.create()
    }
}
