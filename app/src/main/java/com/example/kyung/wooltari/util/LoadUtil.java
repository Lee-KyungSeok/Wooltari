package com.example.kyung.wooltari.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by Kyung on 2017-11-28.
 */

public class LoadUtil {

    public static void circleImageLoad(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }
    public static void circleImageLoad(Context context, Uri uri, ImageView imageView){
        Glide.with(context)
                .load(uri)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }
}
