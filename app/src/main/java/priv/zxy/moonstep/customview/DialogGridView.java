package priv.zxy.moonstep.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 创建人: zhang376358913
 * 创建时间: 2019/3/13 21:59
 * 类描述: 解决ScrollView + GridView滑动冲突的问题
 * 修改人: zhang376358913
 * 修改时间: zhang376358913
 * 修改备注:
 */

public class DialogGridView extends GridView {
    public DialogGridView(Context context) {
        super(context);
    }

    public DialogGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DialogGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
