package com.example.protej.album_manager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log.v
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.protej.album_manager.Class.Album
import com.example.protej.album_manager.Class.AlbumAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    lateinit var adapter: AlbumAdapter
    lateinit var search: EditText
    private val albumList = ArrayList<Album>()

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                val album = result.data?.getParcelableExtra<Album>("album")
                album?.let {

                    adapter.addAlbum(it)
                }
            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
         search = findViewById<EditText>(R.id.edtSearch)
        val fab = findViewById<FloatingActionButton>(R.id.fabAdd)
        val layoutEmpty = findViewById<LinearLayout>(R.id.layoutEmpty)

        loadDummyAlbums()


        adapter = AlbumAdapter(albumList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        adapter.onDataChanged = { size ->
            if (size == 0) {
                recyclerView.visibility = View.GONE
                layoutEmpty.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                layoutEmpty.visibility = View.GONE
            }
        }

        adapter.onDataChanged?.invoke(albumList.size)


        search.addTextChangedListener {
            adapter.filter(it.toString().trim())
        }

        fab.setOnClickListener {
            launcher.launch(Intent(this, AddAlbumActivity::class.java))
        }


    }
            private fun loadDummyAlbums() {
                albumList.add(
                    Album(
                        "Best of Arijit",
                        2021,
                        "Arijit Singh",
                        "android.resource://${packageName}/${R.drawable.img_2}"
                    )
                )
                albumList.add(
                    Album(
                        "Romantic Hits",
                        2020,
                        "Shreya Ghoshal",
                        "android.resource://${packageName}/${R.drawable.img}"
                    )
                )
                albumList.add(
                    Album(
                        "Classic Gold",
                        2019,
                        "Kishore Kumar",
                        "android.resource://${packageName}/${R.drawable.img_1}"
                    )
                )


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


}