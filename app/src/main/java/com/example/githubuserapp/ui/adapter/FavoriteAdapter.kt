package com.example.githubuserapp.ui.adapter

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.local.entity.UserFavEntity
import com.example.githubuserapp.databinding.ItemRowUserBinding
import com.example.githubuserapp.ui.detailUser.DetailUserActivity
import com.example.githubuserapp.utils.FavDiffCallback

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavViewHolder>() {

    private val listFav = ArrayList<UserFavEntity>()

    fun setListFav(listFav: List<UserFavEntity>) {
        val diffCallback = FavDiffCallback(this.listFav, listFav)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.listFav.clear()
        this.listFav.addAll(listFav)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.onBind(listFav[position])
    }

    inner class FavViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(user: UserFavEntity) {
            Glide.with(binding.root.context)
                .load(user.avatar_url) // URL Gambar
                .placeholder(ColorDrawable(Color.BLACK)) // placeholder
                .error(ColorDrawable(Color.RED)) // while error
                .fallback(ColorDrawable(Color.GRAY)) // while null
                .circleCrop() // Mengubah image menjadi lingkaran
                .into(binding.imgItemAvatar) // imageView mana yang akan diterapkan
            binding.tvItemName.text = user.login
            binding.tvItemUsername.text = user.type
            itemView.setOnClickListener {
                val intent = Intent(it.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_FAVORITE, user)
                it.context.startActivity(intent)
            }
        }
    }


    override fun getItemCount(): Int = listFav.size

}

