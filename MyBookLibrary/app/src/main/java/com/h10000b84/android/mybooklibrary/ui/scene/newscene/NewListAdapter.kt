package com.h10000b84.android.mybooklibrary.ui.scene.newscene

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.h10000b84.android.mybooklibrary.R
import com.h10000b84.android.mybooklibrary.model.Book

class NewListAdapter(fragment: Fragment) : RecyclerView.Adapter<NewListAdapter.ListViewHolder>() {

    private val list: MutableList<Book> = mutableListOf()

    private val listener: NewListAdapter.onItemClickListener

    init {
        this.listener = fragment as NewListAdapter.onItemClickListener
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_new_item, parent, false)
        return NewListAdapter.ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val book = list[position]

        // holder!!.bind(post)
        holder!!.title!!.setText(book.title)
        holder.subTitle!!.setText(book.subTitle)

        holder.layout!!.setOnClickListener {
            listener.itemDetail(book)
        }
    }

    fun addList(list: MutableList<Book>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layout = itemView.findViewById<ConstraintLayout>(R.id.item_layout)
        val title = itemView.findViewById<TextView>(R.id.title_text)
        val subTitle = itemView.findViewById<TextView>(R.id.subtitle_text)
    }

    interface onItemClickListener {
        fun itemRemoveClick(post: Book)
        fun itemDetail(book: Book)
    }
}