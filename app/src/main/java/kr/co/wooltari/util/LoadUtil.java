package kr.co.wooltari.util;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import kr.co.wooltari.R;

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

    public static int loadColor(Context context, String color){
        switch (color){
            case "colorRed": return ContextCompat.getColor(context, R.color.colorRed);
            case "colorBurgundy": return ContextCompat.getColor(context, R.color.colorBurgundy);
            case "colorPink": return ContextCompat.getColor(context, R.color.colorPink);
            case "colorBeige": return ContextCompat.getColor(context, R.color.colorBeige);
            case "colorDarkBlue": return ContextCompat.getColor(context, R.color.colorDarkBlue);
            case "colorGray": return ContextCompat.getColor(context, R.color.colorGray);
            case "colorDarkGreen": return ContextCompat.getColor(context, R.color.colorDarkGreen);
            case "colorGoldGreen": return ContextCompat.getColor(context, R.color.colorGoldGreen);
            case "colorBlueOfSea": return ContextCompat.getColor(context, R.color.colorBlueOfSea);
            case "colorOrangeMuffler": return ContextCompat.getColor(context, R.color.colorOrangeMuffler);
            case "colorLittleBlack": return ContextCompat.getColor(context, R.color.colorLittleBlack);
            case "colorPetDefault": return ContextCompat.getColor(context, R.color.colorBlackE);
            default: return ContextCompat.getColor(context,R.color.colorBlackE);
        }
    }
}
