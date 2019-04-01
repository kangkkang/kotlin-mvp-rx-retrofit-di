package com.h10000b84.android.mybooklibrary.di.component

import com.h10000b84.android.mybooklibrary.di.module.ActivityModule
import com.h10000b84.android.mybooklibrary.ui.MainActivity
import dagger.Component

@Component(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)

}