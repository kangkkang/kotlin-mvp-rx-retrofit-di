package com.h10000b84.android.mybooklibrary.di.module

import com.h10000b84.android.mybooklibrary.api.ApiService
import com.h10000b84.android.mybooklibrary.ui.scene.bookmarkscene.BookmarkContract
import com.h10000b84.android.mybooklibrary.ui.scene.bookmarkscene.BookmarkPresenter
import com.h10000b84.android.mybooklibrary.ui.scene.newscene.DetailContract
import com.h10000b84.android.mybooklibrary.ui.scene.newscene.DetailPresenter
import com.h10000b84.android.mybooklibrary.ui.scene.newscene.NewContract
import com.h10000b84.android.mybooklibrary.ui.scene.newscene.NewPresenter
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
    fun provideApiService(): ApiService {
        return ApiService.create()
    }
}
