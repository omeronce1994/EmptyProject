package omeronce.android.emptyproject.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import omeronce.android.emptyproject.R

class NotificationUtils {

    companion object{

        val NOTIF_ID = 1 //we gonna use only one notification in this demo for simplicity
        val CHANNEL_ID = "EmptyProjectDefault"
        val CHANNEL_NAME = "EmptyProjectDefaultName"

        /**
         * Create notification channel for android 8+
         */
        fun createNotificationChannel(context: Context, channelId: String = CHANNEL_ID, name: String = CHANNEL_NAME, importance: Int = NotificationManager.IMPORTANCE_DEFAULT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val serviceChannel = NotificationChannel(
                    channelId,
                    name,
                    importance
                )

                val manager = context.getSystemService(NotificationManager::class.java)
                manager.createNotificationChannel(serviceChannel)
            }
        }

        /**
         * Build Notification object
         */
        fun getNotification(context: Context, channelId: String = CHANNEL_ID,
                            title:String = context.getString(R.string.app_name), text:String = "SomeText",
                            iconRes:Int = R.mipmap.ic_launcher) = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(iconRes)
            .setOngoing(false)
            .build()

        fun showNotification(context: Context,notification: Notification, notifId: Int = NOTIF_ID){
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(notifId, notification)
        }
    }


}