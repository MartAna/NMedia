package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post

class InMemoryPostRepository : PostRepository {
    override val data = MutableLiveData(
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
            share = 1099
        )
    )

    override fun like() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        data.value =
            if (currentPost.likedByMe) currentPost.copy(
                likedByMe = !currentPost.likedByMe,
                likes = currentPost.likes - 1
            ) else currentPost.copy(
                likedByMe = !currentPost.likedByMe,
                likes = currentPost.likes + 1
            )
    }

    override fun share() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        data.value = currentPost.copy(share = currentPost.share + 1)
    }
}