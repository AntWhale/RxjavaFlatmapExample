package com.boo.sample.rxjavaflatmapexample.requests

import com.boo.sample.rxjavaflatmapexample.models.Post

import retrofit2.http.GET
import retrofit2.http.Path

interface RequestApi {
    @GET("posts")
    fun getPosts(): io.reactivex.Observable<List<Post>>

    @GET("posts/{id}/comments")
    fun getComments(@Path("id") id:Int): io.reactivex.Observable<List<com.boo.sample.rxjavaflatmapexample.models.Comment>>
}

