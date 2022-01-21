package com.example.a26_rafa_roomdb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a26_rafa_roomdb.room.Constant
import com.example.a26_rafa_roomdb.room.Movie
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val db by lazy { Notedb(this) }
    lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupListener()
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            MovieAdapter.setData(db.MovieDao().getNotes())
            withContext(Dispatchers.Main) {
                MovieAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupView (){
        supportActionBar!!.apply {
            title = "NOTE HARIAN RAFA ALDHINO"
        }
    }

    private fun setupListener(){
        button_create.setOnClickListener {
            intentEdit(Constant.TYPE_CREATE, 0)
        }
    }

    private fun setupRecyclerView () {

        noteAdapter = MovieAdapter(
            arrayListOf(),
            object : MovieAdapter.OnAdapterListener {
                override fun onClick(note: Movie) {
                    intentEdit(Constant.TYPE_READ, note.id)
                }

                override fun onUpdate(note: Movie) {
                    intentEdit(Constant.TYPE_UPDATE, note.id)
                }

                override fun onDelete(note: Movie) {
                    deleteAlert(note)
                }

            })

        list_note.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = noteAdapter
        }

    }

    private fun intentEdit(intent_type: Int, note_id: Int) {
        startActivity(
            Intent(this, EditActivity::class.java)
                .putExtra("intent_type", intent_type)
                .putExtra("note_id", note_id)
        )

    }

    private fun deleteAlert(note: Movie){
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Konfirmasi Hapus")
            setMessage("Yakin hapus ${note.title}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.noteDao().deleteNote(note)
                    dialogInterface.dismiss()
                    loadData()
                }
            }
        }

        dialog.show()
    }
}