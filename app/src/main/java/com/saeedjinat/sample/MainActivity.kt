package com.saeedjinat.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saeedjinat.sample.databinding.ActivityMainBinding
import com.saeedjinat.sample.feature.posts.PostsActivity
import com.saeedjinat.sample.feature.posts.api.PostRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.hotPosts.setOnClickListener {
            startActivity(
                PostsActivity.intentFor(
                    this@MainActivity,
                    PostRepository.TopicType.HOT
                )
            )
        }

        binding.newPosts.setOnClickListener {
            startActivity(
                PostsActivity.intentFor(
                    this@MainActivity,
                    PostRepository.TopicType.NEW
                )
            )
        }

        binding.topPosts.setOnClickListener {
            startActivity(
                PostsActivity.intentFor(
                    this@MainActivity,
                    PostRepository.TopicType.TOP
                )
            )
        }
    }
}