package com.xhwbaselibrary.permission;

import android.support.annotation.NonNull;

/**
 * Created by lingdian on 2018/3/22.
 */

public interface MyPermissionLogicBehavior {
    void requestPermission(String[] var1);

    void checkRuntimePermission(String[] var1);

    void onPermissionsResult(int var1, @NonNull String[] var2, @NonNull int[] var3);
}
