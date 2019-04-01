package com.h10000b84.android.mybooklibrary.di.component

import com.h10000b84.android.mybooklibrary.di.module.FragmentModule
import com.h10000b84.android.mybooklibrary.ui.scene.historyscene.History
import com.h10000b84.android.mybooklibrary.ui.scene.historyscene.UserProfile
import com.h10000b84.android.mybooklibrary.ui.scene.newscene.About
import com.h10000b84.android.mybooklibrary.ui.scene.newscene.NewFragment
import com.h10000b84.android.mybooklibrary.ui.scene.searchscene.Register
import com.h10000b84.android.mybooklibrary.ui.scene.searchscene.Registered
import dagger.Component

@Component(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

    fun inject(newFragment: NewFragment)
    fun inject(about: About)
    fun inject(register: Register)
    fun inject(registered: Registered)
    fun inject(history: History)
    fun inject(userProfile: UserProfile)
}