package com.h10000b84.android.mybooklibrary.ui.scene.newscene

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.h10000b84.android.mybooklibrary.R
import com.h10000b84.android.mybooklibrary.di.component.DaggerFragmentComponent
import com.h10000b84.android.mybooklibrary.di.module.FragmentModule
import com.h10000b84.android.mybooklibrary.model.Book
import kotlinx.android.synthetic.main.fragment_new.*
import javax.inject.Inject

class NewFragment : Fragment(), NewContract.View, NewListAdapter.onItemClickListener {

    @Inject
    lateinit var presenter: NewContract.Presenter

    private var newListAdapter: NewListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.subscribe()
        initView()
        //        view.findViewById<Button>(R.id.about_btn).setOnClickListener {
//            findNavController().navigate(R.id.action_new_to_about)
//        }
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
        newListAdapter?.addList(list.toMutableList())
    }

    override fun itemRemoveClick(post: Book) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun itemDetail(book: Book) {
        findNavController().navigate(R.id.action_new_to_about)
    }

    private fun injectDependency() {
        val listComponent = DaggerFragmentComponent.builder()
            .fragmentModule(FragmentModule())
            .build()

        listComponent.inject(this)
    }

    private fun initView() {
        newListAdapter = NewListAdapter(this)
        newListView!!.setLayoutManager(LinearLayoutManager(activity))
        newListView!!.setAdapter(newListAdapter)

        presenter.loadData()
    }
}