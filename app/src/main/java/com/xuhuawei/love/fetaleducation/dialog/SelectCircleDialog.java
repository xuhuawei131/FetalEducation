package com.xuhuawei.love.fetaleducation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xhwbaselibrary.persistence.MySharedManger;
import com.xuhuawei.love.fetaleducation.R;

import static com.xuhuawei.love.fetaleducation.config.ShareConfig.SHARED_KEY_LAST_CIRCLE;

/**
 * Created by lingdian on 17/9/20.
 * http://blog.csdn.net/zouzhigang96/article/details/50454111
 */

public class SelectCircleDialog extends Dialog {

    private ImageView imageViews[]=new ImageView[5];

    private View layout_none;
    private ImageView image_none_select;

    private View layout_single;
    private ImageView image_single_select;

    private View layout_list;
    private ImageView image_list_select;

    private View layout_none_list;
    private ImageView image_list_none_select;


    private View layout_random;
    private ImageView image_random_select;


    public SelectCircleDialog(@NonNull Context context) {
        super(context, R.style.my_dialog);
    }

    public SelectCircleDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected SelectCircleDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.setCanceledOnTouchOutside(true);
    }


    public void showDialog() {
        LinearLayout root = (LinearLayout) LayoutInflater.from(getContext()).inflate(
                R.layout.layout_circle_item, null);
        root.findViewById(R.id.btn_close).setOnClickListener(btnlistener);
        layout_none=  root.findViewById(R.id.layout_none);
        image_none_select= (ImageView) root.findViewById(R.id.image_none_select);
        layout_none.setOnClickListener(btnlistener);
        imageViews[0]=image_none_select;

        layout_single=  root.findViewById(R.id.layout_single);
        image_single_select= (ImageView) root.findViewById(R.id.image_single_select);
        layout_single.setOnClickListener(btnlistener);
        imageViews[1]=image_single_select;

        layout_list=  root.findViewById(R.id.layout_list);
        image_list_select= (ImageView) root.findViewById(R.id.image_list_select);
        layout_list.setOnClickListener(btnlistener);
        imageViews[2]=image_list_select;

        layout_none_list=  root.findViewById(R.id.layout_list_none);
        image_list_none_select= (ImageView) root.findViewById(R.id.image_list_none_select);
        layout_none_list.setOnClickListener(btnlistener);
        imageViews[3]=image_list_none_select;


        layout_random=  root.findViewById(R.id.layout_random);
        image_random_select= (ImageView) root.findViewById(R.id.image_random_select);
        layout_random.setOnClickListener(btnlistener);
        imageViews[4]=image_random_select;


        int circleType= MySharedManger.getInstance().getIntValue(SHARED_KEY_LAST_CIRCLE);
        circleType=circleType==-1?0:circleType;
        for (int i=0;i<5;i++){
            if (i==circleType){
                imageViews[i].setImageResource(R.drawable.icon_msg_toggle_true);
            }else{
                imageViews[i].setImageResource(R.drawable.icon_msg_toggle_false);
            }
        }


        this.setContentView(root);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = -20; // 新位置Y坐标
        lp.width = (int) getContext().getResources().getDisplayMetrics().widthPixels; // 宽度
//      lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度
//      lp.alpha = 9f; // 透明度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        show();
    }

    private View.OnClickListener btnlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isShowing()) {
                dismiss();
            }
            if(onDialogItemClick==null){
                return;
            }
            if (v.getId() == R.id.layout_none) {
                onDialogItemClick.onDialogItem(0);
            } else if (v.getId() == R.id.layout_single) {
                onDialogItemClick.onDialogItem(1);
            } else if (v.getId() == R.id.layout_list) {
                onDialogItemClick.onDialogItem(2);
            } else if (v.getId() == R.id.layout_list_none) {
                onDialogItemClick.onDialogItem(3);
            }else if (v.getId() == R.id.layout_random) {
                onDialogItemClick.onDialogItem(4);
            }
        }
    };
    private OnDialogItemClick onDialogItemClick;

    public void setOnDialogItemClick(OnDialogItemClick click) {
        this.onDialogItemClick = click;
    }

    public interface OnDialogItemClick {
        public void onDialogItem(int index);
    }

}
