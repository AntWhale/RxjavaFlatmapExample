package com.boo.sample.rxjavaflatmapexample.models

data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)