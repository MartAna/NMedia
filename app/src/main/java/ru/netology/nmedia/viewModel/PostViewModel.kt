package ru.netology.nmedia.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.dto.Post

class PostViewModel : ViewModel(), PostInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository()

    val data get() = repository.data

    val currentPost = MutableLiveData<Post?>(null)

    override fun onLikedClicked(post: Post) =
        repository.like(post.id)

    override fun onShareClicked(post: Post) =
        repository.share(post.id)

    override fun onDeleteClicked(post: Post) =
        repository.delete(post.id)

    override fun onSaveClicked(content: String) {
        if (content.isBlank()) return
        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.newPostId,
            author = "Нетология",
            content = content,
            published = "Today"
        )
        repository.save(post)
        currentPost.value = null
    }

    override fun onEditClicked(post: Post) {
        currentPost.value = post
    }
}