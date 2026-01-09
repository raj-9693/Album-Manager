package com.example.protej.album_manager.Class



import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.protej.album_manager.R
import java.util.Locale

class AlbumAdapter(
    albums: ArrayList<Album>
) : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    private val originalList = ArrayList<Album>()
    private val displayList = ArrayList<Album>()

    var onDataChanged: ((Int) -> Unit)? = null

    init {
        originalList.addAll(albums)
        displayList.addAll(albums)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.imgAlbum)
        val title: TextView = view.findViewById(R.id.txtTitle)
        val singer: TextView = view.findViewById(R.id.txtSinger)
        val year: TextView = view.findViewById(R.id.txtYear)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_album, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = displayList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = displayList[position]

        holder.title.text = album.title
        holder.singer.text = album.singer
        holder.year.text = album.year.toString()

        if (album.image.isNotEmpty()) {
            holder.img.setImageURI(Uri.parse(album.image))
        } else {
            holder.img.setImageResource(R.drawable.ic_launcher_background)
        }
    }


    fun filter(query: String) {
        displayList.clear()

        if (query.isEmpty()) {
            displayList.addAll(originalList)
        } else {
            val q = query.lowercase()
            for (item in originalList) {
                if (
                    item.title.lowercase().contains(q) ||
                    item.singer.lowercase().contains(q)
                ) {
                    displayList.add(item)
                }
            }
        }

        notifyDataSetChanged()
        onDataChanged?.invoke(displayList.size)
    }


    fun addAlbum(album: Album) {
        originalList.add(album)
        displayList.add(album)
        notifyDataSetChanged()
        onDataChanged?.invoke(displayList.size)
    }
}
