package com.h10000b84.android.mybooklibrary.ui.scene.common

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.h10000b84.android.mybooklibrary.R
import com.h10000b84.android.mybooklibrary.di.component.DaggerFragmentComponent
import com.h10000b84.android.mybooklibrary.di.module.FragmentModule
import com.h10000b84.android.mybooklibrary.model.DetailBook
import com.h10000b84.android.mybooklibrary.model.historyList
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject

class DetailFragment : Fragment(), DetailContract.View {
    companion object {
        const val ARGS_ISBN13 = "isbn13"
    }

    @Inject
    lateinit var presenter: DetailContract.Presenter

    private var detailBook: DetailBook? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attach(this)
        presenter.subscribe()

        arguments?.let { presenter.loadData(it.getString(ARGS_ISBN13)) }

        detail_favorite_button.setOnCheckedChangeListener { _, isChecked ->
            detailBook?.let { presenter.setFavorite(isChecked, it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unsubscribe()
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun showErrorMessage(error: String) {
        Log.e("Error", error)
    }

    override fun loadDataSuccess(detailBook: DetailBook) {
        this.detailBook = detailBook

        detail_title.text = detailBook.title
        detail_subtitle.text = detailBook.subtitle

        Glide.with(this).load(detailBook.image).into(detail_book_image)

        detail_authors.text = detailBook.authors
        detail_desc.text = detailBook.desc
        detail_isbn10.text = detailBook.isbn10
        detail_isbn13.text = detailBook.isbn13
        detail_language.text = detailBook.language
        detail_page.text = detailBook.pages
        detail_price.text = detailBook.price
        detail_publisher.text = detailBook.publisher
        detail_rating.text = detailBook.rating
        detail_year.text = detailBook.year
    }

    private fun injectDependency() {
        val listComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .build()

        listComponent.inject(this)
    }
}