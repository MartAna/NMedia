package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post

class InMemoryPostRepository : PostRepository {

    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>

    init {
        val initialPosts = listOf(
            Post(
                id = 1,
                author = "Нетология. Университет интернет-профессий будущего",
                content = " Привет, это новая Нетология! Когда-то Нетология " +
                        "начиналась с интенсивов по онлайн-маркетингу. Затем " +
                        "появились курсы по дизайну, разработке, аналитике " +
                        "и управлению. Мы растём сами и помогаем расти " +
                        "студентам: от новичков до уверенных профессионалов. " +
                        "Но самое важное остаётся с нами: мы верим, что в " +
                        "каждом уже есть сила, которая заставляет хотеть " +
                        "больше, целиться выше, бежать быстрее. Наша миссия " +
                        "- помочь встать на путь роста и начать цепочку " +
                        "перемен -> https://netology.ru",
                published = "08.07.2022",
                likes = 999,
                likedByMe = false,
                share = 1099,
                video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
            ),
            Post(
                id = 2,
                author = "Нетология. Университет интернет-профессий будущего",
                content = " Привет, ты готов?",
                published = "01.08.2022",
                likes = 10,
                likedByMe = true,
                share = 5
            ),
            Post(
                id = 3,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Начинаем!",
                published = "02.08.2022",
                likes = 9999,
                likedByMe = false,
                share = 5099
            ),
            Post(
                id = 4,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Начинаем!",
                published = "02.08.2022",
                likes = 150,
                likedByMe = false,
                share = 13
            ),
            Post(
                id = 5,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Начинаем!",
                published = "02.08.2022",
                likes = 95,
                likedByMe = false,
                share = 19999
            ),
            Post(
                id = 6,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Начинаем!",
                published = "02.08.2022",
                likes = 85236,
                likedByMe = false,
                share = 5
            )
        )
        data = MutableLiveData(initialPosts)
    }

    override fun like(postId: Long) {
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
        if (post.id == PostRepository.newPostId) create(post) else update(post)

    }

    private fun update(post: Post) {
        posts = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private fun create(post: Post) {
        data.value = listOf(
            post.copy(
                id = (posts.size + 1).toLong()
            )
        ) + posts
    }
}