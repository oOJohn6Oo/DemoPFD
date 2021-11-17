package com.john6.example.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BaseBottomSheetDialogFragment<B : ViewBinding?> : BottomSheetDialogFragment() {
    private var _mBinding: B? = null

    val mBinding:B
    get() = _mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mBinding = getViewBindingByReflect(inflater, container)
        return _mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireDialog().window?.let { WindowCompat.setDecorFitsSystemWindows(it, false) }
        requireDialog().setOnShowListener { (view.parent as ViewGroup).setBackgroundColor(Color.TRANSPARENT) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }

    private fun getViewBindingByReflect(inflater: LayoutInflater, container: ViewGroup?): B? {
        try {
            val c: Class<B>? = com.john6.example.base.util.BaseUtil.getGenericClass(javaClass, 0)
            return com.john6.example.base.util.BaseUtil.getViewBinding(c, inflater, container)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}