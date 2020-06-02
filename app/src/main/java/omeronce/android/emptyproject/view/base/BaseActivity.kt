package omeronce.android.emptyproject.view.base

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseActivity: AppCompatActivity() {

    @JvmOverloads
    protected fun replaceFragment(@IdRes containerId: Int, fragment: Fragment, tag: String, addToBackStack: Boolean = false) {
        supportFragmentManager.beginTransaction().replace(containerId,fragment, tag).commit()
    }
}