package com.xhwbaselibrary.permission;

/**
 * Created by lingdian on 2018/3/22.
 */

public abstract class MyPermissionTask {
    private String[] permissions = null;
    private boolean closePageWhenDenied = true;

    public MyPermissionTask(String[] permissions) {
        this.permissions = permissions;
    }

    public String[] getPermissions() {
        return this.permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    public boolean isClosePageWhenDenied() {
        return this.closePageWhenDenied;
    }

    public void setClosePageWhenDenied(boolean closePageWhenDenied) {
        this.closePageWhenDenied = closePageWhenDenied;
    }

    public boolean isValid() {
        return this.permissions != null && this.permissions.length > 0;
    }

    public abstract void onPermissionDenied(String[] var1);

    public abstract void allPermissionGranted();
}
