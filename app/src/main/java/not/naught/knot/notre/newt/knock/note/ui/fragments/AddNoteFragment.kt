package not.naught.knot.notre.newt.knock.note.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import not.naught.knot.notre.newt.knock.note.data.AppDatabase
import not.naught.knot.notre.newt.knock.note.R
import not.naught.knot.notre.newt.knock.note.databinding.FragmentAddNoteBinding
import not.naught.knot.notre.newt.knock.note.data.entities.Note
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

        // Handle menu items
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_add_note, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.menu_save_note -> {
                        // Check if title is empty
                        if (binding.editTextTitle.text.toString().isEmpty()) {
                            binding.editTextTitle.error = resources.getText(R.string.add_note_title_error)
                            return false
                        }

                        // Disable menu item to avoid user to double save
                        menuItem.isEnabled = false

                        // Connect to database
                        val db = Room.databaseBuilder(
                            context!!,
                            AppDatabase::class.java,
                            "app")
                            .allowMainThreadQueries()
                            .build()

                        // Save note to database
                        val note = Note(
                            title = binding.editTextTitle.text.toString(),
                            content = binding.editTextContent.text.toString(),
                            createdAt = Date(System.currentTimeMillis()),
                        )
                        db.noteDao().insertAll(note)

                        // Go back to previous fragment
                        findNavController().popBackStack()
                        true
                    }
                    android.R.id.home -> {
                        discardNoteAlertDialogIfUnsavedChange()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Handle back button
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this) {
                discardNoteAlertDialogIfUnsavedChange()
            }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Show alert dialog if there is any unsaved change
     */
    fun discardNoteAlertDialogIfUnsavedChange() {

        // Check if there is any unsaved change
        if (binding.editTextTitle.text.toString().isEmpty() &&
            binding.editTextContent.text.toString().isEmpty()) {
            findNavController().popBackStack()
            return
        }

        // Show alert dialog
        MaterialAlertDialogBuilder(requireContext())
            .setIcon(android.R.drawable.ic_menu_save)
            .setTitle(resources.getText(R.string.add_note_discard_title))
            .setMessage(resources.getText(R.string.add_note_discard_content))
            .setPositiveButton(resources.getText(R.string.yes)) { _, _ ->
                findNavController().popBackStack()
            }
            .setNegativeButton(resources.getText(R.string.no), null)
            .show()
    }
}