package omeronce.android.onelineradapter.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalArgumentException

open class GenericViewHolder(val variableId: Int, val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {

    fun <T> onBind(value : T) {
        val valid = binding.setVariable(variableId, value)
        if(!valid) {
            throw IllegalArgumentException("variableId is incorrect")
        }
        else {
            binding.executePendingBindings()
        }
    }
}