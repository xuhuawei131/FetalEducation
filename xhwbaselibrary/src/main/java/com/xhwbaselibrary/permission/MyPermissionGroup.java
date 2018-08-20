package com.xhwbaselibrary.permission;

import com.xhwbaselibrary.permission.MyPermissionItem;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by lingdian on 2018/3/22.
 */

public class MyPermissionGroup {
    private String groupName;
    private String groupDesc;
    private ArrayList<MyPermissionItem> permissions;

    public MyPermissionGroup(String groupDesc) {
        this.groupDesc = groupDesc;
        this.permissions = new ArrayList();
    }

    public MyPermissionGroup(String groupName, String groupDesc) {
        this.groupName = groupName;
        this.groupDesc = groupDesc;
        this.permissions = new ArrayList();
    }

    public void add(MyPermissionItem permissionObj) {
        this.permissions.add(permissionObj);
    }

    public boolean has(String permission) {
        Iterator var2 = this.permissions.iterator();

        MyPermissionItem desc;
        do {
            if(!var2.hasNext()) {
                return false;
            }

            desc = (MyPermissionItem)var2.next();
        } while(!desc.getPermission().equals(permission));

        return true;
    }

    public MyPermissionItem get(String permission) {
        Iterator var2 = this.permissions.iterator();

        MyPermissionItem desc;
        do {
            if(!var2.hasNext()) {
                return null;
            }

            desc = (MyPermissionItem)var2.next();
        } while(!desc.getPermission().equals(permission));

        return desc;
    }

    public String getDeniedString(String[] permissions) {
        boolean find = false;
        StringBuilder builder = new StringBuilder();
        builder.append(this.groupDesc + ":\n");
        String[] var4 = permissions;
        int var5 = permissions.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String each = var4[var6];
            if(this.has(each)) {
                builder.append("    " + this.get(each).getDesc() + "\n");
                find = true;
            }
        }

        if(find) {
            return builder.toString();
        } else {
            return null;
        }
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesc() {
        return this.groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }
}
