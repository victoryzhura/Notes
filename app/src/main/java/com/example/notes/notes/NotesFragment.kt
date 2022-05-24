package com.example.notes.notes


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.CommonViewModelFactory
import com.example.notes.R
import com.example.notes.database.NotesDatabase
import com.example.notes.databinding.FragmentNotesBinding
import com.example.notes.entity.NoteItem

class NotesFragment : Fragment() {
    private lateinit var binding: FragmentNotesBinding
    private lateinit var viewModelNotes: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBinding.inflate(inflater)

        val application = requireActivity().application
        val dataSource = NotesDatabase.getInstance(application).notesDatabase
        val viewModelFactory = CommonViewModelFactory(dataSource)
        viewModelNotes =
            ViewModelProvider(
                this, viewModelFactory
            )[NotesViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NotesAdapter(callback = {
            findNavController().navigate(
                NotesFragmentDirections.actionNotesFragmentToDetailNoteFragment(
                    it
                )
            )
        }, longCallback = { item, itemView ->
            val popup = PopupMenu(requireContext(), itemView)
            popup.menuInflater
                .inflate(R.menu.popup_menu, popup.menu)

            popup.setOnMenuItemClickListener {
                if (it.itemId == R.id.delete)
                    viewModelNotes.delete(item.id)
                Toast.makeText(
                    requireContext(),
                    "You Clicked : " + item.title,
                    Toast.LENGTH_SHORT
                ).show()
                true
            }

            popup.show()
        })

        binding.notesRecycler.adapter = adapter
        binding.notesRecycler.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        viewModelNotes.listOfNotes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }

        binding.addButton.setOnClickListener {
            findNavController().navigate(
                NotesFragmentDirections.actionNotesFragmentToDetailNoteFragment(
                    NoteItem()
                )
            )
        }


    }
}