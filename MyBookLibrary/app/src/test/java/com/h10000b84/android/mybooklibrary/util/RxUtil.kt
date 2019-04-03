package com.h10000b84.android.mybooklibrary.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun ioThread(): Scheduler {
    return Schedulers.trampoline()
}

fun androidThread(): Scheduler {
    return Schedulers.trampoline()
}