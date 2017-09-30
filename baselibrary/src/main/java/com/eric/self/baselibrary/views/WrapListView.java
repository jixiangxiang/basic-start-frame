package com.eric.self.baselibrary.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by eric on 2017/9/13.
 */

public class WrapListView extends ListView {
    public WrapListView(Context context) {
        super(context);
    }

    public WrapListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
