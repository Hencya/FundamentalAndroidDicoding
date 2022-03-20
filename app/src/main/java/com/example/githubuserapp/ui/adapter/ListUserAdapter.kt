package com.example.githubuserapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.databinding.ItemRowUserBinding
import com.example.githubuserapp.model.UserSearchItem

class ListUserAdapter(
    private val listUser: ArrayList<UserSearchItem>,
    private val listener: OnItemClickCallback
) :
    RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {

    }

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
                .circleCrop() // Mengubah image menjadi lingkaran
                .into(binding.imgItemAvatar) // imageView mana yang akan diterapkan
            binding.tvItemName.text = user.login
            binding.tvItemUsername.text = user.type
            binding.imgItemAvatar.setOnClickListener {
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
