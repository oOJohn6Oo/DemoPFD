package com.john6.example.scenenavigation.ngapi

import android.os.Bundle
import android.view.View
import com.john6.example.base.BaseFragment
import com.john6.example.scenenavigation.ngapi.databinding.FragmentSampleBinding

class ThreeFragment:BaseFragment<FragmentSampleBinding>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.root.text = "Three"
    }
}