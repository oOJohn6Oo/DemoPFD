package com.john6.example.base.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * 分割线
 *
 * @author chenhengfei@agora.io
 */
class DividerDecoration @JvmOverloads constructor(
    private val spanCount: Int,
    private val gapHorizontal: Int = BaseUtil.dp2px(16).toInt(),
    private val gapVertical: Int = BaseUtil.dp2px(16).toInt()
) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val index = parent.getChildAdapterPosition(view)
        if (spanCount == 1) {
            outRect.left = gapHorizontal
            outRect.right = gapHorizontal
        } else {
            outRect.left = gapHorizontal * (spanCount - index % spanCount) / spanCount
            outRect.right = gapHorizontal * (1 + index % spanCount) / spanCount
        }
        outRect.top = gapVertical / 2
        outRect.bottom = gapVertical / 2
    }
}