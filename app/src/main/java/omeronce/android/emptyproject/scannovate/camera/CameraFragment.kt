package omeronce.android.emptyproject.scannovate.camera

import android.os.Bundle
import androidx.core.app.ActivityCompat
import omeronce.android.emptyproject.view.base.BaseFragment

class CameraFragment: BaseFragment(), ActivityCompat.OnRequestPermissionsResultCallback {

    companion object {

        @JvmStatic
        val TAG = "CameraFragment"

        @JvmStatic
        fun newInstance(): CameraFragment {
            val bundle = Bundle()
            val fragment = CameraFragment().apply { arguments = bundle }
            return fragment
        }
    }
}
