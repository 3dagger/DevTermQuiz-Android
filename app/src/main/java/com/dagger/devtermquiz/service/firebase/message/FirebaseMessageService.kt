package com.dagger.devtermquiz.service.firebase.message

import android.app.NotificationManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.orhanobut.logger.Logger

class FirebaseMessageService : FirebaseMessagingService() {

//    private val notificationClass: NotificationClass by inject()
//    private val notificationManager: NotificationManager by inject()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if(remoteMessage.data.isNotEmpty()) {
            Logger.d("remoteMessage.data :: ${remoteMessage.data}")
        }

        remoteMessage.notification?.let {
            Logger.d("Messageing Notification Body : ${it.body}")
        }
    }

}