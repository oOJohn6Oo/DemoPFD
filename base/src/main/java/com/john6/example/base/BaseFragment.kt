package com.john6.example.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * On Jetpack navigation
 * Fragments enter/exit represent onCreateView/onDestroyView
 * Thus we should detach all reference to the VIEW on onDestroyView
 */
abstract class BaseFragment<B : ViewBinding?> : Fragment() {
    private var _mBinding: B? = null
    val mBinding: B
    get() = _mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mBinding = getViewBindingByReflect(inflater, container)
        return if (_mBinding == null) null else _mBinding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }

    @JvmOverloads
    fun showLoading(cancelable: Boolean = true) {
        parentActivity.showLoading(cancelable)
    }

    fun dismissLoading() {
        parentActivity.dismissLoading()
    }

    val parentActivity: BaseActivity<*>
        get() = requireActivity() as BaseActivity<*>

    private fun getViewBindingByReflect(inflater: LayoutInflater, container: ViewGroup?): B? {
        return try {
            val c: Class<B>? = com.john6.example.base.util.BaseUtil.getGenericClass(javaClass, 0)
            com.john6.example.base.util.BaseUtil.getViewBinding(c, inflater, container)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}