package com.boo.sample.rxjavaflatmapexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boo.sample.rxjavaflatmapexample.databinding.ActivityMainBinding
import com.boo.sample.rxjavaflatmapexample.models.Post
import com.boo.sample.rxjavaflatmapexample.requests.ServiceGenerator
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = "MainActivity"
    }

    val disposables = CompositeDisposable()
    private val madapter = RecyclerAdapter()

    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        getPostsObservable()
            .subscribeOn(Schedulers.io())
            .flatMap {
                getCommentsObservable(it)
            }.observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: Observer<Post>{
                override fun onSubscribe(d: Disposable) {
                    disposables.add(d)
                }

                override fun onNext(post: Post) {
                    updatePost(post)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: ", e)
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: onComplete")
                }
            })
    }

    private fun getPostsObservable(): Observable<Post>{
        return ServiceGenerator
            .requestApi.getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap{
                madapter.setPosts(it)
                Observable.fromIterable(it)
                    .subscribeOn(Schedulers.io())
            }
    }

    private fun getCommentsObservable(post: Post): Observable<Post>{
        return ServiceGenerator.requestApi.getComments(post.id).map {
            val delay = (java.util.Random().nextInt(5)+1)*1000
            Thread.sleep(delay.toLong())
            Log.d(TAG, "sleeping thread: " + Thread.currentThread().name + "for $delay ms")
            post.comment = it
            Log.d(TAG, "getCommentsObservable List<comment>: $it")
            post
        }.subscribeOn(Schedulers.io())
    }

    private fun updatePost(post: Post){
        madapter.updatePost(post)
    }

    private fun initRecyclerView(){

        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = madapter
        }
        /*binding.recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.recyclerview.adapter = adapter*/
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}