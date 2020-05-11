package omeronce.android.emptyproject.books.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import omeronce.android.emptyproject.R
import omeronce.android.emptyproject.databinding.ItemBookBinding
import omeronce.android.emptyproject.model.books.Book


class BooksAdapter: ListAdapter<Book, BooksAdapter.BookViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = DataBindingUtil.inflate<ItemBookBinding>(LayoutInflater.from(parent.context),R.layout.item_book, parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BookViewHolder(val binding: ItemBookBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book) {
            binding.book = book
            binding.executePendingBindings()
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.url == oldItem.url
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }
        }
    }
}
