package com.h10000b84.android.mybooklibrary.api.response

import com.h10000b84.android.mybooklibrary.model.Book

data class BookResponse(val error: String, val total: String, val books: List<Book>)