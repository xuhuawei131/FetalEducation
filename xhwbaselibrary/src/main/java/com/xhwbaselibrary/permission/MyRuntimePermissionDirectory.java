package com.xhwbaselibrary.permission;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;

import com.xhwbaselibrary.R;
import com.xhwbaselibrary.utils.AppUtils;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by lingdian on 2018/3/22.
 */

public class MyRuntimePermissionDirectory {

    private static MyRuntimePermissionDirectory self;
    private ArrayList<MyPermissionGroup> permissionGroups;

    private MyRuntimePermissionDirectory() {
        this.loadPermissions();
    }

    public static MyRuntimePermissionDirectory getInstance() {
        if(self == null) {
            self = new MyRuntimePermissionDirectory();
        }

        return self;
    }

    private void loadPermissions() {
        if(this.permissionGroups == null) {
            this.permissionGroups = new ArrayList();
        } else {
            this.permissionGroups.clear();
        }

        this.loadGroup_CONTACTS();
        this.loadGroup_PHONE();
        this.loadGroup_CALENDAR();
        this.loadGroup_CAMERA();
        this.loadGroup_SENSORS();
        this.loadGroup_LOCATION();
        this.loadGroup_STORAGE();
        this.loadGroup_MICROPHONE();
        this.loadGroup_SMS();
    }

    public void showPermissionDeniedDialog(final Activity activity, String[] permissions, final boolean closeActivity) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(activity.getString(R.string.mage_str_permission_denied_desc), new Object[]{AppUtils.getAppName(activity)}) + "\n\n");
        Iterator var5 = this.permissionGroups.iterator();

        while(var5.hasNext()) {
            MyPermissionGroup group = (MyPermissionGroup)var5.next();
            String eachPermission = group.getDeniedString(permissions);
            if(!TextUtils.isEmpty(eachPermission)) {
                builder.append(eachPermission + "\n");
            }
        }

//        ((MageDialogBuilder)((MageDialogBuilder)((MageDialogBuilder)((MageDialogBuilder)((MageDialogBuilder)MageDialog.createOrigin(activity).setTitle(R.string.mage_str_permission_denied_title)).setMessage(builder.toString()).setCancelable(false)).setCanceledOnTouchOutside(false)).setPositiveButton(string.mage_str_go_and_setting, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent("android.settings.APPLICATION_SETTINGS");
//                activity.startActivity(intent);
//            }
//        })).setNegativeButton(string.mage_str_cancel, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                if(closeActivity) {
//                    activity.finish();
//                }
//
//            }
//        })).show();
    }

    private void loadGroup_SMS() {
        MyPermissionGroup groupDesc = null;
        if(Build.VERSION.SDK_INT >= 23) {
            groupDesc = new MyPermissionGroup("android.permission-group.SMS", "短信");
        } else {
            groupDesc = new MyPermissionGroup("短信");
        }

        groupDesc.add(new MyPermissionItem("android.permission.READ_SMS", "读取短信"));
        groupDesc.add(new MyPermissionItem("android.permission.RECEIVE_WAP_PUSH", "接收Wap推送"));
        groupDesc.add(new MyPermissionItem("android.permission.RECEIVE_MMS", "接收彩信"));
        groupDesc.add(new MyPermissionItem("android.permission.RECEIVE_SMS", "接收短信"));
        groupDesc.add(new MyPermissionItem("android.permission.SEND_SMS", "发送短信"));
        this.permissionGroups.add(groupDesc);
    }

    private void loadGroup_MICROPHONE() {
        MyPermissionGroup groupDesc = null;
        if(Build.VERSION.SDK_INT >= 23) {
            groupDesc = new MyPermissionGroup("android.permission-group.MICROPHONE", "麦克风");
        } else {
            groupDesc = new MyPermissionGroup("麦克风");
        }

        groupDesc.add(new MyPermissionItem("android.permission.RECORD_AUDIO", "录音"));
        this.permissionGroups.add(groupDesc);
    }

    private void loadGroup_STORAGE() {
        MyPermissionGroup groupDesc = null;
        if(Build.VERSION.SDK_INT >= 23) {
            groupDesc = new MyPermissionGroup("android.permission-group.STORAGE", "存储");
        } else {
            groupDesc = new MyPermissionGroup("存储");
        }

        groupDesc.add(new MyPermissionItem("android.permission.READ_EXTERNAL_STORAGE", "读取外部存储器"));
        groupDesc.add(new MyPermissionItem("android.permission.WRITE_EXTERNAL_STORAGE", "写入外部存储器"));
        this.permissionGroups.add(groupDesc);
    }

    private void loadGroup_LOCATION() {
        MyPermissionGroup groupDesc = null;
        if(Build.VERSION.SDK_INT >= 23) {
            groupDesc = new MyPermissionGroup("android.permission-group.LOCATION", "位置");
        } else {
            groupDesc = new MyPermissionGroup("位置");
        }

        groupDesc.add(new MyPermissionItem("android.permission.ACCESS_FINE_LOCATION", "获取精准位置"));
        groupDesc.add(new MyPermissionItem("android.permission.ACCESS_COARSE_LOCATION", "获取粗略位置"));
        this.permissionGroups.add(groupDesc);
    }

    private void loadGroup_SENSORS() {
        if(Build.VERSION.SDK_INT >= 20) {
            MyPermissionGroup groupDesc = new MyPermissionGroup("android.permission-group.SENSORS", "传感器");
            groupDesc.add(new MyPermissionItem("android.permission.BODY_SENSORS", "体传感器"));
            this.permissionGroups.add(groupDesc);
        }

    }

    private void loadGroup_CAMERA() {
        MyPermissionGroup groupDesc = null;
        if(Build.VERSION.SDK_INT >= 23) {
            groupDesc = new MyPermissionGroup("android.permission-group.CAMERA", "相机");
        } else {
            groupDesc = new MyPermissionGroup("相机");
        }

        groupDesc.add(new MyPermissionItem("android.permission.CAMERA", "调用相机"));
        this.permissionGroups.add(groupDesc);
    }

    private void loadGroup_CALENDAR() {
        MyPermissionGroup groupDesc = null;
        if(Build.VERSION.SDK_INT >= 23) {
            groupDesc = new MyPermissionGroup("android.permission-group.CALENDAR", "日历");
        } else {
            groupDesc = new MyPermissionGroup("日历");
        }

        groupDesc.add(new MyPermissionItem("android.permission.READ_CALENDAR", "读取日历"));
        groupDesc.add(new MyPermissionItem("android.permission.WRITE_CALENDAR", "写入日历"));
        this.permissionGroups.add(groupDesc);
    }

    private void loadGroup_CONTACTS() {
        MyPermissionGroup groupDesc = null;
        if(Build.VERSION.SDK_INT >= 23) {
            groupDesc = new MyPermissionGroup("android.permission-group.CONTACTS", "通讯录");
        } else {
            groupDesc = new MyPermissionGroup("通讯录");
        }

        groupDesc.add(new MyPermissionItem("android.permission.WRITE_CONTACTS", "写入通讯录"));
        groupDesc.add(new MyPermissionItem("android.permission.GET_ACCOUNTS", "访问通讯录"));
        groupDesc.add(new MyPermissionItem("android.permission.READ_CONTACTS", "读取通讯录"));
        this.permissionGroups.add(groupDesc);
    }

    private void loadGroup_PHONE() {
        MyPermissionGroup groupDesc = null;
        if(Build.VERSION.SDK_INT >= 23) {
            groupDesc = new MyPermissionGroup("android.permission-group.PHONE", "手机");
        } else {
            groupDesc = new MyPermissionGroup("手机");
        }

        groupDesc.add(new MyPermissionItem("android.permission.READ_CALL_LOG", "访问通话记录"));
        groupDesc.add(new MyPermissionItem("android.permission.READ_PHONE_STATE", "读取手机状态"));
        groupDesc.add(new MyPermissionItem("android.permission.CALL_PHONE", "拨打电话"));
        groupDesc.add(new MyPermissionItem("android.permission.WRITE_CALL_LOG", "编辑通话信息"));
        groupDesc.add(new MyPermissionItem("android.permission.USE_SIP", "使用Sip"));
        groupDesc.add(new MyPermissionItem("android.permission.PROCESS_OUTGOING_CALLS", "通话输出处理"));
        groupDesc.add(new MyPermissionItem("com.android.voicemail.permission.ADD_VOICEMAIL", "添加语音信箱"));
        this.permissionGroups.add(groupDesc);
    }
}
