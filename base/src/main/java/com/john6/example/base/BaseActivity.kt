package com.john6.example.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.john6.example.base.util.BaseUtil.getGenericClass
import com.john6.example.base.util.BaseUtil.toast

/**
 * 基础
 *
 * @author chenhengfei@agora.io
 */
abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {
    lateinit var mBinding: B
    private var mLoadingDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempBinding = getViewBindingByReflect(layoutInflater)
        if (tempBinding == null) {
            toast(this, "Inflate Error")
            finish()
        } else {
            mBinding = tempBinding
            setContentView(mBinding.root)
        }

//        WindowCompat.setDecorFitsSystemWindows(getWindow(), true)
    }

    @JvmOverloads
    fun showLoading(cancelable: Boolean = true) {
        if (mLoadingDialog == null) {
            mLoadingDialog = AlertDialog.Builder(this).create().apply {
                window?.decorView?.setBackgroundColor(Color.TRANSPARENT)
                setView(ProgressBar(this@BaseActivity).apply {
                    isIndeterminate = true
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                })
            }
        }
        mLoadingDialog?.setCancelable(cancelable)
        mLoadingDialog?.show()
    }

    fun dismissLoading() {
        mLoadingDialog?.dismiss()
    }

    private fun getViewBindingByReflect(inflater: LayoutInflater): B? {
        return try {
            val c: Class<B>? = getGenericClass(javaClass, 0)
            com.john6.example.base.util.BaseUtil.getViewBinding(c, inflater) as B
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}