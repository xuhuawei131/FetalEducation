package com.xuhuawei.love.fetaleducation.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xhwbaselibrary.base.BasePermissActivity;
import com.xhwbaselibrary.permission.MyPermissionTask;
import com.xuhuawei.love.fetaleducation.R;

import java.security.Permission;

public class StartActivity extends BasePermissActivity {
    private static final String PERMISSIO_ARRAY[] = new String[]{
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            checkAndRequestPermission();
        else {
            fetchSplashAD();
        }
    }

    private void checkAndRequestPermission() {
        checkRuntimePermission(new MyPermissionTask(PERMISSIO_ARRAY) {
            @Override
            public void onPermissionDenied(String[] var1) {

            }

            @Override
            public void allPermissionGranted() {
                fetchSplashAD();
            }
        });
    }

    private void fetchSplashAD() {
        startActivity(new Intent(this,MainActivity.class));
    }


}
