package com.dengtuo.apt.app;

import android.os.Bundle;
import android.widget.TextView;

import com.dengtuo.apt.annotation.BindView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainV2Activity extends AppCompatActivity {


    @BindView(R.id.tv_title)
    TextView mTvTitle = null;

    @BindView(R.id.tv_desc)
    TextView mTvDesc = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apt_activity_layout_view);
    }
}
