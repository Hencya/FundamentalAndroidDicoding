package com.example.githubuserapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.githubuserapp.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        supportActionBar?.title = "Detail User"

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<User>("DATA")

        Glide.with(this)
            .load(data?.avatar) // URL Gambar
            .circleCrop() // Mengubah image menjadi lingkaran
            .into(binding.imgUserAvatar) // imageView mana yang akan diterapkan
        binding.apply {
            tvUserName.text = data?.name
            tvUserUsername.text = StringBuilder("@").append(data?.username)
            tvUserFollower.text = StringBuilder("Follower : ").append(data?.follower)
            tvUserFollowing.text = StringBuilder("Following : ").append(data?.following)
            tvUserRepository.text = StringBuilder("Sum Repository : ").append(data?.repository)
            tvUserLocation.text = StringBuilder("Location : ").append(data?.location)
            tvUserCompany.text = StringBuilder("Company : ").append(data?.company)
        }
    }
}