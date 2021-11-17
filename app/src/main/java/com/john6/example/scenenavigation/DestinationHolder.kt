package com.john6.example.scenenavigation

import com.john6.example.base.util.BaseRecyclerViewAdapter
import com.john6.example.scenenavigation.bean.DestinationInfo
import com.john6.example.scenenavigation.databinding.ItemMainBinding

class DestinationHolder(mBinding: ItemMainBinding) : BaseRecyclerViewAdapter.BaseViewHolder<ItemMainBinding, DestinationInfo>(mBinding) {
    override fun binding(data: DestinationInfo?, selectedIndex: Int) {
        data?.let {
            mBinding.root.text = it.desc
        }
    }
}