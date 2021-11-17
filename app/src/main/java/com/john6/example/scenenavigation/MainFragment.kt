package com.john6.example.scenenavigation

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.dynamicfeatures.DynamicInstallManager
import androidx.navigation.dynamicfeatures.fragment.DynamicFragmentNavigator
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.john6.example.base.BaseFragment
import com.john6.example.base.util.BaseRecyclerViewAdapter
import com.john6.example.base.util.OnItemClickListener
import com.john6.example.scenenavigation.bean.DestinationInfo
import com.john6.example.scenenavigation.databinding.FragmentMainBinding
import com.john6.example.scenenavigation.databinding.ItemMainBinding

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MainFragment : BaseFragment<FragmentMainBinding>() {
    private lateinit var mAdapter: BaseRecyclerViewAdapter<ItemMainBinding, DestinationInfo, DestinationHolder>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = BaseRecyclerViewAdapter(
            DestinationHolder::class.java,
            object : OnItemClickListener<DestinationInfo> {
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
        initData()
    }

    private fun initData() {
        val graph = Navigation.findNavController(mBinding.root).graph
        val list = mutableListOf<DestinationInfo>()

        val manager = SplitInstallManagerFactory.create(requireContext())
        manager.installedModules.forEach {
            println("it->$it")
        }

        graph.forEach { dest ->
            var shouldExclude = dest.arguments.containsKey("exclude");
            if (shouldExclude) {
                val exclude = dest.arguments["exclude"]?.let {
                    if (it.defaultValue == null) null
                    else it.defaultValue as Boolean
                }
                // true => true; false,null => false
                shouldExclude = exclude != null
            }
            if (!shouldExclude) {
                val destinationInfo =
                    DestinationInfo(dest.id, dest.label.toString(), dest.label.toString())
                list.add(destinationInfo)
            }
        }
        mAdapter.submitListAndPurge(list)

    }
}