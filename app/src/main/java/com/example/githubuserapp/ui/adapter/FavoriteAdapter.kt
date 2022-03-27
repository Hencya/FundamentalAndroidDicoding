package com.example.githubuserapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.local.entity.UserFavEntity
import com.example.githubuserapp.databinding.ItemRowUserBinding
import com.example.githubuserapp.ui.detailUser.DetailUserActivity

class FavoriteAdapter() :
    ListAdapter<UserFavEntity, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userFav = getItem(position)
        holder.bind(userFav)

    }

    class MyViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserFavEntity) {
            binding.tvItemName.text = user.login
            binding.tvItemUsername.text = user.type
            Glide.with(binding.root.context)
                .load(user.avatar_url) // URL Gambar
                .placeholder(ColorDrawable(Color.BLACK)) // placeholder
                .error(ColorDrawable(Color.RED)) // while error
                .fallback(ColorDrawable(Color.GRAY)) // while null
                .circleCrop() // Mengubah image menjadi lingkaran
                .into(binding.imgItemAvatar) // imageView mana yang akan diterapkan
            itemView.setOnClickListener {
                val intent = Intent(it.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.DATA_FAV, user)
                it.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UserFavEntity> =
            object : DiffUtil.ItemCallback<UserFavEntity>() {
                override fun areItemsTheSame(
                    oldUser: UserFavEntity,
                    newUser: UserFavEntity
                ): Boolean {
                    return oldUser.login == newUser.login
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldUser: UserFavEntity,
                    newUser: UserFavEntity
                ): Boolean {
                    return oldUser == newUser
                }
            }
    }
}



