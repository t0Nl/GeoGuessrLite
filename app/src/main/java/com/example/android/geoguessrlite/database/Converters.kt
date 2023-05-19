package com.example.android.geoguessrlite.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class Converters {
    @TypeConverter
    fun fromGameDuration(value: Int?): GameDuration? {
        return value?.let {
            when (it) {
                GameDuration.ONE_MINUTE.durationSeconds -> GameDuration.ONE_MINUTE
                GameDuration.THREE_MINUTES.durationSeconds -> GameDuration.THREE_MINUTES
                else -> GameDuration.FIVE_MINUTES
            }
        }
    }

    @TypeConverter
    fun toGameDuration(gameDuration: GameDuration?): Int? {
        return gameDuration?.durationSeconds
    }

    @TypeConverter
    fun fromGameCategory(value: String?): GameCategory? {
        return value?.let {
            when (it) {
                GameCategory.WORLD.label -> GameCategory.WORLD
                GameCategory.NORTH_AMERICA.label -> GameCategory.NORTH_AMERICA
                GameCategory.SOUTH_AMERICA.label -> GameCategory.SOUTH_AMERICA
                GameCategory.EUROPE.label -> GameCategory.EUROPE
                GameCategory.ASIA.label -> GameCategory.ASIA
                GameCategory.AFRICA.label -> GameCategory.AFRICA
                else -> GameCategory.AUSTRALIA_PACIFIC
            }
        }
    }

    @TypeConverter
    fun toGameCategory(gameCategory: GameCategory?): String? {
        return gameCategory?.label
    }

    @TypeConverter
    fun fromDouble(value: String?): Double? {
        return value?.toDouble()
    }

    @TypeConverter
    fun toDouble(coordinate: Double?): String? {
        return coordinate?.toString()
    }
}