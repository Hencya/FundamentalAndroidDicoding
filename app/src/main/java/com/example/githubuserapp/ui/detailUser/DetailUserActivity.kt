package com.example.githubuserapp.ui.detailUser

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.data.local.entity.UserFavEntity
import com.example.githubuserapp.data.remote.response.UserSearchItem
import com.example.githubuserapp.databinding.ActivityDetailUserBinding
import com.example.githubuserapp.ui.adapter.SectionsPagerAdapter
import com.example.githubuserapp.ui.favoriteUser.FavoriteActivity
import com.example.githubuserapp.ui.setting.SettingActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private val detailViewModel by viewModels<DetailUserViewModel>()

    private lateinit var binding: ActivityDetailUserBinding

    private var isFavorited = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        supportActionBar?.title = getString(R.string.detail_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }


        lateinit var user: UserSearchItem
        lateinit var userFav: UserFavEntity
        lateinit var username: String
        lateinit var avatarURL: String
        lateinit var userType: String

        if (intent.getParcelableExtra<UserSearchItem>(DATA) != null) {
            user = intent.getParcelableExtra<UserSearchItem>(DATA) as UserSearchItem
            username = user.login
            avatarURL = user.avatarUrl
            userType = user.type
        } else {
            userFav = intent.getParcelableExtra(DATA_FAV)!!
            avatarURL = userFav.avatar_url
            username = userFav.login
            userType = userFav.type
        }

        detailViewModel.setUserDetail(username)
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
                        .placeholder(ColorDrawable(Color.BLACK)) // placeholder
                        .error(ColorDrawable(Color.RED)) // while error
                        .fallback(ColorDrawable(Color.GRAY)) // while null
                        .circleCrop() // Mengubah image menjadi lingkaran
                        .into(binding.imgUserAvatar) // imageView mana yang akan diterapkan
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

        isFavorited = false
        checkIsFavorited(username)

        val bundle = Bundle()
        bundle.putString(USERNAME, username)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager)
        { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        btnFavoriteAction(userType, username, avatarURL)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_setting -> {
                val settingActivityIntent = Intent(this, SettingActivity::class.java)
                startActivity(settingActivityIntent)
                return true
            }
            R.id.menu_fav -> {
                val favActivityIntent = Intent(this, FavoriteActivity::class.java)
                startActivity(favActivityIntent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkIsFavorited(username: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = detailViewModel.checkIsFavorited(username)
            withContext(Dispatchers.Main) {
                if (result != null) {
                    if (result > 0) {
                        isFavorited = true
                        binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                    } else {
                        binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border_24)
                        isFavorited = false
                    }
                }
            }
        }
    }

    private fun btnFavoriteAction(userType: String, username: String, avatarURL: String) {
        binding.btnFavorite.setOnClickListener {
            if (!isFavorited) {
                isFavorited = true
                detailViewModel.insertToFavorite(username, avatarURL, userType)
                binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                showAlertDialog(username)
            }
        }
    }

    private fun showAlertDialog(username: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(R.string.delete)
            setMessage(R.string.message_delete)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                isFavorited = false
                binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border_24)
                detailViewModel.removeFromFavorite(username)
                showToast(getString(R.string.deleted))
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val USERNAME = "user"
        const val DATA_FAV = "favorite"
        const val DATA = "data_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following
        )
    }


}