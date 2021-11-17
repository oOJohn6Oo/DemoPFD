package com.john6.example.base.util

import android.view.View

interface OnItemClickListener<T> {
    /**
     * For item data not null
     */
    fun onItemClick(data: T, view: View?, position: Int, viewType: Long) {}

    /**
     * For the null data item
     */
    fun onItemClick(view: View?, position: Int, viewType: Long) {}
}