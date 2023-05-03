package not.naught.knot.notre.newt.knock.note

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import not.naught.knot.notre.newt.knock.note.entities.Note
import not.naught.knot.notre.newt.knock.note.repositories.NoteDao
import not.naught.knot.notre.newt.knock.note.utilities.DateConverter

@Database(entities = [Note::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}