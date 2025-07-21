package com.example.notesadv

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Adapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Carousel
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesadv.databinding.ActivityMainBinding
import com.example.notesadv.roomDB.NoteDB
import com.example.notesadv.roomDB.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var notelist: List<NoteEntity>
    lateinit var adapter: NotesAdapterRV
    //var count =-1;
    //var sharedpref=this.getSharedPreferences("LOG STATS",MODE_PRIVATE)
    //var editor=sharedpref.edit()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.addbutton.setOnClickListener {
            startActivity(Intent(this, AddPage::class.java).putExtra("titleToolbar", "Create Note"))

        }
    }

    override fun onStart() {
        super.onStart()
        /*count=sharedpref.getInt("Count",-1)
        if(count==-1){
            editor.putInt("Count",0)
        }
        count=sharedpref.getInt("Count",-1)
        count=count+1
        editor.putString(count.toString(), LocalDateTime.now().toString())
        editor.commit()*/
        Log.d("datetine",LocalDateTime.now().toString())
        lifecycleScope.launch(Dispatchers.IO) {
            notelist = NoteDB.getInstance(this@MainActivity).getNoteDAO().getQuery()
            Log.d("ROOMDATA",notelist.toString())
            withContext(Dispatchers.Main) {
                if (!notelist.isNullOrEmpty()) {
                    binding.rv.layoutManager =
                        LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                    binding.rv.adapter = NotesAdapterRV(notelist,this@MainActivity)
                }
                else {
                    Toast.makeText(this@MainActivity, "No Notes Found....", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}