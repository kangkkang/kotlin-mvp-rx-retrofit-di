package com.h10000b84.android.mybooklibrary.ui.scene.historyscene

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.h10000b84.android.mybooklibrary.R
import com.h10000b84.android.mybooklibrary.model.Book

class HistoryListAdapter(fragment: Fragment) : RecyclerView.Adapter<HistoryListAdapter.ListViewHolder>() {

    private val list: MutableList<Book> = mutableListOf()

    private val listener: HistoryListAdapter.onItemClickListener

    init {
        this.listener = fragment as HistoryListAdapter.onItemClickListener
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_new_item, parent, false)
        return HistoryListAdapter.ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val book = list[position]

        holder.title!!.setText(book.title)
        holder.subTitle!!.setText(book.subtitle)
        holder.isbn13!!.setText(book.isbn13)
        holder.price!!.setText(book.price)
        Glide.with(holder.image).load(book.image).apply(RequestOptions().circleCrop()).into(holder.image)

        holder.layout!!.setOnClickListener {
            listener.itemDetail(book)
        }
    }

    fun addList(list: MutableList<Book>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun removeAt(position: Int): Book {
        val book = list.removeAt(position)
        notifyItemRemoved(position)

        return book
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layout = itemView.findViewById<ConstraintLayout>(R.id.item_layout)
        val image = itemView.findViewById<ImageView>(R.id.book_image)
        val title = itemView.findViewById<TextView>(R.id.title_text)
        val subTitle = itemView.findViewById<TextView>(R.id.subtitle_text)
        val isbn13 = itemView.findViewById<TextView>(R.id.isbn13_text)
        val price = itemView.findViewById<TextView>(R.id.price_text)
    }

    interface onItemClickListener {
        fun itemDetail(book: Book)
    }
}