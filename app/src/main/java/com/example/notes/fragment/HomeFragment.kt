package com.example.notes.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notes.App
import com.example.notes.R
import com.example.notes.adapters.NoteAdapter
import com.example.notes.databinding.FragmentHomeBinding
import com.example.notes.entities.Note
import com.example.notes.viewmodel.NoteRepository
import com.example.notes.viewmodel.NoteViewModel
import com.example.notes.viewmodel.NoteViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: NoteAdapter
    private var listNote = arrayListOf<Note>()
    private val noteViewModel: NoteViewModel by viewModels {
        val repo = NoteRepository(App.db.noteDao())
        NoteViewModelFactory(repo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listNote.addAll(App.db.noteDao().getAllNotes())
        adapter = NoteAdapter(this::onClick)
        adapter.addList(App.db.noteDao().getAllNotes())

        binding.noteRecyclerView.adapter = adapter

        binding.inputSearch.addTextChangedListener {
            noteViewModel.setSearchQuery(it.toString())
        }

        noteViewModel.searchResult.observe(viewLifecycleOwner) {
            adapter.addList(it)
        }

        binding.imageAddNoteMain.setOnClickListener {
            findNavController().navigate(R.id.action_fragment1_to_fragment2)
        }
    }

    private fun onClick(note: Note) {
        val bundle = Bundle()
        bundle.putSerializable("key", note)
        findNavController().navigate(R.id.addNoteFragment, bundle)
    }


}