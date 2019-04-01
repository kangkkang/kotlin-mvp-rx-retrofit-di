package com.h10000b84.android.mybooklibrary.ui.scene.searchscene

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.h10000b84.android.mybooklibrary.R

/**
 * Shows "Done".
 */
class Registered : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_registered, container, false)
    }
}