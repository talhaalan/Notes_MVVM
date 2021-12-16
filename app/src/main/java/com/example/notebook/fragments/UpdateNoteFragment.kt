package com.example.notebook.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.example.notebook.view.MainActivity
import com.example.notebook.R
import com.example.notebook.databinding.FragmentUpdateNoteBinding
import com.example.notebook.model.Note
import com.example.notebook.viewmodel.NoteViewModel

class UpdateNoteFragment : Fragment() {

    private var _binding : FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private val args : UpdateNoteFragmentArgs by navArgs()
    private lateinit var currentNote : Note

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentUpdateNoteBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel

        currentNote = args.note!!

        binding.etNoteTitleUpdate.setText(currentNote.noteTitle)
        binding.etNoteBodyUpdate.setText(currentNote.noteBody)

        binding.fabUpdate.setOnClickListener {
            val title = binding.etNoteTitleUpdate.text.toString().trim()
            val body = binding.etNoteBodyUpdate.text.toString().trim()

            if (title.isNotEmpty()) {
                val note = Note(currentNote.id,title,body)

                noteViewModel.updateNote(note)

                Snackbar.make(view,getString(R.string.note_updated), Snackbar.LENGTH_SHORT).show()

                view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
            }

        }

    }

    private fun deleteNote() {
        AlertDialog.Builder(activity).apply {
            setTitle(getString(R.string.delete_note))
            setMessage(getString(R.string.delete_note_message))
            setPositiveButton(getString(R.string.delete), DialogInterface.OnClickListener { dialogInterface, i ->
                noteViewModel.deleteNote(currentNote)
                view?.findNavController()?.navigate(R.id.action_updateNoteFragment_to_homeFragment)
            })
            setNegativeButton(getString(R.string.cancel),null)
        }.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.delete_menu -> {
                deleteNote()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.update_menu,menu)

        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}