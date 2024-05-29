package com.sunny.softapp.myapplication.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;

abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getRootContentView());
        initView();
    }

    void initView() {
        initStatusBar();
    }

    private void initStatusBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .init();
    }

    abstract View getRootContentView();
}
