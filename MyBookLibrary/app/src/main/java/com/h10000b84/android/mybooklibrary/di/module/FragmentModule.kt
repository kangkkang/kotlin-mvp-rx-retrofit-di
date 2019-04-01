package com.h10000b84.android.mybooklibrary.di.module

import com.h10000b84.android.mybooklibrary.api.ApiService
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
    fun provideApiService(): ApiService {
        return ApiService.create()
    }
}
