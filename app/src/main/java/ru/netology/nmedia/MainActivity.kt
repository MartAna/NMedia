package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel

var countShareText = 1098
var countLikeText = 10

class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.data.observe(this) { post ->

            with(binding) {
                textAuthor.text = post.author
                data.text = post.published
                textPost.text = post.content
                countLikes.text = countLikes(post.likedByMe)
                countShare.text = correctNumberNotation(countShareText)
                like.setImageResource(likeIcon(post.likedByMe))


                like.setOnClickListener {
                    viewModel.onLikedClicked()
                }
                share.setOnClickListener {
                    countShareText++
                    countShare.text = correctNumberNotation(countShareText)
                }
            }

        }

    }

    private fun likeIcon(likedByMe: Boolean) =
        if (likedByMe) R.drawable.ic_like_24 else R.drawable.ic_baseline_favorite_border_24

    private fun countLikes(likedByMe: Boolean) =
        if (likedByMe)correctNumberNotation(countLikeText + 1) else correctNumberNotation(countLikeText)
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


