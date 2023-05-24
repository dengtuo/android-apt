package com.dengtuo.apt.app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dengtuo.apt.annotation.BindView

class MainActivity : AppCompatActivity() {

    @BindView(R.id.tv_title)
    var mTvTitle: TextView? = null

    @BindView(R.id.tv_desc)
    var mTvDesc: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.apt_activity_layout_view)
    }
}
