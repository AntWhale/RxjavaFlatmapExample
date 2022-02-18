package com.boo.sample.rxjavaflatmapexample.models

data class Post(val userId: Int, val id: Int, val title: String, var comment: List<Comment>)