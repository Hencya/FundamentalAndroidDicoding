package com.example.githubuserapp

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.model.UserSearchItem
import com.example.githubuserapp.ui.activity.DetailUserActivity
import com.example.githubuserapp.ui.adapter.ListUserAdapter
import com.example.githubuserapp.ui.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var rvUser: RecyclerView

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github User's"

        binding.rvUser.setHasFixedSize(true)

        mainViewModel.itemUser.observe(this) {
            setUserData(it)
        }

//      SetupRecycleView
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.snackBarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    findViewById(R.id.rv_user),
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        searchUsername()
    }

    private fun searchUsername() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    return true
                } else {
                    mainViewModel.searchUser(query)
                    binding.search.clearFocus()
                    Toast.makeText(
                        this@MainActivity,
                        "Mencari Username " + query,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun showSelectedUser(user: UserSearchItem) {
        Toast.makeText(this, "Kamu memilih " + user.login, Toast.LENGTH_SHORT).show()
    }

    private fun setUserData(itemUser: List<UserSearchItem>) {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUser.layoutManager = LinearLayoutManager(this)
        }

        val listUser = ArrayList<UserSearchItem>()

        for (user in itemUser) {
            val dataUser = UserSearchItem(
                user.login,
                user.avatarUrl,
                user.type,
            )
            listUser.add(dataUser)
        }

        val adapter =
            ListUserAdapter(listUser, object : ListUserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: UserSearchItem) {
                    val intentToDetail = Intent(this@MainActivity, DetailUserActivity::class.java)
                    intentToDetail.putExtra("DATA", data)
                    startActivity(intentToDetail)
                    showSelectedUser(data)
                }
            })
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
                rvUser.visibility = View.INVISIBLE
            } else {
                progressBar.visibility = View.GONE
                rvUser.visibility = View.VISIBLE
            }
        }
    }
}