package com.example.protej.album_manager.Class



import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    val title: String,
    val year: Int,
    val singer: String,
    val image: String,

) : Parcelable
