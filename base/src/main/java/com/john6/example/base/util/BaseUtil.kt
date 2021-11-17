package com.john6.example.base.util

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.Keep
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout
import com.john6.example.base.BuildConfig
import java.lang.reflect.ParameterizedType

@Keep
object BaseUtil {
    @JvmOverloads
    fun toast(context: Context?, msg: String?, longTime: Boolean = false) {
        var time = Toast.LENGTH_SHORT
        if (longTime) time = Toast.LENGTH_LONG
        Toast.makeText(context, msg, time).show()
    }

    fun logD(msg: String?) {
        if (BuildConfig.DEBUG) Log.d("Agora-BaseUtil", msg!!)
    }

    fun logE(msg: String?) {
        if (BuildConfig.DEBUG) Log.e("Agora-BaseUtil", msg!!)
    }

    fun logE(e: Throwable) {
        if (BuildConfig.DEBUG) Log.e("Agora-BaseUtil", e.message!!)
    }

    fun dp2px(dp: Int): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            Resources.getSystem().displayMetrics
        )
    }

    fun sp2px(sp: Int): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp.toFloat(),
            Resources.getSystem().displayMetrics
        )
    }

    fun hideKeyboard(window: Window?, view: View) {
        val con = WindowCompat.getInsetsController(window!!, view)
        con?.hide(WindowInsetsCompat.Type.ime())
        view.clearFocus()
    }

    fun showKeyboard(window: Window?, view: View?) {
        val con = WindowCompat.getInsetsController(window!!, view!!)
        con?.show(WindowInsetsCompat.Type.ime())
    }

    fun shakeViewAndVibrateToAlert(view: TextInputLayout) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        view.postDelayed({ view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY) }, 100)
        view.postDelayed({ view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY) }, 300)
        val o = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0f, 50f, -50f, 0f, 50f, 0f)
        o.interpolator = OvershootInterpolator()
        o.duration = 500L
        o.start()
    }

    /**
     * android.R.attr.actionBarSize
     */
    fun getAttrResId(context: Context, @AttrRes resId: Int): Int {
        val tv = TypedValue()
        context.theme.resolveAttribute(resId, tv, true)
        return tv.resourceId
    }

    fun getColorInt(context: Context, @AttrRes resId: Int): Int {
        val tv = TypedValue()
        context.theme.resolveAttribute(resId, tv, true)
        return if (tv.type == TypedValue.TYPE_STRING) ContextCompat.getColor(
            context,
            tv.resourceId
        ) else tv.data
    }

    fun <T> getGenericClass(clz: Class<*>, index: Int): Class<T>? {
        val type = clz.genericSuperclass ?: return null
        return (type as ParameterizedType).actualTypeArguments[index] as Class<T>
    }

    fun getViewBinding(bindingClass: Class<*>?, inflater: LayoutInflater?): Any? {
        try {
            val inflateMethod =
                bindingClass!!.getDeclaredMethod("inflate", LayoutInflater::class.java)
            return inflateMethod.invoke(null, inflater)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun <T> getViewBinding(
        bindingClass: Class<T>?,
        inflater: LayoutInflater?,
        container: ViewGroup?
    ): T? {
        try {
            val inflateMethod = bindingClass!!.getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                java.lang.Boolean.TYPE
            )
            return inflateMethod.invoke(null, inflater, container, false) as T
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}