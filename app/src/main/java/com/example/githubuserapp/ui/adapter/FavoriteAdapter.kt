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
import com.example.githubuserapp.utils.FavDiffCallback

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {

    private val listFav = ArrayList<UserFavEntity>()

    fun setListFav(listFav: List<UserFavEntity>) {
        val diffCallback = FavDiffCallback(this.listFav, listFav)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFav.clear()
        this.listFav.addAll(listFav)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listFav[position])
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

    override fun getItemCount(): Int {
        return listFav.size
    }
}



