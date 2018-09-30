package com.iqos.imagepicker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.iqos.imageselector.utils.ImageSelector;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSelectImage = findViewById(R.id.app_main_btn_open_select);
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openGallery();
                }
            }
        });


    }

    private void openGallery() {
        //限数量的多选(比如最多9张)
        ImageSelector.builder()
                .useCamera(true)//设置是否使用拍照
                .setSingle(false)//设置是否单选
                .showGif(true)//设置是否要显示 GIF
                .setMaxSelectCount(9)//图片的最大选择数量，小于等于0时，不限数量。
                .start(this, 1);//打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                for (String permission : permissions)
                    if (0 != grantResults.length)
                        if (PackageManager.PERMISSION_GRANTED == grantResults[0])
                            openGallery();
                        else
                            System.out.println(permission);
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
