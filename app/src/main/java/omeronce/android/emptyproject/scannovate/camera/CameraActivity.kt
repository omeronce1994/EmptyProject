package omeronce.android.emptyproject.scannovate.camera

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import omeronce.android.emptyproject.Const.Companion.CAMERA_PERMISSION
import omeronce.android.emptyproject.Const.Companion.CAMERA_PERMISSION_CODE
import omeronce.android.emptyproject.R
import omeronce.android.emptyproject.databinding.ActivityCameraBinding
import omeronce.android.emptyproject.view.base.BaseActivity

class CameraActivity: BaseActivity() {

    companion object {
        fun startActivity(from: Context) {
            val intent = Intent(from, CameraActivity::class.java)
            from.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camera)
        checkCameraPermission()
    }

    @SuppressWarnings("MissingPermission")
    private fun checkCameraPermission() {
        if(hasCameraPermission()) {
            showCameraFragment()
        }
        else {
            requestCameraPermission()
        }
    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, CAMERA_PERMISSION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(CAMERA_PERMISSION), CAMERA_PERMISSION_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(hasCameraPermission()) {
            hasCameraPermission()
        }
        else {
            requestCameraPermission()
        }
    }

    @RequiresPermission(android.Manifest.permission.CAMERA)
    private fun showCameraFragment() {
        replaceFragment(binding.frmContainer.id, CameraFragment.newInstance(), CameraFragment.TAG)
    }
}