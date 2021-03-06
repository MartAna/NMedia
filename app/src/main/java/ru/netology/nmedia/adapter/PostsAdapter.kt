package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostItemBinding
import ru.netology.nmedia.dto.Post

class PostsAdapter(
    private val onLikedClicked: (Post) -> Unit,
    private val onShareClicked: (Post) -> Unit
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostItemBinding.inflate(
            inflater, parent, false
        )
        return ViewHolder(binding, onLikedClicked, onShareClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    class ViewHolder(
        private val binding: PostItemBinding,
        private val onLikedClicked: (Post) -> Unit,
        private val onShareClicked: (Post) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        init {
            binding.like.setOnClickListener {
                onLikedClicked(post)
            }
            binding.share.setOnClickListener {
                onShareClicked(post)
            }
        }

        fun bind(post: Post) {
            this.post = post
            with(binding) {
                textAuthor.text = post.author
                data.text = post.published
                textPost.text = post.content
                countLikes.text = correctNumberNotation(post.likes)
                countShare.text = correctNumberNotation(post.share)
                like.setImageResource(likeIcon(post.likedByMe))
            }
        }

        private fun likeIcon(likedByMe: Boolean) =
            if (likedByMe) R.drawable.ic_like_24 else R.drawable.ic_baseline_favorite_border_24

        private fun correctNumberNotation(count: Int): String {
            return when (count) {
                in 0..999 -> count.toString()
                in 1_000..9_999 -> if (count % 1_000 in 0..99) (count / 1_000).toString() + "K" else (count / 1_000).toString() + "." + ((count % 1_000) / 100).toString() + "K"
                in 10_000..999_999 -> (count / 1_000).toString() + "K"
                in 1_000_000..999_999_999 -> if (count % 1_000_000 in 0..99_999) "1M" else (count / 1_000_000).toString() + "." + ((count % 1_000_000) / 100_000).toString() + "M"
                else -> "0"
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}
