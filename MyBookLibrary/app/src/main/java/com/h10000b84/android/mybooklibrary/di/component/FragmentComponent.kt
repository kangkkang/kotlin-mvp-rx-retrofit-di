package com.h10000b84.android.mybooklibrary.di.component

import com.h10000b84.android.mybooklibrary.di.module.FragmentModule
import com.h10000b84.android.mybooklibrary.ui.scene.bookmarkscene.BookmarkFragment
import com.h10000b84.android.mybooklibrary.ui.scene.historyscene.History
import com.h10000b84.android.mybooklibrary.ui.scene.historyscene.UserProfile
import com.h10000b84.android.mybooklibrary.ui.scene.common.DetailFragment
import com.h10000b84.android.mybooklibrary.ui.scene.newscene.NewFragment
import com.h10000b84.android.mybooklibrary.ui.scene.searchscene.SearchFragment
import dagger.Component

@Component(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

    fun inject(newFragment: NewFragment)
    fun inject(detailFragment: DetailFragment)
    fun inject(bookmarkFragment: BookmarkFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(history: History)
    fun inject(userProfile: UserProfile)
}