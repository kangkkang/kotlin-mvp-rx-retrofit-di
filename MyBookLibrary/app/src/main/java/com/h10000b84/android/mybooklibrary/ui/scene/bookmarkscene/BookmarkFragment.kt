package com.h10000b84.android.mybooklibrary.ui.scene.bookmarkscene

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.h10000b84.android.mybooklibrary.R
import com.h10000b84.android.mybooklibrary.di.component.DaggerFragmentComponent
import com.h10000b84.android.mybooklibrary.di.module.FragmentModule
import com.h10000b84.android.mybooklibrary.model.Book
import com.h10000b84.android.mybooklibrary.ui.scene.common.DetailFragment
import com.h10000b84.android.mybooklibrary.ui.util.SwipeToDelete
import kotlinx.android.synthetic.main.fragment_bookmark.*
import javax.inject.Inject

class BookmarkFragment : Fragment(), BookmarkContract.View, BookmarkListAdapter.onItemClickListener {

    @Inject
    lateinit var presenter: BookmarkContract.Presenter

    private var bookmarkListAdapter: BookmarkListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
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

    override fun showRefreshing(isRefreshing: Boolean) {
        refreshLayout?.isRefreshing = isRefreshing
    }

    override fun showErrorMessage(error: String) {
        Log.e("Error", error)
    }

    override fun loadDataSuccess(list: List<Book>) {
        bookmarkListAdapter?.addList(list.toMutableList())

        this.context?.let {
            val swipeHandler = object : SwipeToDelete(it) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val adapter = bookmarkListView.adapter as BookmarkListAdapter
                    presenter.deleteItem(adapter.removeAt(viewHolder.adapterPosition))
                }
            }

            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(bookmarkListView)
        }
    }

    override fun itemDetail(book: Book) {
        var bundle = bundleOf(DetailFragment.ARGS_ISBN13 to book.isbn13)
        findNavController().navigate(R.id.action_bookmark_to_detail, bundle)
    }

    private fun injectDependency() {
        val listComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .build()

        listComponent.inject(this)
    }

    private fun initView() {
        bookmarkListAdapter = BookmarkListAdapter(this)
        bookmarkListView!!.setLayoutManager(LinearLayoutManager(activity))
        bookmarkListView!!.setAdapter(bookmarkListAdapter)

        refreshLayout.setOnRefreshListener {
            presenter.loadData()
        }

        presenter.loadData()
    }
}