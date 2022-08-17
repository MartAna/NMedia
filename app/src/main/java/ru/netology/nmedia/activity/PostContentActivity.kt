package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.NewPostContentBinding


class PostContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = NewPostContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent ?: return

        val content = intent.getStringExtra(Intent.EXTRA_TEXT)
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
        binding.ok.setOnClickListener {
            val resultIntent = Intent()
            val text = binding.newContent.text
            if (text.isNullOrBlank()) {
                setResult(RESULT_CANCELED, resultIntent)
            } else {
                val textNew = text.toString()
                resultIntent.putExtra(RESULT_KEY, textNew)
                setResult(RESULT_OK, resultIntent)
            }
            finish()
        }
    }

    class ResultContract : ActivityResultContract<String?, String?>() {

        override fun createIntent(context: Context, input: String?): Intent {
            val intent = Intent(context, PostContentActivity::class.java)
            intent.putExtra(Intent.EXTRA_TEXT, input)
            print(input)
            return intent
        }

        override fun parseResult(resultCode: Int, intent: Intent?) =
            if (resultCode == RESULT_OK) {
                intent?.getStringExtra(RESULT_KEY)
            } else null
    }

    private companion object {
        private const val RESULT_KEY = "postNewContent"
    }
}