package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel


class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = createAdapter(binding)
        binding.postsRecyclerView.adapter = adapter
        subscribe(binding, adapter)
    }

    private fun createAdapter(binding: ActivityMainBinding): PostsAdapter {
        val adapter = PostsAdapter(
            onLikedClicked = { post ->
                viewModel.onLikedClicked(post)
            },
            onShareClicked = { post ->
                viewModel.onShareClicked(post)
            }
        )
        return adapter
    }

    private fun subscribe(binding: ActivityMainBinding, adapter: PostsAdapter) {
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
    }
}



