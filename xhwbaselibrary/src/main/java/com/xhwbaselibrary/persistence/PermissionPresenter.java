package com.xhwbaselibrary.persistence;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.xhwbaselibrary.permission.MyPermissionBehavior;
import com.xhwbaselibrary.permission.MyPermissionLogicBehavior;
import com.xhwbaselibrary.permission.MyPermissionTask;
import com.xhwbaselibrary.permission.MyRuntimePermissionDirectory;

import java.util.ArrayList;

/**
 * Created by lingdian on 2018/3/22.
 */

public class PermissionPresenter implements MyPermissionLogicBehavior {

    public static final int MAGE_RUNTIME_PERMISSION = 100;
    public MyPermissionBehavior permissionBehavior;
    private MyPermissionTask task = null;

    public PermissionPresenter(MyPermissionBehavior permissionBehavior) {
        if(!(permissionBehavior instanceof Activity) && !(permissionBehavior instanceof Fragment)) {
            throw new RuntimeException("必须由Activity或者Fragment去申请运行时权限!");
        } else {
            this.permissionBehavior = permissionBehavior;
        }
    }

    public void requestPermission(String[] permissions) {
        if(this.permissionBehavior instanceof Activity) {
            ActivityCompat.requestPermissions((Activity)this.permissionBehavior, permissions, 100);
        } else if(this.permissionBehavior instanceof Fragment) {
            ((Fragment)this.permissionBehavior).requestPermissions(permissions, 100);
        }

    }

    public void checkRuntimePermission(@NonNull MyPermissionTask task) {
        this.task = task;
        this.checkRuntimePermission(task.getPermissions());
    }

    public void checkRuntimePermission(String[] permissions) {
        if(permissions != null && permissions.length != 0) {
            ArrayList<String> permissionArr = new ArrayList();
            Context context = null;
            if(this.permissionBehavior instanceof Activity) {
                context = (Context)this.permissionBehavior;
            } else if(this.permissionBehavior instanceof Fragment) {
                context = ((Fragment)this.permissionBehavior).getContext();
            }

            String[] var4 = permissions;
            int var5 = permissions.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String p = var4[var6];
                if(ContextCompat.checkSelfPermission(context, p) == -1) {
                    permissionArr.add(p);
                }
            }

            if(permissionArr.size() > 0) {
                this.requestPermission(this.transToStringArr(permissionArr));
            } else if(this.task != null) {
                this.task.allPermissionGranted();
                this.task = null;
            } else {
                this.permissionBehavior.allPermissionGranted();
            }

        }
    }

    public void onPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 100) {
            ArrayList<String> deniedPermission = new ArrayList();

            for(int i = 0; i < permissions.length; ++i) {
                if(grantResults[i] == -1) {
                    deniedPermission.add(permissions[i]);
                }
            }

            if(deniedPermission.size() > 0) {
                String[] deniedPermissions = this.transToStringArr(deniedPermission);
                boolean closeActivity = true;
                if(this.task != null) {
                    this.task.onPermissionDenied(deniedPermissions);
                    closeActivity = this.task.isClosePageWhenDenied();
                    this.task = null;
                } else {
                    this.permissionBehavior.onPermissionDenied(deniedPermissions);
                }

                MyRuntimePermissionDirectory.getInstance().showPermissionDeniedDialog(this.permissionBehavior.getActivity(), deniedPermissions, closeActivity);
            } else if(this.task != null) {
                this.task.allPermissionGranted();
                this.task = null;
            } else {
                this.permissionBehavior.allPermissionGranted();
            }

        }
    }

    private String[] transToStringArr(ArrayList<String> list) {
        String[] arr = new String[list.size()];

        for(int i = 0; i < list.size(); ++i) {
            arr[i] = (String)list.get(i);
        }

        return arr;
    }
}
