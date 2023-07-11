package com.example.android.geoguessrlite.util

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.Calendar

const val DAILY_REMINDER_CHANNEL_ID = "daily_reminder_channel_id"
const val DAILY_REMINDER_CHANNEL_NAME = "Daily Reminder"
const val DAILY_REMINDER_CHANNEL_DESCRIPTION = "Channel for Geoguessr Lite daily reminder"

private const val NOTIFICATION_REQUEST_CODE = 123
private const val NOTIFICATION_MINUTE_OF_DAY = 0

private val NOTIFICATION_HOUR_OF_DAY = listOf(8, 10, 12, 14, 16, 18, 20)

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            DAILY_REMINDER_CHANNEL_ID,
            DAILY_REMINDER_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = DAILY_REMINDER_CHANNEL_DESCRIPTION
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun scheduleDailyNotification(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val notificationIntent = Intent(context, NotificationReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        NOTIFICATION_REQUEST_CODE,
        notificationIntent,
        PendingIntent.FLAG_IMMUTABLE
    )

    val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(
            Calendar.HOUR_OF_DAY,
            NOTIFICATION_HOUR_OF_DAY.random()
        ) // Set the hour of the day for the notification
        set(
            Calendar.MINUTE,
            NOTIFICATION_MINUTE_OF_DAY
        ) // Set the minute of the hour for the notification
    }

    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
    )
}
