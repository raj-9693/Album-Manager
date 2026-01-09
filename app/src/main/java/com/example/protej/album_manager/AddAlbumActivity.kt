package com.example.protej.album_manager

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.util.Log.v
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.protej.album_manager.Class.Album

class AddAlbumActivity : AppCompatActivity() {
    lateinit var imgAlbum2: ImageView
    lateinit var btnAddPhoto: Button
    var selectedImageUri: Uri? = null
    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            uri?.let {

                contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                selectedImageUri = it
                imgAlbum2.setImageURI(it)
            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_album)

        val title = findViewById<EditText>(R.id.edtTitle)
        val singer = findViewById<EditText>(R.id.edtSinger)
        val year = findViewById<EditText>(R.id.edtYear)

        imgAlbum2 = findViewById(R.id.imgAlbum2)

        btnAddPhoto = findViewById(R.id.btnAddPhoto)

        btnAddPhoto.setOnClickListener {
            imagePickerLauncher.launch(arrayOf("image/*"))
        }




        findViewById<Button>(R.id.btnSave).setOnClickListener {

                    val titleText = title.text.toString().trim()
                    val singerText = singer.text.toString().trim()
                    val yearText = year.text.toString().trim()



                    if (titleText.isEmpty() || singerText.isEmpty() || yearText.isEmpty()) {
                        Toast.makeText(
                            this,
                            "Please fill all fields",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }


                    val yearInt = yearText.toIntOrNull()
                    if (yearInt == null) {
                        Toast.makeText(
                            this,
                            "Please enter valid year",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }


                    val album = Album(
                        titleText,
                        yearInt,
                        singerText,
                        selectedImageUri?.toString() ?: ""

                    )

                    val intent = Intent()
                    intent.putExtra("album", album)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}