package com.bronski.android.antortask.users.ui.screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bronski.android.antortask.core.data.UserEntity
import com.bronski.android.antortask.databinding.ItemUsersBinding

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    private val usersListDiffer = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUsersBinding.inflate(inflater, parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = usersListDiffer.currentList[position]
        holder.bind(itemUser = user)
    }

    override fun getItemCount(): Int = usersListDiffer.currentList.size

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserEntity>() {
            override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class UsersViewHolder(
        val binding: ItemUsersBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemUser: UserEntity) = with(binding) {
            nameTextView.text = itemUser.name
            emailTextView.text = itemUser.email
            phoneTextView.text = itemUser.phone
        }
    }
}