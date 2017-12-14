package kr.co.wooltari.util;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BlurTransformation;
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

    public static void recCropImageLoad(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new CenterCrop()))
                .into(imageView);
    }

    public static void recCropImageLoad(Context context, Uri uri, ImageView imageView){
        Glide.with(context)
                .load(uri)
                .apply(RequestOptions.bitmapTransform(new CenterCrop()))
                .into(imageView);
    }

    public static void blurImageLoad(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation()))
                .into(imageView);
    }

    public static void blurImageLoad(Context context, Uri uri, ImageView imageView){
        Glide.with(context)
                .load(uri)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation()))
                .into(imageView);
    }

    public static Uri getResourceImageUri(int resId, Context context){
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(resId)
                + '/' + context.getResources().getResourceTypeName(resId)
                + '/' + context.getResources().getResourceEntryName(resId)
        );
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
            case "black": return ContextCompat.getColor(context, R.color.black);
            case "gold": return ContextCompat.getColor(context, R.color.gold);
            case "white": return ContextCompat.getColor(context,R.color.white);
            case "brown":return ContextCompat.getColor(context, R.color.brown);
            default: return ContextCompat.getColor(context,R.color.colorBlackE);
        }
    }
}
