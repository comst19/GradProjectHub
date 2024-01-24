package com.project.fat.data.dto

import android.net.Uri
import retrofit2.http.Url
import java.net.URI

typealias Fatman = ArrayList<FatmanElement>

data class FatmanElement (
    val name: String,
    val fatmanImageUrl: String,
    val fatmanId: Long,
    val fatmanCost: Long
)
