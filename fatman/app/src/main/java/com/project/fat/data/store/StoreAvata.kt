package com.project.fat.data.store

data class StoreAvata(
    val id : Long,
    val fatmanImage : String,
    var achieved : Boolean,
    val fatmanName : String,
    var selected : Boolean,
    val cost : Long
)