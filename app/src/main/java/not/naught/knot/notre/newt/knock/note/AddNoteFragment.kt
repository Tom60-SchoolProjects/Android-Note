package not.naught.knot.notre.newt.knock.note

import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import not.naught.knot.notre.newt.knock.note.databinding.FragmentAddNoteBinding
import not.naught.knot.notre.newt.knock.note.entities.Note
import java.util.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_add_note, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.menu_save_note -> {

                        menuItem.isEnabled = false

                        val db = Room.databaseBuilder(
                            context!!,
                            AppDatabase::class.java,
                            "app")
                            .allowMainThreadQueries()
                            .build()

                        val noteDao = db.noteDao()

                        val note = Note(
                            title = binding.editTextTitle.text.toString(),
                            content = binding.editTextContent.text.toString(),
                            createdAt = Date(System.currentTimeMillis()),
                        )

                        noteDao.insertAll(note)

                        findNavController().popBackStack()

                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Discard Note?")
                    .setMessage("Are you sure you want to discard this note?")
                    .setPositiveButton("Yes") { _, _ ->
                        findNavController().popBackStack()
                    }
                    .setNegativeButton("No", null)
                    .show()
            }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}