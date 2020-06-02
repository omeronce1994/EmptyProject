package omeronce.android.emptyproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import omeronce.android.emptyproject.scannovate.main.MainFragment
import omeronce.android.emptyproject.view.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(R.id.frm_container, MainFragment.newInstance(), MainFragment.TAG)
    }
}
