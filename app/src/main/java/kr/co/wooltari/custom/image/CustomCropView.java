package kr.co.wooltari.custom.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import kr.co.wooltari.R;

/**
 * Created by Kyung on 2017-12-18.
 */

public class CustomCropView extends FrameLayout {

    ImageView imageCircle;
    Bitmap prevBit, nextBit;

    private static Point leftTop, rightBottom, center, previous;

    public CustomCropView(@NonNull Context context) {
        super(context);
    }

    public CustomCropView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context){
        prevBit = null;
        nextBit = null;
        leftTop = new Point();
        rightBottom = new Point();
        center = new Point();
        previous = new Point();
        setBackground(context);
        setImageView(context);
    }

    private void setBackground(Context context){
        GradientDrawable gd = new GradientDrawable();
        gd.setStroke(getResources().getDimensionPixelSize(R.dimen.image_crop_background_stroke),Color.WHITE);
        gd.setColor(ContextCompat.getColor(context,R.color.translucent));
    }

    private void setImageView(Context context){
        imageCircle = new ImageView(context);
        setImageSize();
        imageCircle.setBackground(new ShapeDrawable(new OvalShape()));
        imageCircle.setClipToOutline(true);
        addView(imageCircle);
    }

    private void setImageSize(){
        Log.e("width,height",getWidth()+"/W ================== H/"+getHeight());
        LayoutParams lp;
        if(getHeight()>getWidth()){
            lp = new LayoutParams(getWidth(),getWidth());
        } else {
            lp = new LayoutParams(getHeight(),getHeight());
        }
        imageCircle.setLayoutParams(lp);
    }

    public void setImage(Bitmap bm){
        imageCircle.setImageBitmap(bm);
    }
}
