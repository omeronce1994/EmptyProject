package omeronce.android.emptyproject

import android.Manifest

class Const {
    companion object {
        const val PREFS_PREFIX = "emptyproject.key."
        val PREFS_TOKEN_KEY = PREFS_PREFIX + "tokenkey"

        //TODO change url
        const val BASE_URL = "https://btrustdev.scanovate.com/"
        const val FLOW_ID = "2"
        const val CAMERA_PERMISSION_CODE = 0
        const val CAMERA_PERMISSION = Manifest.permission.CAMERA
    }
}