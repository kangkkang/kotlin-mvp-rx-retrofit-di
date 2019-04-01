package com.h10000b84.android.mybooklibrary.di.component

import com.h10000b84.android.mybooklibrary.BaseApp
import com.h10000b84.android.mybooklibrary.di.module.ApplicationModule
import dagger.Component

@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(application: BaseApp)

}