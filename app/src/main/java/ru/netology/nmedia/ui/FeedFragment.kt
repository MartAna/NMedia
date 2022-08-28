package ru.netology.nmedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FeedFragmentBinding
import ru.netology.nmedia.ui.PostContentFragment.Companion.textArg
import ru.netology.nmedia.ui.PostFragment.Companion.LongArg
import ru.netology.nmedia.viewModel.PostViewModel


class FeedFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FeedFragmentBinding.inflate(layoutInflater, container, false)
        val adapter = PostsAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
        subscribe(adapter)
        createNewPost(binding)
        openPost()
        shareContent()
        videoContent()
        return binding.root
    }

    private fun openPost() {
        viewModel.navigateToPost.observe(viewLifecycleOwner) {
            if (it != null) {
                findNavController().navigate(
                    R.id.action_feedFragment_to_postFragment,
                    Bundle().apply {
                        LongArg = it
                    }
                )
            }
        }
    }

    private fun createNewPost(binding: FeedFragmentBinding) {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_postContentFragment)
        }

        viewModel.currentPost.observe(viewLifecycleOwner) { currentPost ->
            val content = currentPost?.content
            if (content != null) {
                findNavController().navigate(
                    R.id.action_feedFragment_to_postContentFragment,
                    Bundle().apply {
                        textArg = currentPost.content
                    }
                )
            }
        }
    }

    private fun subscribe(adapter: PostsAdapter) {
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }
    }

    private fun shareContent() {
        viewModel.sharePostContent.observe(viewLifecycleOwner) { postContent ->
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

    private fun videoContent() {
        viewModel.urlVideo.observe(viewLifecycleOwner) { url ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
}



