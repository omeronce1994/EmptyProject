package omeronce.android.emptyproject.scannovate.camera

import android.content.Context
import android.graphics.ImageFormat
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.media.ImageReader
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import omeronce.android.emptyproject.R
import omeronce.android.emptyproject.databinding.FragmentCameraBinding
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.scannovate.camera.viewmodel.CameraViewModel
import omeronce.android.emptyproject.view.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

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
    val surfaceReadyCallback = object: SurfaceHolder.Callback {
        override fun surfaceChanged(p0: SurfaceHolder?, format: Int, width: Int, height: Int) { }
        override fun surfaceDestroyed(p0: SurfaceHolder?) { }

        override fun surfaceCreated(p0: SurfaceHolder?) {
            startCameraSession()
        }
    }
    private lateinit var cameraDevice: CameraDevice

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
        binding.surfaceView.holder.addCallback(surfaceReadyCallback)
        initObservers()
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

    @SuppressWarnings("MissingPermission")
    private fun startCameraSession() {
        val activity = activity ?: return
        val cameraManager = activity.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        if (cameraManager.cameraIdList.isEmpty()) {
            // no cameras
            return
        }
        val firstCamera = cameraManager.cameraIdList[0]
        cameraManager.openCamera(firstCamera, object: CameraDevice.StateCallback() {
            override fun onDisconnected(p0: CameraDevice) { }
            override fun onError(p0: CameraDevice, p1: Int) { }

            override fun onOpened(cameraDevice: CameraDevice) {
                // use the camera
                this@CameraFragment.cameraDevice = cameraDevice
                val cameraCharacteristics =    cameraManager.getCameraCharacteristics(cameraDevice.id)

                cameraCharacteristics[CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP]?.let { streamConfigurationMap ->
                    streamConfigurationMap.getOutputSizes(ImageFormat.YUV_420_888)?.let { yuvSizes ->
                        val previewSize = yuvSizes.last()
                        // cont.
                        val displayRotation = activity.windowManager.defaultDisplay.rotation
                        val swappedDimensions = areDimensionsSwapped(displayRotation, cameraCharacteristics)
// swap width and height if needed
                        val rotatedPreviewWidth = if (swappedDimensions) previewSize.height else previewSize.width
                        val rotatedPreviewHeight = if (swappedDimensions) previewSize.width else previewSize.height
                        binding.surfaceView.holder.setFixedSize(rotatedPreviewWidth, rotatedPreviewHeight)
                        val previewSurface = binding.surfaceView.holder.surface
                        val imageReader = ImageReader.newInstance(rotatedPreviewWidth, rotatedPreviewHeight,
                        ImageFormat.YUV_420_888, 2)
                        val recordingSurface = imageReader.surface
                        imageReader.setOnImageAvailableListener({
                            // do something
                            val image = it.acquireLatestImage()
                            image?.let {
                                val byteBuffer = image.planes[0].buffer
                                val byteArray = ByteArray(byteBuffer.remaining())
                                byteBuffer.get(byteArray)
                                viewModel.getJson(byteArray = byteArray)
                                image.close()
                            }
                        }, Handler { true })

                        val captureCallback = object : CameraCaptureSession.StateCallback()
                        {
                            override fun onConfigureFailed(session: CameraCaptureSession) {}

                            override fun onConfigured(session: CameraCaptureSession) {
                                // session configured
                                val previewRequestBuilder =   cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                                    .apply {
                                        addTarget(previewSurface)
                                        addTarget(recordingSurface)
                                    }
                                session.setRepeatingRequest(
                                    previewRequestBuilder.build(),
                                    object: CameraCaptureSession.CaptureCallback() {},
                                    Handler { true }
                                )
                            }
                        }

                        cameraDevice.createCaptureSession(mutableListOf(previewSurface, recordingSurface), captureCallback, Handler { true })
                    }
                }
            }
        }, Handler { true })
    }

    private fun areDimensionsSwapped(displayRotation: Int, cameraCharacteristics: CameraCharacteristics): Boolean {
        var swappedDimensions = false
        when (displayRotation) {
            Surface.ROTATION_0, Surface.ROTATION_180 -> {
                if (cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION) == 90 || cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION) == 270) {
                    swappedDimensions = true
                }
            }
            Surface.ROTATION_90, Surface.ROTATION_270 -> {
                if (cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION) == 0 || cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION) == 180) {
                    swappedDimensions = true
                }
            }
            else -> {
                // invalid display rotation
            }
        }
        return swappedDimensions
    }

    override fun onStop() {
        super.onStop()
        cameraDevice.close()
    }
}
