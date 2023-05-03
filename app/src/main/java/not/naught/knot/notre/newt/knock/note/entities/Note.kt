package not.naught.knot.notre.newt.knock.note.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Note(
    @PrimaryKey val uid: Int,
    @ColumnInfo val title: String?,
    @ColumnInfo val content: String?,
    @ColumnInfo(name = "created_at") val createdAt: Date?
)