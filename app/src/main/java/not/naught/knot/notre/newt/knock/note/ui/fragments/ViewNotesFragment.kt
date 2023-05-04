package not.naught.knot.notre.newt.knock.note.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import not.naught.knot.notre.newt.knock.note.data.AppDatabase
import not.naught.knot.notre.newt.knock.note.databinding.FragmentViewNotesBinding


/**
 */
class ViewNotesFragment : Fragment() {
    private var _binding: FragmentViewNotesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentViewNotesBinding.inflate(inflater, container, false)

        // Get notes from DB
        val db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "app").allowMainThreadQueries().build()
        val noteDao = db.noteDao()
        val notes = noteDao.getAll()

        // Bind them to the recycler view
        binding.innerView.adapter = NoteAdapter(notes)
        binding.innerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}