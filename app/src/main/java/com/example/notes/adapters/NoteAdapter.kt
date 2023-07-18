package com.example.notes.adapters

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.notes.databinding.ItemContainerNoteBinding
import com.example.notes.entities.Note

class NoteAdapter(val listener: (note: Note) -> Unit) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var list: ArrayList<Note> = arrayListOf()

    fun addList(list: List<Note>) {
        this.list = list as ArrayList<Note>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding =
            ItemContainerNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBind(list[position])
        holder.binding.layoutNote.setOnClickListener {
            listener(list[position])
        }
    }

    class NoteViewHolder(val binding: ItemContainerNoteBinding) : ViewHolder(binding.root) {
        fun onBind(note: Note) {
            binding.textTitle.text = note.noteText
            binding.textSubtitle.text = note.subtitle
            binding.textDateTime.text = note.dateTime

            binding.textTitle.text = note.title
            if (note.subtitle.trim().isEmpty() == true) {
                binding.textSubtitle.visibility = View.GONE
            } else {
                binding.textSubtitle.text = note.subtitle
            }
            binding.textDateTime.text = note.dateTime


            val gradientDrawable = binding.layoutNote.background as GradientDrawable
            if (note.color != null) {
                gradientDrawable.setColor(Color.parseColor(note.color))
            } else {
                gradientDrawable.setColor(Color.parseColor("#333333"))
            }

            if (note.imagePath != null) {
                binding.imageNote.setImageBitmap(BitmapFactory.decodeFile(note.imagePath))
                binding.imageNote.visibility = View.VISIBLE
            } else {
                binding.imageNote.visibility = View.GONE
            }
        }


    }


}