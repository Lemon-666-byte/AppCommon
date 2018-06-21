package com.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 防止 scrollView 冲突的 ListView
 */
public class NoScrollListView extends ListView {

    public NoScrollListView(Context context){
        super(context);
    }
    public NoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
