package omeronce.android.emptyproject.scannovate.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import omeronce.android.emptyproject.R
import omeronce.android.emptyproject.databinding.FragmentMainBinding
import omeronce.android.emptyproject.model.Result
import omeronce.android.emptyproject.scannovate.camera.CameraActivity
import omeronce.android.emptyproject.view.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment: BaseFragment() {

    companion object {

        @JvmStatic
        val TAG = "MainFragment"

        @JvmStatic
        fun newInstance(): MainFragment {
            val bundle = Bundle()
            val fragment = MainFragment().apply { arguments = bundle }
            return fragment
        }
    }

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initObservers()
    }

    private fun initBinding() {
        binding.clicks = ClickListeners()
    }

    private fun initObservers() {
        viewModel.json.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Result.Success -> showResponse(it.value)
                is Result.Error -> showError(it.exception.message ?: getString(R.string.error_message_unknown))
            }
        })
    }

    private fun showResponse(json: String) {
        binding.textResponse.text = json
    }

    private fun showError(msg: String) {
        binding.textResponse.text = msg
    }

    private fun showCameraActivity() {
        activity?.let { CameraActivity.startActivity(it) }
    }

    inner class ClickListeners {
        fun onTakePictureClick(view: View) {
            showCameraActivity()
        }
    }
}