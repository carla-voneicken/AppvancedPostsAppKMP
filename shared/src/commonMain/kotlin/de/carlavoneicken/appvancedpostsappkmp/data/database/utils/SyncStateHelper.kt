package de.carlavoneicken.appvancedpostsappkmp.data.database.utils

import androidx.room.TypeConverter

enum class SyncState { SYNCED, CREATING, UPDATING, DELETING, FAILED }

class Converters {
    @TypeConverter
    fun toState(name: String?): SyncState? = name?.let { SyncState.valueOf(it) }
    @TypeConverter fun fromState(state: SyncState?): String? = state?.name
}