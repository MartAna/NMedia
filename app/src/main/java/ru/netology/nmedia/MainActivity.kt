package ru.netology.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

var countShareText = 1098
var countLikeText = 10

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val post = Post(
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
            likedByMe = false
        )

        with(binding) {
            textAuthor.text = post.author
            data.text = post.published
            textPost.text = post.content
            countLikes.text = countLikeText.toString()
            countShare.text = correctNumberNotation(countShareText)


            like.setOnClickListener {
                post.likedByMe = !post.likedByMe
                like.setImageResource(likeIcon(post.likedByMe))
                if (post.likedByMe) countLikes.text =
                    correctNumberNotation(countLikeText + 1) else countLikes.text =
                    correctNumberNotation(countLikeText)
            }

            share.setOnClickListener {
                countShareText++
                countShare.text = correctNumberNotation(countShareText)
            }

        }

    }

    private fun likeIcon(likedByMe: Boolean) =
        if (likedByMe) R.drawable.ic_like_24 else R.drawable.ic_baseline_favorite_border_24
}

private fun correctNumberNotation(count: Int): String {
    return when (count) {
        in 0..999 -> count.toString()
        in 1_000..9_999 -> if (count % 1_000 in 0..99) "1K" else (count / 1_000).toString() + "." + ((count % 1_000) / 100).toString() + "K"
        in 10_000..999_999 -> (count / 1_000).toString() + "K"
        in 1_000_000..999_999_999 -> if (count % 1_000_000 in 0..99_999) "1M" else (count / 1_000_000).toString() + "." + ((count % 1_000_000) / 100_000).toString() + "M"
        else -> "0"
    }
}


