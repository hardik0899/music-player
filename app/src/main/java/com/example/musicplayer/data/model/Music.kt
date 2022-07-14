package com.example.musicplayer.data.model

import android.net.Uri

data class Music(
    val uri: Uri,
    val displayName: String,
    val id: Long,
    val artist: String,
    val data: String,
    val duration: Int,
    val title: String
)