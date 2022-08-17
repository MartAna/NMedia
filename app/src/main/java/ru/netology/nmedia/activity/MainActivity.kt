package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel


class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = PostsAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
        subscribe(adapter)
        createNewPost(binding)
        shareContent()

    }

    private fun createNewPost(binding: ActivityMainBinding) {
        binding.fab.setOnClickListener {
            viewModel.onAddClicked()
        }

        val postContentActivityLauncher = registerForActivityResult(
            PostContentActivity.ResultContract()
        ) { postContent ->
            postContent?.let(viewModel::onSaveClicked)
        }
        viewModel.navigateToPostContentScreenEvent.observe(this) {
            with(postContentActivityLauncher) { launch(null) }

        }
        viewModel.currentPost.observe(this) { currentPost ->
            val content = currentPost?.content
            if (content != null)
                postContentActivityLauncher.launch(content)
        }
    }

    private fun subscribe(adapter: PostsAdapter) {
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
    }

    private fun shareContent() {
        viewModel.sharePostContent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent =
                Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }
    }
}



