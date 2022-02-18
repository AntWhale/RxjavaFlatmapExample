package com.boo.sample.rxjavaflatmapexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boo.sample.rxjavaflatmapexample.databinding.LayoutPostListItemBinding
import com.boo.sample.rxjavaflatmapexample.models.Post

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {
    val TAG = "RecyclerAdapter"

    private val posts = arrayListOf<Post>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = LayoutPostListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun setPosts(posts: List<Post>){
        this.posts.addAll(posts)
        notifyDataSetChanged()
    }

    fun getPosts(): List<Post>{
        return posts
    }

    fun updatePost(post: Post){
        posts[posts.indexOf(post)] = post
        notifyItemChanged(posts.indexOf(post))
    }

    class MyViewHolder(private val binding: LayoutPostListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(post: Post){
            binding.title.text = post.title

            if(post.comment.isNullOrEmpty()){
                showProgressBar(true)
                binding.numComments.text = ""
            } else {
                showProgressBar(false)
                binding.numComments.text = post.comment.size.toString()
            }
        }
        fun showProgressBar(showProgressBar: Boolean){
            if(showProgressBar){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }

    }
}