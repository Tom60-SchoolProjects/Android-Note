package not.naught.knot.notre.newt.knock.note.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import not.naught.knot.notre.newt.knock.note.R
import not.naught.knot.notre.newt.knock.note.data.entities.Note

class NoteAdapter(private val data: List<Note>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView
        val descView: TextView

        init {
            titleView = view.findViewById(R.id.titleView)
            descView = view.findViewById(R.id.descView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleView.text = data[position].title
        holder.descView.text = data[position].content
    }

    override fun getItemCount(): Int = data.size
}