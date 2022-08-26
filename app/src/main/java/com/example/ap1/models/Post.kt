package com.example.ap1.models

data class Post (
    val text: String="",
    val createdBy:User = User(),
    val createdAt: Long=0L,
    val likedBy: ArrayList<String> = ArrayList())
