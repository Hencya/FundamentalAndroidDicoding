package com.example.githubuserapp

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var rvUser: RecyclerView
    private val list = ArrayList<User>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Github User's"

        rvUser = findViewById(R.id.rv_user)
        rvUser.setHasFixedSize(true)

        list.addAll(listUser)
        showRecyclerList()
    }

    private fun showSelectedHero(hero: User) {
        Toast.makeText(this, "Kamu memilih " + hero.name, Toast.LENGTH_SHORT).show()
    }

    private val listUser: ArrayList<User>
        get() {
            val dataUsername = resources.getStringArray(R.array.data_username)
            val dataName = resources.getStringArray(R.array.data_name)
            val dataAvatar = resources.getStringArray(R.array.data_avatar)
            val dataFollower = resources.getStringArray(R.array.data_follower)
            val dataFollowing = resources.getStringArray(R.array.data_following)
            val dataCompany = resources.getStringArray(R.array.data_company)
            val dataLocation = resources.getStringArray(R.array.data_location)
            val dataRepository = resources.getStringArray(R.array.data_repository)
            val listUser = ArrayList<User>()
            for (i in dataName.indices) {
                val user = User(
                    dataUsername[i],
                    dataName[i],
                    dataAvatar[i],
                    dataFollower[i],
                    dataFollowing[i],
                    dataCompany[i],
                    dataLocation[i],
                    dataRepository[i]
                )
                listUser.add(user)
            }
            return listUser
        }

    private fun showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            rvUser.layoutManager = LinearLayoutManager(this)
        }
        val listHeroAdapter = ListUserAdapter(list)
        rvUser.adapter = listHeroAdapter

        listHeroAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intentToDetail = Intent(this@MainActivity, DetailUserActivity::class.java)
                intentToDetail.putExtra("DATA", data)
                startActivity(intentToDetail)
                showSelectedHero(data)
            }
        })
    }
}