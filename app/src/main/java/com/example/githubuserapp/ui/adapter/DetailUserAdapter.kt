package com.example.githubuserapp.ui.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.remote.response.UserSocialResponse
import com.example.githubuserapp.databinding.ItemRowUserBinding

class DetailUserAdapter(
    private val listUser:
    ArrayList<UserSocialResponse>
) :
    RecyclerView.Adapter<DetailUserAdapter.ViewHolder>() {
    private lateinit var binding: ItemRowUserBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private var binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(user: UserSocialResponse) {
            Glide.with(binding.root.context)
                .load(user.avatarUrl) // URL Gambar
                .placeholder(ColorDrawable(Color.BLACK)) // placeholder
                .error(ColorDrawable(Color.RED)) // while error
                .fallback(ColorDrawable(Color.GRAY)) // while null
                .circleCrop() // Mengubah image menjadi lingkaran
                .into(binding.imgItemAvatar) // imageView mana yang akan diterapkan
            binding.tvItemName.text = user.login
            binding.tvItemUsername.text = user.type
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(listUser[position])
    }

    override fun getItemCount() = listUser.size
}