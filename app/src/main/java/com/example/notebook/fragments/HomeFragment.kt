package com.example.notebook.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notebook.view.MainActivity
import com.example.notebook.R
import com.example.notebook.adapter.NoteAdapter
import com.example.notebook.databinding.FragmentHomeBinding
import com.example.notebook.model.Note
import com.example.notebook.viewmodel.NoteViewModel


class HomeFragment : Fragment(R.layout.fragment_home),SearchView.OnQueryTextListener {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter : NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel

        setupRecyclerView()

        binding.fabAddNote.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }

    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter()

        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = noteAdapter
        }

        activity?.let {
            noteViewModel.getAllNote().observe(viewLifecycleOwner, Observer { note ->
                noteAdapter.differ.submitList(note)
                updateUI(note)
            })
        }

    }

    private fun updateUI(note: List<Note>) {
        if (note.isNotEmpty()) {
            binding.recyclerView.visibility = View.VISIBLE
            binding.tvNoNotes.visibility = View.GONE
        } else {
            binding.recyclerView.visibility = View.GONE
            binding.tvNoNotes.visibility = View.VISIBLE
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.home_menu,menu)

        val menuSearch = menu.findItem(R.id.search).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = true
        menuSearch.setOnQueryTextListener(this)


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        if (query != null) {
            searchNotes(query)
        }

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchNotes(newText)
        }

        return true
    }

    private fun searchNotes(query: String?) {
        val searchQuery = "%${query}%"
        noteViewModel.searchNote(searchQuery).observe(this, Observer {
            noteAdapter.differ.submitList(it)
        })
    }

}