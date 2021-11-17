package com.john6.example.scenenavigation.ngapi

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.john6.example.base.BaseFragment
import com.john6.example.base.util.BaseRecyclerViewAdapter
import com.john6.example.base.util.OnItemClickListener
import com.john6.example.scenenavigation.DestinationHolder
import com.john6.example.scenenavigation.bean.DestinationInfo
import com.john6.example.scenenavigation.databinding.ItemMainBinding
import com.john6.example.scenenavigation.ngapi.databinding.NgFragmentMainBinding

class MainFragment:BaseFragment<NgFragmentMainBinding>() {
    private lateinit var mAdapter: BaseRecyclerViewAdapter<ItemMainBinding, DestinationInfo, DestinationHolder>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = BaseRecyclerViewAdapter(DestinationHolder::class.java, object :OnItemClickListener<DestinationInfo>{
            override fun onItemClick(
                data: DestinationInfo,
                view: View?,
                position: Int,
                viewType: Long
            ) {
                Navigation.findNavController(mBinding.root).navigate(data.id)
            }
        })
        mBinding.root.adapter = mAdapter
        initView()
    }

    private fun initView() {
        val graph = Navigation.findNavController(mBinding.root).graph
        val list = mutableListOf<DestinationInfo>()
        graph.forEach {
            val destinationInfo = DestinationInfo(it.id, it.label.toString(), it.label.toString())
            list.add(destinationInfo)
        }
        mAdapter.submitListAndPurge(list)
    }
}