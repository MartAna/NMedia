package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.FilePostRepository
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel(
    application: Application
) : AndroidViewModel(application),
    PostInteractionListener {

    private val repository: PostRepository = FilePostRepository(application)

    val data get() = repository.data

    val sharePostContent = SingleLiveEvent<String>()
    val navigateToPostContentScreenEvent = SingleLiveEvent<Unit>()
    val currentPost = MutableLiveData<Post?>(null)
    val urlVideo = SingleLiveEvent<String>()

    override fun onLikedClicked(post: Post) =
        repository.like(post.id)

    override fun onShareClicked(post: Post) {
        repository.share(post.id)
        sharePostContent.value = post.content
    }

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

    override fun onVideoClicked(post: Post) {
        val url = post.video ?: return
        urlVideo.value = url
        print(url)
    }

    fun onAddClicked() {
        navigateToPostContentScreenEvent.call()
    }
}