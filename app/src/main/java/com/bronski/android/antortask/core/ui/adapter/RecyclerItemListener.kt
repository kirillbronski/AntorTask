package com.bronski.android.antortask.core.ui.adapter

import com.bronski.android.antortask.core.data.UserEntity

interface RecyclerItemListener {
    fun deleteUser(itemUser: UserEntity)
}