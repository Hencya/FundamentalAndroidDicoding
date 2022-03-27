package com.example.githubuserapp.ui.favoriteUser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.ActivityFavoriteBinding
import com.example.githubuserapp.ui.adapter.FavoriteAdapter

class FavoriteActivity : AppCompatActivity() {

    private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = getString(R.string.favorite_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        SetupRecycleView
        val viewModel = obtainViewModel(this@FavoriteActivity)
        viewModel.getFavorite().observe(this) {
            if (it != null) {
                adapter.setListFav(it)
            }
        }

        adapter = FavoriteAdapter()
        binding?.rvUser?.layoutManager = LinearLayoutManager(this)
        binding?.rvUser?.setHasFixedSize(true)
        binding?.rvUser?.adapter = adapter

    }


    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
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

//class FavoriteActivity : AppCompatActivity() {
//
//    private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
//    private val binding get() = _activityFavoriteBinding
////    private lateinit var adapter: FavoriteAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
//        setContentView(binding?.root)
//
//        supportActionBar?.title = getString(R.string.favorite_user)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
////        SetupRecycleView
//        val factory: FavoriteViewModelFactory =
//            FavoriteViewModelFactory.getInstance(this)
//        val viewModel: FavoriteViewModel by viewModels {
//            factory
//        }
//
//        val adapter = FavoriteAdapter()
//        viewModel.getFavorite().observe(this) { result ->
//            if (result != null) {
//                when (result) {
//                    is Result.Loading -> {
//                        binding?.progressBar?.visibility = View.VISIBLE
//                        Toast.makeText(
//                            this,
//                            "Loading",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                    is Result.Success -> {
//                        binding?.progressBar?.visibility = View.GONE
//                        val favData = result.data
//                        adapter.submitList(favData)
//                    }
//                    is Result.Error -> {
//                        binding?.progressBar?.visibility = View.GONE
//                        Toast.makeText(
//                            this,
//                            "Terjadi kesalahan" + result.error,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//            }
//        }
//
//
//        binding?.rvUser?.layoutManager = LinearLayoutManager(this)
//        binding?.rvUser?.setHasFixedSize(true)
//        binding?.rvUser?.adapter = adapter
//
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        finish()
//        return true
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        _activityFavoriteBinding = null
//    }
//}

