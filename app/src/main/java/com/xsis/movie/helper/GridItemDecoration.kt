package com.xsis.movie.helper

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridItemDecoration(var spanCount : Int, var spacing : Int, var includeEdge : Boolean) : RecyclerView.ItemDecoration() {

    // Taken From Stack Overflow User xuxu
    // https://stackoverflow.com/questions/28531996/android-recyclerview-gridlayoutmanager-column-spacing

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        var pos : Int = parent.getChildAdapterPosition(view)
        var column : Int = pos % spanCount

        if(includeEdge){
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            if(pos < spanCount){
                outRect.top = spacing
            }

            outRect.bottom = spacing
        } else{
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount

            if(pos >= spanCount){
                outRect.top = spacing
            }
        }

    }
}