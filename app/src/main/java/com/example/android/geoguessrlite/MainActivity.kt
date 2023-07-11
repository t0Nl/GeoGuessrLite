package com.example.android.geoguessrlite

import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.android.geoguessrlite.util.createNotificationChannel
import com.example.android.geoguessrlite.util.scheduleDailyNotification

class MainActivity : AppCompatActivity() {
    private var hasNotificationPermissionGranted = false

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            hasNotificationPermissionGranted = isGranted
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= 33) {
            notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            hasNotificationPermissionGranted = true
        }

        if (hasNotificationPermissionGranted) {
            createNotificationChannel(this)
            scheduleDailyNotification(this)
        }
    }
}
