package com.example.notesadv

import android.app.ComponentCaller
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesadv.databinding.ActivityFilereaderBinding

class filereader : AppCompatActivity() {
    lateinit var binding: ActivityFilereaderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityFilereaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.fileselect.setOnClickListener {
            val intent= Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type="text/plain"
            }
            startActivityForResult(intent,101)

        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)
        if(requestCode==101 && resultCode==RESULT_OK){
            val uri=data?.data
            if(uri!=null){
                val text=readTextFromUri(uri)
                binding.contentfil.setText(text.toString())
            }
        }
    }

    private fun readTextFromUri(uri: Uri) : String{
        val input=contentResolver.openInputStream(uri)
        return input?.bufferedReader().use { it?.readText()?:" " }
    }

}