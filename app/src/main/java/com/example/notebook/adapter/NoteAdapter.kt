package com.example.notebook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notebook.databinding.NoteLayoutViewholderBinding
import com.example.notebook.fragments.HomeFragmentDirections
import com.example.notebook.model.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {



    class NoteViewHolder(val itemBinding: NoteLayoutViewholderBinding) : RecyclerView.ViewHolder(itemBinding.root)


    private val differCallback =
        object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }
        }
    val differ = AsyncListDiffer(this,differCallback)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder((NoteLayoutViewholderBinding.inflate(LayoutInflater.from(parent.context),parent,false)))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]

        holder.itemBinding.tvNoteTitle.text = currentNote.noteTitle
        holder.itemBinding.tvNoteBody.text = currentNote.noteBody



        holder.itemView.setOnClickListener { mView ->
            val direction = HomeFragmentDirections.actionHomeFragmentToUpdateNoteFragment(currentNote)

            mView.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}