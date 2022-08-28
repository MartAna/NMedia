package ru.netology.nmedia.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.PostFragmentBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.LongArg
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewModel.PostViewModel

class PostFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    lateinit var post: Post

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = PostFragmentBinding.inflate(inflater, container, false)
        val viewHolder = PostsAdapter.ViewHolder(binding.postLayout, viewModel)
        val id = arguments?.LongArg

        viewModel.data.value?.map {
            if (it.id == id) {
                post = it
            }
        }

        viewModel.data.observe(viewLifecycleOwner) {
            it.map { post ->
                if (post.id == id) {
                    viewHolder.bind(post)
                }
            }
        }

        viewModel.deletePost.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        viewModel.currentPost.observe(viewLifecycleOwner) { currentPost ->
            val content = currentPost?.content
            if (content != null) {
                findNavController().navigate(
                    R.id.action_postFragment_to_postContentFragment,
                    Bundle().apply {
                        textArg = currentPost.content
                    }
                )
            }
        }

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


        return binding.root
    }

    companion object {
        var Bundle.LongArg: Long? by LongArg
        var Bundle.textArg: String? by StringArg
    }

}