package com.bronski.android.antortask.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bronski.android.antortask.core.data.UserEntity
import com.bronski.android.antortask.databinding.ItemMangeUsersBinding

class UsersAdapter(
    private val listener: RecyclerItemListener? = null,
    private val showDeleteIcon: Boolean = false,
) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    private val usersListDiffer = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitList(usersList: List<UserEntity>) = usersListDiffer.submitList(usersList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMangeUsersBinding.inflate(inflater, parent, false)
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
        val binding: ItemMangeUsersBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemUser: UserEntity) = with(binding) {
            nameTextView.text = itemUser.name
            emailTextView.text = itemUser.email
            phoneTextView.text = itemUser.phone
            deleteImageView.isVisible = showDeleteIcon
            deleteImageView.apply {
                setOnClickListener {
                    listener?.deleteUser(itemUser = itemUser)
                }
            }
        }
    }
}