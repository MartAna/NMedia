package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.dto.Post

class SQLiteRepository(
    private val dao: PostDao
) : PostRepository {

    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }

    override val data = MutableLiveData(dao.getAll())


    override fun like(postId: Long) {
        dao.likeById(postId)
        posts = posts.map { post ->
            if (post.id == postId) {
                if (post.likedByMe) post.copy(
                    likedByMe = !post.likedByMe,
                    likes = post.likes - 1
                ) else post.copy(likedByMe = !post.likedByMe, likes = post.likes + 1)
            } else post
        }
    }

    override fun share(postId: Long) {
        dao.shareById(postId)
        posts = posts.map { post ->
            if (post.id == postId) post.copy(share = post.share + 1) else post
        }
    }

    override fun delete(postId: Long) {
        posts = posts.filterNot { post ->
            post.id == postId
        }
    }

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        posts = if (id == 0L) {
            listOf(saved) + posts
        } else {
            posts.map {
                if (it.id != id) it else saved
            }
        }
    }

}