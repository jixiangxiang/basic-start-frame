package com.eric.self.selfapplication.acts;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.eric.self.baselibrary.util.swipeback.SwipeBackActivity;
import com.eric.self.selfapplication.R;

public class PhotoActivity extends SwipeBackActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        imageView = (ImageView) findViewById(R.id.imageView);
        Glide.with(this).load("http://bpic.588ku.com/back_pic/05/11/08/37598fd214cc4a8.jpg!ww800").into(imageView);
    }
}
