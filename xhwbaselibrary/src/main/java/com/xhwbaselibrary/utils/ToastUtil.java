package com.xhwbaselibrary.utils;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xhwbaselibrary.MyBaseApp;
import com.xhwbaselibrary.R;

/**
 * Created by xuhuawei on 2018/3/19.
 *
 */
public class ToastUtil {
    public static Toast showActionResult(int strID) {
        View v = View.inflate(MyBaseApp.context,
                R.layout.toast_result_layout, null);
        TextView tv = (TextView) v.findViewById(R.id.tv_toast);
        tv.setText(MyBaseApp.context.getString(strID));
        Toast toast = new Toast(MyBaseApp.context);
        toast.setView(v);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

    public static Toast showActionResult(String str, boolean isOk) {
        View v = View.inflate(MyBaseApp.context,
                R.layout.toast_result_layout, null);

//        float w = ScreenSizeUtil.getScreenWidth(Mage.getInstance().getApplication());
//        v.setMinimumWidth((int) (w * 0.4f));
//        v.setMinimumHeight((int) (w * 0.4f));
        ImageView iv = (ImageView) v.findViewById(R.id.iv_toast);
        iv.setImageResource(isOk ? R.drawable.toast_ok
                : R.drawable.toast_error);
        TextView tv = (TextView) v.findViewById(R.id.tv_toast);
        tv.setText(str);
        Toast toast = new Toast(MyBaseApp.context);
        toast.setView(v);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

    public static Toast showActionResult(int res, boolean isOk) {
        return showActionResult(MyBaseApp.context.getString(res), isOk);
    }
}
