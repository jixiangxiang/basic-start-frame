package com.eric.self.selfapplication.acts;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.eric.self.baselibrary.adapter.recycler.ComAdapter;
import com.eric.self.baselibrary.holder.recycler.ViewHolder;
import com.eric.self.baselibrary.util.swipeback.SwipeBackActivity;
import com.eric.self.photolibrary.images.CommonImageSource;
import com.eric.self.photolibrary.images.ImageSource;
import com.eric.self.photolibrary.ui.PicViewActivity;
import com.eric.self.selfapplication.R;
import com.eric.self.selfapplication.bean.ImageBean;

import java.util.ArrayList;
import java.util.List;

public class ListImageActivity extends SwipeBackActivity {

    private RecyclerView imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_image);
        initialize();
    }

    private void initialize() {
        imageList = (RecyclerView) findViewById(R.id.imageList);
        imageList.setLayoutManager(new LinearLayoutManager(this));
        List<ImageBean> datas = new ArrayList<>();
        datas.add(new ImageBean("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3829582425,217645016&fm=11&gp=0.jpg"));
        datas.add(new ImageBean("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1204211051,3834529407&fm=11&gp=0.jpg"));
        datas.add(new ImageBean("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=700115609,1629360841&fm=11&gp=0.jpg"));
        datas.add(new ImageBean("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1131498720,1305212106&fm=11&gp=0.jpg"));
        datas.add(new ImageBean("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2934621520,1909620703&fm=11&gp=0.jpg"));
        datas.add(new ImageBean("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2642869548,3819967964&fm=200&gp=0.jpg"));
        datas.add(new ImageBean("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1810139275,270117667&fm=27&gp=0.jpg"));
        datas.add(new ImageBean("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2490711297,2692103830&fm=27&gp=0.jpg"));
        ComAdapter<ImageBean> adapter = new ComAdapter<ImageBean>(this, datas, R.layout.item_image_list) {
            @Override
            public void covert(ViewHolder holder, final ImageBean imagebean) {
                //holder.setImage(R.id.image, imagebean.getUrl());
                final ImageView iv = (ImageView) holder.getView(R.id.imageView);
                Glide.with(ListImageActivity.this).load(imagebean.getUrl()).into(iv);
                holder.setOnclickListener(R.id.imageView, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ListImageActivity.this, PicViewActivity.class);
                        ImageSource value = new CommonImageSource(imagebean.getUrl(), imagebean.getUrl(), iv.getMeasuredWidth(), iv.getMeasuredHeight());
                        intent.putExtra("image", value);
                        Rect rect = new Rect();
                        iv.getGlobalVisibleRect(rect);
                        intent.putExtra("rect", rect);
                        intent.putExtra("scaleType", iv.getScaleType());
                        startActivity(intent);
                    }
                });
            }
        };
        imageList.setAdapter(adapter);
    }
}
