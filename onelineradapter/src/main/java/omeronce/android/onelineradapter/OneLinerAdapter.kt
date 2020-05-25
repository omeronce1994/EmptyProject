package omeronce.android.onelineradapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import omeronce.android.onelineradapter.viewholder.GenericViewHolder
import java.lang.IllegalStateException


open class OneLinerAdapter<T> protected constructor(diffUtil: DiffUtil.ItemCallback<T>, builder:Builder<T>): ListAdapter<T, GenericViewHolder>(diffUtil) {

    private val configuration: AdapterConfiguration<T>

    init {
        configuration = builder.configuration
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(configuration.layoutId, parent, false)
        val binding: ViewDataBinding = DataBindingUtil.bind(view) ?: throw IllegalStateException("No matching layout binding found for given resources." +
                "Check if the layout is indeed wrapped using the layout tag and the layout id is correct.")
        val holder = GenericViewHolder(configuration.variableId, binding)
        setItemClickListener(holder)
        return holder
    }


    private fun setItemClickListener(holder: GenericViewHolder) {
        configuration.clickListener?.let {clickListener ->
            holder.itemView.setOnClickListener {
                val position = holder.adapterPosition
                val item = getItem(position)
                clickListener.invoke(item)
            }
        }
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
    }

    open class Builder<T> {
        internal val configuration = AdapterConfiguration<T>()

        fun withLayout(@LayoutRes layoutId: Int): Builder<T> {
            configuration.layoutId = layoutId
            return this
        }


        fun toVariableId(variableId: Int): Builder<T> {
            configuration.variableId = variableId
            return this
        }

        fun withItemClickListener(clickListener: (T) -> Unit): Builder<T> {
            configuration.clickListener = clickListener
            return this
        }

        fun setDiffUtil(diffUtil: DiffUtil.ItemCallback<T>) = Creator(diffUtil, this)
    }

    class Creator<T>(private val diffUtil: DiffUtil.ItemCallback<T>, private val builder: Builder<T>) {
        fun build() =  OneLinerAdapter(diffUtil, builder)
    }
}