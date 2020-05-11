package omeronce.android.emptyproject.utils.files

import android.content.Context
import android.os.Environment
import androidx.core.content.FileProvider
import omeronce.android.emptyproject.BuildConfig
import java.io.File

class FileUtils {

    companion object{
        //parent dir path
        val rootDirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath+"/anyvision"
        //sub dir path
        val fullDirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath+"/anyvision/folder"

        fun getDownloadFolderUri(context: Context,file: File) = FileProvider.getUriForFile(context,BuildConfig.fileProviderPath,file)
    }
}