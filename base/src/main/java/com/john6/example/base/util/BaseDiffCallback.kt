package com.john6.example.base.util

import androidx.recyclerview.widget.DiffUtil

abstract class BaseDiffCallback<T>(val oldList: List<T>?, val newList: MutableList<T>?) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newList?.size ?: 0
    }
}