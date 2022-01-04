package com.example.monascho.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.text.Html
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.monascho.MainActivityLogin
import com.example.monascho.R
import com.example.monascho.ui.detailinformasi.DetailInformasiActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {
    lateinit var title:String
    lateinit var body:String
    lateinit var id_informasi:String
    lateinit var ids:String
    var notificationCount2 = 1

    private var notificationBuilder: NotificationCompat.Builder? = null
    private var notificationChannel: NotificationChannel? = null

//    private var broadcaster: LocalBroadcastManager? = null

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("subs ", "Message :: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.i("SellerFirebaseService ", "Message :: $remoteMessage")

        val data = remoteMessage.data
        body = "${data["body"]}"
        title  = "${data["title"]}"
//        id_transaksi  = "${data["id_transaksi"]}"
        id_informasi  = "${data["id_informasi"]}"
        ids  = "${data["ids"]}"

//        broadcaster = LocalBroadcastManager.getInstance(this)

//        val intent = Intent("MyData")
//        intent.putExtra("id_transaksi", "${data["id_transaksi"]}")
//        broadcaster!!.sendBroadcast(intent)

        //create your own notification to display it on notification tray
        if (remoteMessage.data != null) {
            when (ids) {
                "transaksi" -> {
                    createTransaksi(title, body)
//                    Log.i("Notification", "title :: $title id_transaksi :: $id_transaksi body :: $body channel :: $ids")
                }
                "informasi" -> {
                    createInformasi(title, body, id_informasi)
//                    Log.i("Notification Driver", "title :: $title id_transaksi :: $id_transaksi body :: $body channel :: $ids")
                }
            }
        }
    }

    private fun createTransaksi(title: String, body: String) {
        val intent = Intent(this, MainActivityLogin::class.java)
        intent.putExtra("first", 1)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val bigStyle = NotificationCompat.BigTextStyle()
        bigStyle.setBigContentTitle(title)
        bigStyle.setSummaryText("Vells Make Up")
        bigStyle.bigText(body)
        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_logo)
        val NOTIFICATION_SOUND_URI = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://com.example.makeup/" + R.raw.notif)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Creating Channel
            notificationChannel = NotificationChannel(
                    "CH_ID_NOTIF_KONSUMEN",
                    "CH_NAME_NOTIF_SURAT",
                    NotificationManager.IMPORTANCE_HIGH
            )
            notificationBuilder = NotificationCompat.Builder(this, notificationChannel.toString())
                    .setSmallIcon(R.drawable.ic_logo)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentTitle(Html.fromHtml(title))
                    .setContentText(Html.fromHtml(body))
                    .setAutoCancel(true)
                    .setSound(NOTIFICATION_SOUND_URI)
                    .setChannelId("CH_ID_NOTIF_KONSUMEN")
                    .setLargeIcon(largeIcon)
                    .setStyle(bigStyle)
                    .setContentIntent(pendingIntent)
        } else {
            notificationBuilder = NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_logo)
                    .setContentTitle(Html.fromHtml(title))
                    .setContentText(Html.fromHtml(body))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setLargeIcon(largeIcon)
                    .setStyle(bigStyle)
                    .setSound(NOTIFICATION_SOUND_URI)
                    .setContentIntent(pendingIntent)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (NOTIFICATION_SOUND_URI != null) {
                notificationBuilder!!.setDefaults(Notification.DEFAULT_VIBRATE)
                val audioAttributes = AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build()
                notificationChannel!!.setSound(NOTIFICATION_SOUND_URI, audioAttributes)
                notificationManager.createNotificationChannel(notificationChannel!!)
            }
        }
        notificationManager.notify(notificationCount2++, notificationBuilder!!.build())
    }


    private fun createInformasi(title: String, body: String, id: String) {
        val intent = Intent(this, DetailInformasiActivity::class.java)
        intent.putExtra("id", id)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val bigStyle = NotificationCompat.BigTextStyle()
        bigStyle.setBigContentTitle(title)
        bigStyle.setSummaryText("Vells Make Up")
        bigStyle.bigText(body)
        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_logo)
        val NOTIFICATION_SOUND_URI = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://com.example.makeup/" + R.raw.notif)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Creating Channel
            notificationChannel = NotificationChannel(
                    "CH_ID_NOTIF_KONSUMEN",
                    "CH_NAME_NOTIF_SURAT",
                    NotificationManager.IMPORTANCE_HIGH
            )
            notificationBuilder = NotificationCompat.Builder(this, notificationChannel.toString())
                    .setSmallIcon(R.drawable.ic_logo)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentTitle(Html.fromHtml(title))
                    .setContentText(Html.fromHtml(body))
                    .setAutoCancel(true)
                    .setSound(NOTIFICATION_SOUND_URI)
                    .setChannelId("CH_ID_NOTIF_KONSUMEN")
                    .setLargeIcon(largeIcon)
                    .setStyle(bigStyle)
                    .setContentIntent(pendingIntent)
        } else {
            notificationBuilder = NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_logo)
                    .setContentTitle(Html.fromHtml(title))
                    .setContentText(Html.fromHtml(body))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setLargeIcon(largeIcon)
                    .setStyle(bigStyle)
                    .setSound(NOTIFICATION_SOUND_URI)
                    .setContentIntent(pendingIntent)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (NOTIFICATION_SOUND_URI != null) {
                notificationBuilder!!.setDefaults(Notification.DEFAULT_VIBRATE)
                val audioAttributes = AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build()
                notificationChannel!!.setSound(NOTIFICATION_SOUND_URI, audioAttributes)
                notificationManager.createNotificationChannel(notificationChannel!!)
            }
        }
        notificationManager.notify(notificationCount2++, notificationBuilder!!.build())
    }
}