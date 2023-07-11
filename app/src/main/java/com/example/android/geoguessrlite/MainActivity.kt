package com.example.android.geoguessrlite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.geoguessrlite.util.createNotificationChannel
import com.example.android.geoguessrlite.util.scheduleDailyNotification

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel(this)
        scheduleDailyNotification(this)
        setContentView(R.layout.activity_main)
    }
}
