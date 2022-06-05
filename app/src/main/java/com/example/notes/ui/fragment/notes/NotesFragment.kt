package com.example.notes.ui.fragment.notes


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.App.Companion.mAuth
import com.example.notes.ui.utility.CommonViewModelFactory
import com.example.notes.R
import com.example.notes.database.NotesDatabase
import com.example.notes.databinding.FragmentNotesBinding
import com.example.notes.database.entity.NoteItem
import com.example.notes.ui.RegisterActivity
import com.example.notes.ui.utility.hideKeyboard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

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
        fbUpdate()
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
                when (it.itemId) {
                    R.id.delete -> viewModelNotes.delete(item.id)
                    R.id.pin -> {
                        item.isPined = !item.isPined
                        viewModelNotes.update(item)
                    }
                }
                true
            }

            popup.show()
        })

        binding.notesRecycler.adapter = adapter
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.notesRecycler.layoutManager = layoutManager

        viewModelNotes.listOfNotes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }

        binding.addButton.setOnClickListener {
            try {
                findNavController().navigate(
                    NotesFragmentDirections.actionNotesFragmentToDetailNoteFragment(
                        NoteItem()
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        binding.searchButton.setOnClickListener {
            binding.isSearch = true
        }
        binding.cancelButton.setOnClickListener {
            binding.isSearch = false
            binding.searchEditText.setText("")
            requireActivity().hideKeyboard()
        }
        binding.deleteAllText.setOnClickListener {
            binding.searchEditText.setText("")
        }

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                binding.notesRecycler.scrollToPosition(0)
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                binding.notesRecycler.scrollToPosition(0)
            }
        })

        binding.searchEditText.addTextChangedListener { editText ->
            val list = mutableListOf<NoteItem>()
            viewModelNotes.listOfNotes.value?.forEach {
                if (it.text?.contains(editText.toString()) == true)
                    list.add(it)
            }

            adapter.submitList(list)
            binding.notesRecycler.scrollToPosition(0)
        }

        binding.logoutButton.setOnClickListener {
            val intent = Intent(requireContext(), RegisterActivity::class.java)
            mAuth?.signOut()
            viewModelNotes.clear()
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun fbUpdate() {
        val fstore = FirebaseFirestore.getInstance()
        val mAuth = FirebaseAuth.getInstance()
        fstore.collection("users").document(mAuth.uid!!).collection("notes").get()
            .addOnCompleteListener {
                it.addOnSuccessListener {
                    it.documents.forEach {
                        val noteItem =
                            it.toObject(NoteItem::class.java)
                        val roomList = viewModelNotes.listOfNotes.value
                        val note = roomList?.find {
                            it.time == noteItem?.time
                        }
                        if (note == null && noteItem != null) {
                            viewModelNotes.insert(noteItem)
                        }
                    }
                }
            }
    }
}


//1. Создать функцию, которая берет список из рума, сравнивает с файдербейзвовським списком и того чего не хвататет добавляем в фаербейз
//2. Если другое - синзронизируем, если меньше - добаавить чего нет, если больше - удалить