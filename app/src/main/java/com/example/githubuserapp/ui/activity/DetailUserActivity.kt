package com.example.githubuserapp.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.ActivityDetailUserBinding
import com.example.githubuserapp.model.UserSearchItem
import com.example.githubuserapp.ui.adapter.SectionsPagerAdapter
import com.example.githubuserapp.ui.viewmodel.DetailUserViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    companion object {
        const val USERNAME = "user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following
        )
    }

    private val detailViewModel by viewModels<DetailUserViewModel>()

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        supportActionBar?.title = "Detail User"

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val data =
            intent.getParcelableExtra<UserSearchItem>("DATA") as UserSearchItem

        val bundle = Bundle()
        bundle.putString(USERNAME, data.login)

        detailViewModel.setUserDetail(data.login)
        detailViewModel.userDetail.observe(this) {
            if (it != null) {
                binding.apply {
                    tvUserName.text = it.name
                    tvUserUsername.text = StringBuilder("@").append(it.login)
                    tvUserFollower.text =
                        StringBuilder("Followers : ").append(it.followers.toString())
                    tvUserFollowing.text =
                        StringBuilder("Following : ").append(it.following.toString())
                    tvUserRepository.text =
                        StringBuilder("Repository : ").append(it.publicRepos.toString())
                    tvUserCompany.text = StringBuilder("Company : ").append(it.company) ?: "-"
                    tvUserLocation.text = StringBuilder("Location : ").append(it.location) ?: "-"
                    Glide.with(imgUserAvatar)
                        .load(it.avatarUrl) // URL Gambar
                        .circleCrop() // Mengubah image menjadi lingkaran
                        .into(imgUserAvatar)// imageView mana yang akan diterapkan
                }
            }
        }


        detailViewModel.snackBarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    findViewById(R.id.view_pager),
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager)
        { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                detailProgressBar.visibility = View.VISIBLE
                viewPager.visibility = View.INVISIBLE
                tabs.visibility = View.INVISIBLE
            } else {
                detailProgressBar.visibility = View.INVISIBLE
                viewPager.visibility = View.VISIBLE
                tabs.visibility = View.VISIBLE
            }
        }
    }
}