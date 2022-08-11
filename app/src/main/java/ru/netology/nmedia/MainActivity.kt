package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard
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
        cancelEditPost(binding)
    }

    private fun createNewPost(binding: ActivityMainBinding) {
        binding.save.setOnClickListener {
            with(binding.contentEditText) {
                val content = text.toString()
                viewModel.onSaveClicked(content)
                clearFocus()
                hideKeyboard()
            }
            viewModel.currentPost.observe(this) { currentPost ->
                with(binding) {
                    val content = currentPost?.content
                    contentEditText.setText(content)
                    groupView.visibility = View.GONE
                    if (content != null){
                        contentEditText.requestFocus()
                        groupView.visibility = View.VISIBLE
                        postEdit.text = content
                    }
                }
            }
        }
    }

    private fun cancelEditPost(binding: ActivityMainBinding) {
        binding.cancel.setOnClickListener {
            with(binding){
                val content = postEdit.text.toString()
                viewModel.onSaveClicked(content)
                contentEditText.clearFocus()
                contentEditText.hideKeyboard()
            }
        }
    }

    private fun subscribe(adapter: PostsAdapter) {
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
    }
}



