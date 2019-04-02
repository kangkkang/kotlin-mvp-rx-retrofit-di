package com.h10000b84.android.mybooklibrary.ui.scene.searchscene

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.h10000b84.android.mybooklibrary.R
import com.h10000b84.android.mybooklibrary.di.component.DaggerFragmentComponent
import com.h10000b84.android.mybooklibrary.di.module.FragmentModule
import com.h10000b84.android.mybooklibrary.model.Book
import com.h10000b84.android.mybooklibrary.ui.scene.common.DetailFragment
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchFragment : Fragment(), SearchContract.View, SearchListAdapter.onItemClickListener {

    @Inject
    lateinit var presenter: SearchContract.Presenter

    private var searchListAdapter: SearchListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attach(this)
        presenter.subscribe()
        initView()
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

    override fun loadDataSuccess(list: List<Book>) {
        searchListAdapter?.addList(list.toMutableList())
    }

    override fun itemDetail(book: Book) {
        var bundle = bundleOf(DetailFragment.ARGS_ISBN13 to book.isbn13)
        findNavController().navigate(R.id.action_search_to_detail, bundle)
    }

    private fun injectDependency() {
        val listComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .build()

        listComponent.inject(this)
    }

    private fun initView() {
        searchListAdapter = SearchListAdapter(this)
        searchListView!!.setLayoutManager(LinearLayoutManager(activity))
        searchListView!!.setAdapter(searchListAdapter)

        searchButton.clicks()
            .throttleFirst(
                500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()
            )
            .filter { inputKeyword?.text?.isNotEmpty() ?: false }
            .subscribe { presenter.searchData(inputKeyword.text.toString()) }
            .apply { presenter.addDisposable(this) }
    }
}