package not.naught.knot.notre.newt.knock.note.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Note(
    @ColumnInfo val title: String?,
    @ColumnInfo val content: String?,
    @ColumnInfo(name = "created_at") val createdAt: Date?
) {
    @PrimaryKey(autoGenerate = true) var uid: Int = 0
}