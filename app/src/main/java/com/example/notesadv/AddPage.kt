package com.example.notesadv

import android.app.ComponentCaller
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.notesadv.databinding.ActivityAddPageBinding
import com.example.notesadv.roomDB.NoteDB
import com.example.notesadv.roomDB.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddPage : AppCompatActivity() {
    lateinit var binding: ActivityAddPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityAddPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val title=intent.getStringExtra("title") ?: "Title"
        val content=intent?.getStringExtra("desc") ?: "Content"
        val barTitle=intent?.getStringExtra("titleToolbar")?:"Update Note"
        val Id=intent.getIntExtra("Id",-1)
        Log.d("INTENT",title.toString())
        Log.d("INTENT",content.toString())
        binding.titleText.setText(title)
        binding.content.setText(content)
        if(barTitle=="Update Note"){
            binding.savebutton.text="Update"
            binding.savebutton.setOnClickListener {
                val titleNote=binding.titleText.text.toString()
                val contentNote=binding.content.text.toString()
                val note=NoteEntity(Id = Id,Title=titleNote,content=contentNote)
                lifecycleScope.launch(Dispatchers.IO) {
                    NoteDB.getInstance(this@AddPage).getNoteDAO().update(note)
                }
                onBackPressedDispatcher.onBackPressed()
            }
            binding.button.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    NoteDB.getInstance(this@AddPage).getNoteDAO().delete(NoteEntity(Id=Id,content="", Title = ""))
                }
                onBackPressedDispatcher.onBackPressed()
            }
        }
        else{
            binding.savebutton.text="Create"
            binding.savebutton.setOnClickListener{
                val titleNote=binding.titleText.text.toString()
                val contentNote=binding.content.text.toString()
                val note=NoteEntity(Title=titleNote,content=contentNote)
                lifecycleScope.launch (Dispatchers.IO){
                    NoteDB.getInstance(this@AddPage).getNoteDAO().insert(note)
                }
                onBackPressedDispatcher.onBackPressed()
            }
            binding.button.isVisible=false
        }
        binding.xport.setOnClickListener {
            val intent=Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type="text/plain"
            }.putExtra(Intent.EXTRA_TITLE,title.toString()+".txt")
            startActivityForResult(intent,201)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(binding.savebutton.text=="Create"){
            val titleNote=binding.titleText.text.toString()
            val contentNote=binding.content.text.toString()
            val note=NoteEntity(Title=titleNote,content=contentNote)
            lifecycleScope.launch (Dispatchers.IO){
                NoteDB.getInstance(this@AddPage).getNoteDAO().insert(note)
            }
        }
        Log.d("BACK","BACK PRESSED")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("BACK","DESTROY")
    }

    override fun onPause() {
        super.onPause()
        Log.d("BACK","PAUSE")
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)
        if(requestCode==201 && resultCode==RESULT_OK){
            val uri=data?.data
            if(uri!=null){
                writeFromUri(uri)
            }
        }
    }

    private fun writeFromUri(uri: Uri) {
        val output=contentResolver.openOutputStream(uri)
        output?.use {it->
            it.write(binding.content.text.toString().toByteArray())
            it.flush()
        }
    }
}