package com.gxtc.commlibrary.helper;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gxtc.commlibrary.R;

import java.io.File;


/**
 * 图片加载框架
 */
public class ImageHelper {

    private static ImageHelper mInstance;

    private ImageHelper() {

    }

    public static ImageHelper getInstance() {
        if (mInstance == null) {
            synchronized (ImageHelper.class) {
                if (mInstance == null) {
                    mInstance = new ImageHelper();
                }
            }
        }
        return mInstance;
    }

    public void loadImage(Context context, String url, ImageView imageView) {
        if (imageView == null) {
            throw new IllegalArgumentException("imageView 不能为null");
        }
        Glide.with(context)
                .load(url)
                .dontAnimate()
                .placeholder(R.drawable.default_image)
                .error(R.drawable.error_image)
                .into(imageView);
    }

    //加载无默认图片
    public void LoadNoDefaultImage(Context context, String url, ImageView imageView) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    //加载RoundedImageView
    public void loadRoundedImage(Context context, String url, ImageView imageView) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
                .load(url)
                .asBitmap()
                .placeholder(R.drawable.default_image)
                .error(R.drawable.error_image)
                .into(imageView);
    }

    //加载头像RoundedImageView
    public void loadHeadRoundedImage(Context context, String url, ImageView imageView) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
                .load(url)
                .dontAnimate()
                .placeholder(R.drawable.default_image)
                .into(imageView);
    }

    //加载头像RoundedImageView从int
    public void loadHeadRoundedInt(Context context, int drawle, ImageView imageView) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
                .load(drawle)
                .asBitmap()
                .into(imageView);
    }


    //加载头像RoundedImageView从文件
    public void loadHeadRoundedImageFile(Context context, File file, ImageView imageView) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
                .load(file)
                .asBitmap()
                .placeholder(R.drawable.default_image)
                .error(R.drawable.error_image)
                .into(imageView);
    }

    //加载banner图片
    public void loadBannerImage(Context context, String url, ImageView imageView) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .placeholder(R.drawable.default_image_banner)
                .error(R.drawable.error_image)
                .skipMemoryCache(false)//内存缓存功能(默认的)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//磁盘缓存功能(默认的)
                .into(imageView);
    }

}
