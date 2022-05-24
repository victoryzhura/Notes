package com.example.notes.detailnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes.CommonViewModelFactory
import com.example.notes.R
import com.example.notes.database.NotesDatabase
import com.example.notes.databinding.FragmentDetailNoteBinding
import com.example.notes.entity.NoteItem
import com.google.android.material.snackbar.Snackbar
import java.util.*


class DetailNoteFragment : Fragment() {

    private lateinit var binding: FragmentDetailNoteBinding
    private lateinit var viewModelNote: DetailNoteViewModel
    private val args: DetailNoteFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailNoteBinding.inflate(inflater)

        val application = requireActivity().application
        val dataSource = NotesDatabase.getInstance(application).notesDatabase
        val viewModelFactory = CommonViewModelFactory(dataSource)
        viewModelNote =
            ViewModelProvider(
                this, viewModelFactory
            )[DetailNoteViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        if (args.noteItem.title.isNullOrEmpty() && args.noteItem.text.isNullOrEmpty()) {
            addNew()
        } else {
            updateOld()
        }
    }

    private fun addText(callback: (NoteItem) -> Unit) {
        binding.saveButton.setOnClickListener {
            if (binding.editTitleText.text.trim()
                    .isNotEmpty() || binding.editNoteText.text.trim()
                    .isNotEmpty()
            ) {
                callback(NoteItem())
                findNavController().popBackStack()
            } else
                Snackbar.make(
                    binding.root,
                    "Note must not be empty",
                    Snackbar.LENGTH_LONG
                ).show()
        }
    }

    private fun addNew() {
        addText {
            val androidColors = resources.getIntArray(R.array.material_colors)
            val randomAndroidColor = androidColors[Random().nextInt(androidColors.size)]

            viewModelNote.insert(
                NoteItem(
                    color = randomAndroidColor,
                    title = binding.editTitleText.text.toString(),
                    text = binding.editNoteText.text.toString()
                )
            )
        }
    }

    private fun updateOld() {
        val oldItem = args.noteItem

        addText {
            oldItem.title = binding.editTitleText.text.toString()
            oldItem.text = binding.editNoteText.text.toString()
            viewModelNote.update(
                oldItem
            )
        }
        binding.editTitleText.setText(oldItem.title)
        binding.editNoteText.setText(oldItem.text)
    }
}