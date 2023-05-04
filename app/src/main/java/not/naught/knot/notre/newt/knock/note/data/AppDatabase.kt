package not.naught.knot.notre.newt.knock.note.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import not.naught.knot.notre.newt.knock.note.data.entities.Note
import not.naught.knot.notre.newt.knock.note.data.repositories.NoteDao
import not.naught.knot.notre.newt.knock.note.utilities.DateConverter

@Database(entities = [Note::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}