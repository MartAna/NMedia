package ru.netology.nmedia.adapter

import ru.netology.nmedia.dto.Post

interface PostInteractionListener {
    fun onLikedClicked(post: Post)
    fun onShareClicked(post: Post)
    fun onDeleteClicked(post: Post)
    fun onSaveClicked(content: String)
    fun onEditClicked(post: Post)
}