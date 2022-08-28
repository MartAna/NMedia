package ru.netology.nmedia.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.AppActivityBinding
import ru.netology.nmedia.ui.PostContentFragment.Companion.textArg

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = AppActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }

            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text?.isNotBlank() != true) {
                return@let
            }
            intent.removeExtra(Intent.EXTRA_TEXT)
            findNavController(R.id.nav_host_fragment).navigate(
                R.id.action_feedFragment_to_postContentFragment,
                Bundle().apply {
                    textArg = text
                }
            )
        }

       /* if (supportFragmentManager.findFragmentByTag(FeedFragment.TAG) == null) {
            supportFragmentManager.commit {
                add(R.id.nav_host_fragment, FeedFragment(), FeedFragment.TAG)
            }
        }*/
    }
}