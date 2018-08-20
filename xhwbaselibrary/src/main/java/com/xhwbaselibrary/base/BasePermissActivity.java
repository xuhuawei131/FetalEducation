package com.xhwbaselibrary.base;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.xhwbaselibrary.permission.MyPermissionBehavior;
import com.xhwbaselibrary.permission.MyPermissionTask;
import com.xhwbaselibrary.persistence.PermissionPresenter;

/**
 * Created by lingdian on 2018/3/22.
 */

public class BasePermissActivity extends AppCompatActivity implements MyPermissionBehavior {
    private PermissionPresenter permissionPresenter;

    public void checkRuntimePermission(String[] permissions) {
        if(this.permissionPresenter == null) {
            this.permissionPresenter = new PermissionPresenter(this);
        }

        this.permissionPresenter.checkRuntimePermission(permissions);
    }

    public void checkRuntimePermission(@NonNull MyPermissionTask task) {
        if(task.isValid()) {
            if(this.permissionPresenter == null) {
                this.permissionPresenter = new PermissionPresenter(this);
            }

            this.permissionPresenter.checkRuntimePermission(task);
        }
    }

    public Activity getActivity() {
        return this;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        this.permissionPresenter.onPermissionsResult(requestCode, permissions, grantResults);
    }

    public boolean shouldShowRequestPermissionRationale(String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    public void allPermissionGranted() {
    }

    public void onPermissionDenied(String[] permissions) {
    }
}
