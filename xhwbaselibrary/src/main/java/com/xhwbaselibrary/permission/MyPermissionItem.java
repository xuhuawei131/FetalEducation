package com.xhwbaselibrary.permission;

/**
 * Created by lingdian on 2018/3/22.
 */

public class MyPermissionItem {
    private String permission;
    private String desc;

    public MyPermissionItem(String permission, String desc) {
        this.permission = permission;
        this.desc = desc;
    }

    public String getPermission() {
        return this.permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
