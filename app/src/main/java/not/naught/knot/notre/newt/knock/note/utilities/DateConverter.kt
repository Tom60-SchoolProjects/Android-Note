package not.naught.knot.notre.newt.knock.note.utilities

import androidx.room.TypeConverter
import java.util.*

/**
 * Needed to implement Date objects in the Room database
 */
class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
