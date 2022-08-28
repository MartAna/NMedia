package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.PostContentFragmentBinding
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewModel.PostViewModel


class PostContentFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = PostContentFragmentBinding.inflate(layoutInflater, container, false)

        val content = arguments?.textArg
        content?.let(binding.newContent::setText)

        edit(binding, content)
        cancelEditPost(binding)
        binding.ok.setOnClickListener {
            viewModel.onSaveClicked(binding.newContent.text.toString())
            findNavController().navigateUp()
        }
        return binding.root
    }

    private fun edit(binding: PostContentFragmentBinding, content: String?) {
        if (content.isNullOrBlank()) {
            binding.newContent.requestFocus()
        } else {
            with(binding) {
                newContent.setText(content)
                newContent.requestFocus()
                groupView.visibility = View.VISIBLE
                postEdit.text = content
            }
        }
    }

    private fun cancelEditPost(binding: PostContentFragmentBinding) {
        binding.cancel.setOnClickListener {
            val content = binding.postEdit.text.toString()
            viewModel.onSaveClicked(content)
            findNavController().navigateUp()
        }
    }


    companion object {
        var Bundle.textArg: String? by StringArg
    }
}