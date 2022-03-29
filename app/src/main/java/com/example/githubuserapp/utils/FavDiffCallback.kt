package com.example.githubuserapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuserapp.data.local.entity.UserFavEntity

class FavDiffCallback(
    private val mOldFavList: List<UserFavEntity>,
    private val mNewFavList: List<UserFavEntity>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return mOldFavList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavList[oldItemPosition].login == mNewFavList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldFavList[oldItemPosition]
        val newEmployee = mNewFavList[newItemPosition]
        return oldEmployee.type == newEmployee.type && oldEmployee.avatar_url == newEmployee.avatar_url
    }
}