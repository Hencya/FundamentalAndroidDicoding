package com.example.githubuserapp.ui.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.remote.response.UserSearchItem
import com.example.githubuserapp.databinding.ItemRowUserBinding

class ListUserAdapter(
    private val listUser: ArrayList<UserSearchItem>,
    private val listener: OnItemClickCallback
) :
    RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(listUser[position], listener)
    }

    class ViewHolder(private var binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(user: UserSearchItem, listener: OnItemClickCallback) {
            Glide.with(binding.root.context)
                .load(user.avatarUrl) // URL Gambar
                .placeholder(ColorDrawable(Color.BLACK)) // placeholder
                .error(ColorDrawable(Color.RED)) // while error
                .fallback(ColorDrawable(Color.GRAY)) // while null
                .circleCrop() // Mengubah image menjadi lingkaran
                .into(binding.imgItemAvatar) // imageView mana yang akan diterapkan
            binding.tvItemName.text = user.login
            binding.tvItemUsername.text = user.type
            itemView.setOnClickListener {
                listener.onItemClicked(user)
            }
        }
    }

    override fun getItemCount(): Int = listUser.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserSearchItem)
    }
}

