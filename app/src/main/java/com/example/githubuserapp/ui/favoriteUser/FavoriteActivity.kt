package com.example.githubuserapp.ui.favoriteUser

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.data.Result
import com.example.githubuserapp.data.local.entity.UserFavEntity
import com.example.githubuserapp.databinding.ActivityFavoriteBinding
import com.example.githubuserapp.ui.adapter.FavoriteAdapter
import com.example.githubuserapp.ui.detailUser.DetailUserActivity

class FavoriteActivity : AppCompatActivity() {

    private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = getString(R.string.favorite_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        SetupRecycleView
        val factory: FavoriteViewModelFactory =
            FavoriteViewModelFactory.getInstance(this)
        val viewModel: FavoriteViewModel by viewModels {
            factory
        }


        val listFav = ArrayList<UserFavEntity>()
        val adapter = FavoriteAdapter()
//        val adapter = FavoriteAdapter(listFav, object : FavoriteAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: UserFavEntity) {
//                val intentToDetail = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
//                intentToDetail.putExtra("DATA_FAV", data)
//                startActivity(intentToDetail)
//                showSelectedUser(data)
//            }
//        })

        viewModel.getFavorite().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        val favData = result.data
                        adapter.submitList(favData)
                    }
                    is Result.Error -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(
                            this,
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding?.rvUser?.layoutManager = LinearLayoutManager(this)
        binding?.rvUser?.setHasFixedSize(true)
        binding?.rvUser?.adapter = adapter

    }

    private fun showSelectedUser(user: UserFavEntity) {
        Toast.makeText(this, "Kamu memilih " + user.login, Toast.LENGTH_SHORT).show()
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }
}

