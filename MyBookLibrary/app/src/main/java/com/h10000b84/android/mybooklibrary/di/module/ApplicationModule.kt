package com.h10000b84.android.mybooklibrary.di.module

import android.app.Application
import com.h10000b84.android.mybooklibrary.BaseApp
import com.h10000b84.android.mybooklibrary.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val baseApp: BaseApp) {

    @Provides
    @Singleton
    @PerApplication
    fun provideApplication(): Application {
        return baseApp
    }
}
