package omeronce.android.onelineradapter

import androidx.annotation.LayoutRes

internal data class AdapterConfiguration<T>(
        @LayoutRes internal var layoutId: Int = 0,
        internal var variableId: Int = -1,
        internal var clickListener: ((T) -> Unit)? = null
)