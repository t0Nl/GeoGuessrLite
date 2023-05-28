package com.example.android.geoguessrlite.database

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.android.geoguessrlite.R
import com.example.android.geoguessrlite.database.locations.GuessLocation
import com.example.android.geoguessrlite.database.locations.LocationDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader

class StartingLocationsPrefiler(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            fillWithStartingNotes(context)
        }
    }

    private fun loadJSONArray(context: Context): JSONArray? {

        val inputStream = context.resources.openRawResource(R.raw.guess_locations_for_db)

        BufferedReader(inputStream.reader()).use {
            return JSONArray(it.readText())
        }
    }

    private suspend fun fillWithStartingNotes(context: Context) {

        val dao = LocationDatabase.getInstance(context).guessLocationDao

        try {
            val locations = loadJSONArray(context)
            if (locations != null) {
                for (i in 0 until locations.length()) {
                    val item = locations.getJSONObject(i)
                    val locationLatitude = item.getDouble("locationLatitude")
                    val locationLongitude = item.getDouble("locationLongitude")
                    val gameCategory = item.getString("gameCategory")

                    val guessLocation = GuessLocation(
                        locationLatitude = locationLatitude,
                        locationLongitude = locationLongitude,
                        gameCategory = GameCategory.values().first { it.label == gameCategory },
                    )

                    dao.insert(guessLocation)
                }
            }
        } catch (e: JSONException) {
            Log.e("JSON", "Error parsing JSOn locations")
        }
    }
}
