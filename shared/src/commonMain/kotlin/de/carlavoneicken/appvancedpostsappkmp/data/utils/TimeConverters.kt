@file:OptIn(ExperimentalTime::class)

package de.carlavoneicken.appvancedpostsappkmp.data.utils

import androidx.room.TypeConverter
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

// annotate the database with '@TypeConverters(TimeConverters::class)'


// Room can only store primitive type ins SQLite (e.g. Integer, Text, Real, Blob) -> Instants need to be converted
class TimeConverters {
    // converts a nullable Instant to a Long? representing millisecond since Unix epoch
    // value?.toEpochMilliseconds -> if value is null the whole expression is null, otherwise it calls
    // toEpochMillisecons() to get a Long millisecond timestamp
    @TypeConverter fun instantToLong(value: Instant?): Long? = value?.toEpochMilliseconds()

    // converts a nullable Long from SQLite back into an Instant?
    // value?.let(Instant::fromEpochMilliseconds) -> safe call: if value is null, returns null
    // if value is not null, let calls Instant.fromEpochMilliseconds(millis) (millis = value)
    @TypeConverter fun longToInstant(value: Long?): Instant? = value?.let { millis ->
        Instant.fromEpochMilliseconds(millis)
    }
}