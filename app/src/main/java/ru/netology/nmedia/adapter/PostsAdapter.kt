package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostItemBinding
import ru.netology.nmedia.dto.Post

class  PostsAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostItemBinding.inflate(
            inflater, parent, false
        )
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    class ViewHolder(
        private val binding: PostItemBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.more).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onDeleteClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.like.setOnClickListener {
                listener.onLikedClicked(post)
            }
            binding.share.setOnClickListener {
                listener.onShareClicked(post)
            }
        }

        fun bind(post: Post) {
            this.post = post
            with(binding) {
                textAuthor.text = post.author
                data.text = post.published
                textPost.text = post.content
                like.text = correctNumberNotation(post.likes)
                like.isChecked = post.likedByMe
                share.text = correctNumberNotation(post.share)
                more.setOnClickListener { popupMenu.show() }
            }
        }

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
