package com.eric.self.photolibrary.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.eric.self.photolibrary.R;
import com.eric.self.photolibrary.images.ImageObject;
import com.eric.self.photolibrary.images.ImageSource;
import com.eric.self.photolibrary.views.PinchImageView;


public class PicViewActivity extends Activity {

    private static final long ANIM_TIME = 200;

    private RectF mThumbMaskRect;
    private Matrix mThumbImageMatrix;

    private ObjectAnimator mBackgroundAnimator;

    private View mBackground;
    private PinchImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取参数
        ImageSource image = (ImageSource) getIntent().getSerializableExtra("image");
        final Rect rect = getIntent().getParcelableExtra("rect");
        final ImageView.ScaleType scaleType = (ImageView.ScaleType) getIntent().getSerializableExtra("scaleType");

        final ImageObject thumb = image.getThumb();
        //view初始化
        setContentView(R.layout.activity_pic_view);
        mImageView = findViewById(R.id.pic);
        mBackground = findViewById(R.id.background);
        Glide.with(this).load(image.getOrigin().url).into(mImageView);

        mImageView.post(new Runnable() {
            @Override
            public void run() {
                mImageView.setAlpha(1f);

                //背景动画
                mBackgroundAnimator = ObjectAnimator.ofFloat(mBackground, "alpha", 0f, 1f);
                mBackgroundAnimator.setDuration(ANIM_TIME);
                mBackgroundAnimator.start();

                //status bar高度修正
                Rect tempRect = new Rect();
                mImageView.getGlobalVisibleRect(tempRect);
                rect.top = rect.top - tempRect.top;
                rect.bottom = rect.bottom - tempRect.top;

                //mask动画
                mThumbMaskRect = new RectF(rect);
                RectF bigMaskRect = new RectF(0, 0, mImageView.getWidth(), mImageView.getHeight());
                mImageView.zoomMaskTo(mThumbMaskRect, 0);
                mImageView.zoomMaskTo(bigMaskRect, ANIM_TIME);


                //图片放大动画
                RectF thumbImageMatrixRect = new RectF();
                PinchImageView.MathUtils.calculateScaledRectInContainer(new RectF(rect), thumb.width, thumb.height, scaleType, thumbImageMatrixRect);
                RectF bigImageMatrixRect = new RectF();
                PinchImageView.MathUtils.calculateScaledRectInContainer(new RectF(0, 0, mImageView.getWidth(), mImageView.getHeight()), thumb.width, thumb.height, ImageView.ScaleType.FIT_CENTER, bigImageMatrixRect);
                mThumbImageMatrix = new Matrix();
                PinchImageView.MathUtils.calculateRectTranslateMatrix(bigImageMatrixRect, thumbImageMatrixRect, mThumbImageMatrix);
                mImageView.outerMatrixTo(mThumbImageMatrix, 0);
                mImageView.outerMatrixTo(new Matrix(), ANIM_TIME);
            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageView.playSoundEffect(SoundEffectConstants.CLICK);
                finish();
            }
        });
    }

    @Override
    public void finish() {
        if ((mBackgroundAnimator != null && mBackgroundAnimator.isRunning())) {
            return;
        }

        //背景动画
        mBackgroundAnimator = ObjectAnimator.ofFloat(mBackground, "alpha", mBackground.getAlpha(), 0f);
        mBackgroundAnimator.setDuration(ANIM_TIME);
        mBackgroundAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                PicViewActivity.super.finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mBackgroundAnimator.start();

        //mask动画
        mImageView.zoomMaskTo(mThumbMaskRect, ANIM_TIME);

        //图片缩小动画
        mImageView.outerMatrixTo(mThumbImageMatrix, ANIM_TIME);
    }
}