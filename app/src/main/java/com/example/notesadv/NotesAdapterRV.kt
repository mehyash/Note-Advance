package com.example.notesadv

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.notesadv.roomDB.NoteDB
import com.example.notesadv.roomDB.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotesAdapterRV(private val Notelist: List<NoteEntity>,val lifecycleOwner: LifecycleOwner):RecyclerView.Adapter<NotesAdapterRV.itemViewHolder>() {
    class itemViewHolder(view: View):RecyclerView.ViewHolder(view){
        val title: TextView =view.findViewById(R.id.noteTitle)
        val desc: TextView =view.findViewById(R.id.desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.notescard,parent,false)
        return itemViewHolder(view)
    }
    override fun getItemCount(): Int {
        return Notelist.size
    }

    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {
        holder.title.text=Notelist[position].Title
        holder.desc.text=Notelist[position].content
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, AddPage::class.java)
            intent.putExtra("title", Notelist[position].Title)
            intent.putExtra("desc", Notelist[position].content)
            intent.putExtra("Id",Notelist[position].Id)
            holder.itemView.context.startActivity(intent)
        }
    }
}
