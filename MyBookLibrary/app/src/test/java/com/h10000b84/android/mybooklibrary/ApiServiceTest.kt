package com.h10000b84.android.mybooklibrary

import com.h10000b84.android.mybooklibrary.api.ApiService
import org.junit.Before
import org.junit.Test

class ApiServiceTest {
    private lateinit var api: ApiService

    @Before
    fun setup() {
        api = ApiService.create()
    }

    @Test
    fun `Check service APIs`() {
        api.getNewBookList()
            .test()
            .await()
            .assertNoErrors()
            .assertValue { it.error.equals("0") }

        api.getDetailBook("9781784394325")
            .test()
            .await()
            .assertNoErrors()
            .assertValue { it.error.equals("0") }

        api.getSearchBook("dd")
            .test()
            .await()
            .assertNoErrors()
            .assertValue { it.error.equals("0") }

        api.getSearchBook("dd", 2)
            .test()
            .await()
            .assertNoErrors()
            .assertValue { it.error.equals("0") }
    }
}