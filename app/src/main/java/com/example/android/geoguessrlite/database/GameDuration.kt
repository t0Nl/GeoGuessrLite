package com.example.android.geoguessrlite.database

enum class GameDuration(val durationSeconds: Int, val label: String) {
        ONE_MINUTE(60, "1 Minute"),
        THREE_MINUTES(180, "3 Minutes"),
        FIVE_MINUTES(300, "5 Minutes"),
}