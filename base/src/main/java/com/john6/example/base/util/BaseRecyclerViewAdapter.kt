package com.john6.example.base.util

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Size
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.john6.example.base.util.BaseRecyclerViewAdapter.BaseViewHolder
import com.john6.example.base.util.BaseRecyclerViewAdapter.BaseViewHolder.OnHolderItemClickListener
import java.util.ArrayList

/**
 * 基础RecyclerView adapter
 *
 * @author chenhengfei@agora.io
 */
class BaseRecyclerViewAdapter<B : ViewBinding, T, H : BaseViewHolder<B, T>> @JvmOverloads constructor(
    private val viewHolderClass: Class<H>,
    listener: OnItemClickListener<T>? = null,
    dataList: List<T>? = null
) : RecyclerView.Adapter<H>() {
    var dataList: MutableList<T>
    private val mOnItemClickListener: OnItemClickListener<T>?
    var selectedIndex = -1
    private var bindingClass: Class<B?>? = null

    init {
        if (dataList == null) {
            this.dataList = ArrayList()
        } else {
            this.dataList = ArrayList(dataList)
        }
        mOnItemClickListener = listener
    }

    private fun createHolder(mBinding: B?): H? {
        ensureBindingClass()
        try {
            val constructor = viewHolderClass.getConstructor(bindingClass)
            return constructor.newInstance(mBinding)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H {
        val mBinding = getViewBindingByReflect(LayoutInflater.from(parent.context), parent)
        val holder = createHolder(mBinding)!!
        if (mOnItemClickListener != null) {
            holder.mListener = object :OnHolderItemClickListener{
                override fun onItemClick(view: View?, position: Int, itemViewType: Int) {
                    val itemData = getItemData(position)
                    if (itemData == null) mOnItemClickListener.onItemClick(
                        view,
                        position,
                        viewType.toLong()
                    ) else mOnItemClickListener.onItemClick(
                        itemData,
                        view,
                        position,
                        viewType.toLong()
                    )
                }
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: H, position: Int) {
        val data = dataList[position]
        holder!!.binding(data, selectedIndex)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun getItemData(position: Int): T? {
        return if (position < 0 || dataList.size <= position) {
            null
        } else dataList[position]
    }

    fun getViewBindingByReflect(inflater: LayoutInflater, container: ViewGroup?): B? {
        ensureBindingClass()
        return try {
            BaseUtil.getViewBinding(bindingClass, inflater, container)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    //<editor-fold desc="CURD">
    operator fun contains(data: T): Boolean {
        return dataList.contains(data)
    }

    fun indexOf(data: T): Int {
        return dataList.indexOf(data)
    }

    fun submitListWithDiffCallback(callback: BaseDiffCallback<T>) {
        val diffResult = DiffUtil.calculateDiff(callback)
        dataList = callback.newList?: ArrayList<T>()
        diffResult.dispatchUpdatesTo(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitListAndPurge(dataList: MutableList<T>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    fun addItem(data: T) {
        val index = dataList.indexOf(data)
        if (index < 0) {
            dataList.add(data)
            notifyItemInserted(dataList.size - 1)
        } else {
            dataList[index] = data
            notifyItemChanged(index)
        }
    }

    fun addItem(data: T, index: Int) {
        dataList.add(index, data)
        notifyItemRangeChanged(index, dataList.size - index)
    }

    fun update(index: Int, data: T) {
        dataList[index] = data
        notifyItemChanged(index)
    }

    fun clear() {
        val formalCount = dataList.size
        dataList.clear()
        notifyItemRangeRemoved(0, formalCount)
    }

    fun deleteItem(@Size(min = 0) pos: Int) {
        if (0 <= pos && pos < dataList.size) {
            dataList.removeAt(pos)
            notifyItemRemoved(pos)
        }
    }

    fun deleteItem(data: T) {
        val index = dataList.indexOf(data)
        if (0 <= index && index < dataList.size) {
            dataList.remove(data)
            notifyItemRemoved(index)
        }
    }

    //</editor-fold>
    fun ensureBindingClass() {
        if (bindingClass == null) bindingClass = BaseUtil.getGenericClass(viewHolderClass, 0)
    }

    abstract class BaseViewHolder<B : ViewBinding, T>(val mBinding: B) :
        RecyclerView.ViewHolder(mBinding.root) {
        var mListener: OnHolderItemClickListener? = null
        fun onItemClick(view: View?) {
            mListener?.onItemClick(view, adapterPosition, itemViewType)
        }

        interface OnHolderItemClickListener {
            fun onItemClick(view: View?, position: Int, itemViewType: Int)
        }

        abstract fun binding(data: T?, selectedIndex: Int)

        init {
            mBinding.root.setOnClickListener { view: View? -> onItemClick(view) }
        }
    }
}