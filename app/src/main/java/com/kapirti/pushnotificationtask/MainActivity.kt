package com.kapirti.pushnotificationtask

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kapirti.pushnotificationtask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val CHANNEL_ID="channel_id_example_01"
    private val notificationId=101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        createNotificationChannel()

        binding.btnButton.setOnClickListener {
            sendNotification()
        }
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name="Notification Title"
            val descriptionText="Notification description"
            val importance= NotificationManager.IMPORTANCE_DEFAULT
            val channel:NotificationChannel=NotificationChannel(CHANNEL_ID,name,importance).apply {
                description=descriptionText
            }
            val notificationManager:NotificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun sendNotification(){
        /**This part for what will do when click the notification - bu kisimda gelen fcm'i ne yapacagımızı yazarız. cunku bu kısım olamadan gelene tıklayınca hıcbırsey olmuyor*/
        val intent= Intent(this,MainActivity::class.java).apply{
            flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent:PendingIntent=PendingIntent.getActivity(this,0,intent,0)
        val bitmap=BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_launcher_background)
        val bitmapLargeIcon=BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_launcher_background)


        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Example Title")
            .setContentText("Example description")
            .setLargeIcon(bitmapLargeIcon)
                /**buyuk resım gosterir*/
            /**.setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))*/
                /**buyuk kısımda yazı gosterir. ustekıyle ıkısınden bırı kullanılır*/
            .setStyle(NotificationCompat.BigTextStyle().bigText("bbbbbbbbbbbbbbbsjsjsjjsjsjsjjjsjjss"))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId,builder.build())
        }
    }
}