package omeronce.android.emptyproject.scannovate.camera

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import omeronce.android.emptyproject.Const.Companion.PIC_FILE_NAME
import omeronce.android.emptyproject.R
import omeronce.android.emptyproject.databinding.FragmentCameraBinding
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.scannovate.camera.helper.CameraFragmentHelper
import omeronce.android.emptyproject.scannovate.camera.viewmodel.CameraViewModel
import omeronce.android.emptyproject.view.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File

class CameraFragment: BaseFragment(), ActivityCompat.OnRequestPermissionsResultCallback {

    companion object {

        @JvmStatic
        val TAG = "CameraFragment"

        @JvmStatic
        @RequiresPermission(android.Manifest.permission.CAMERA)
        fun newInstance(): CameraFragment {
            val bundle = Bundle()
            val fragment = CameraFragment().apply { arguments = bundle }
            return fragment
        }
    }

    private val viewModel: CameraViewModel by viewModel()
    private lateinit var binding: FragmentCameraBinding
    private lateinit var cameraFragmentHelper: CameraFragmentHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initHelper()
        initObservers()
    }

    private fun initBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.clicks = ClickListeners()
    }

    fun initHelper() {
        val activity = activity ?: return
        val file = File(activity.getExternalFilesDir(null), PIC_FILE_NAME)
        viewModel.initCameraHelper(this, binding.texture, file)
    }

    private fun initObservers() {
        viewModel.json.observe(viewLifecycleOwner, Observer {
            var text = ""
            when (it) {
                is Result.Success -> text = getString(R.string.message_succesful_request)
                is Result.Error -> text = it.exception.message ?: getString(R.string.error_message_unknown)
            }
            onRequestResult(text)
        })
    }

    private fun onRequestResult(text: String) {
        activity?.let {
            Toast.makeText(it.applicationContext, text, Toast.LENGTH_LONG).show()
            it.finish()
        }
    }

    inner class ClickListeners {
        fun onTakeImageClick(view: View) {
            viewModel.captureImage()
        }
    }
}
