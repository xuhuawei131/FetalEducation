package com.xhwbaselibrary.permission;

import android.app.Activity;
import android.support.annotation.NonNull;

/**
 * Created by lingdian on 2018/3/22.
 */

public interface MyPermissionBehavior {
    void checkRuntimePermission(String[] var1);

    void onPermissionDenied(String[] var1);

    void allPermissionGranted();

    void checkRuntimePermission(@NonNull MyPermissionTask var1);

    Activity getActivity();
}
