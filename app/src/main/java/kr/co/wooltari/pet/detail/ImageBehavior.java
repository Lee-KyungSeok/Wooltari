package kr.co.wooltari.pet.detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import kr.co.wooltari.R;

/**
 * CustomBehavior
 *  - 출처 : http://www.devexchanges.info/2016/03/android-tip-custom-coordinatorlayout.html
 */

public class ImageBehavior extends CoordinatorLayout.Behavior {
    private final static float MIN_PROFILE_PERCENTAGE_SIZE = 0.3f;
    private final static int EXTRA_FINAL_PROFILE_PADDING = 80;

    private final static String TAG = "image_behavior";
    private final Context context;
    private float profileMaxSize;

    private float startToolbarPosition;
    private float finalLeftProfilePadding;
    private float startPosition;
    private int startXPosition;
    private int finalXPosition;
    private int startYPosition;
    private int finalYPosition;
    private int startHeight;
    private int finalHeight;

    public ImageBehavior(Context context, AttributeSet attrs) {
        this.context = context;
        init();

        finalLeftProfilePadding = context.getResources().getDimension(R.dimen.pet_detail_toolbar_profile_horizontal_margin);
    }

    private void init(){
        bindDimenstions();
    }

    private void bindDimenstions(){
        profileMaxSize = context.getResources().getDimension(R.dimen.pet_profile_size);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if(child instanceof FrameLayout)
            initProperties((FrameLayout)child, dependency);

        final int maxScrollDistance = (int) (startToolbarPosition);
        float expandedPercentageFactor = dependency.getY() / maxScrollDistance;

        float distanceYToSubtract = ((startYPosition - finalYPosition)
                * (1f - expandedPercentageFactor)) + (child.getHeight()/2);

        float distanceXToSubtract = ((startXPosition - finalXPosition)
                * (1f - expandedPercentageFactor)) + (child.getWidth()/2);

        float heightToSubtract = ((startHeight - finalHeight) * (1f - expandedPercentageFactor));

        child.setY(startYPosition - distanceYToSubtract + getStatusBarHeight());
        child.setX((startXPosition - distanceXToSubtract));

        int proportionalAvatarSize = (int) (profileMaxSize * (expandedPercentageFactor));

        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        lp.width = (int) (startHeight - heightToSubtract);
        lp.height = (int) (startHeight - heightToSubtract);
        child.setLayoutParams(lp);
        return true;
    }

    @SuppressLint("PrivateResource")
    private void initProperties(FrameLayout child, View dependency) {
        if (startYPosition == 0)
            startYPosition = (int) dependency.getY();

        if (finalYPosition == 0)
            finalYPosition = dependency.getHeight() / 2;

        if (startHeight == 0)
            startHeight = child.getHeight();

        if (finalHeight == 0)
            finalHeight = context.getResources().getDimensionPixelOffset(R.dimen.pet_detail_toolbar_profile_small_width);

        if (startXPosition == 0)
            startXPosition = (int) (child.getX() + child.getWidth()/2);

        if (finalXPosition == 0)
            finalXPosition = context.getResources().getDimensionPixelSize(R.dimen.abc_action_bar_content_inset_with_nav);
        // 아래는 다른 크기
//        context.getResources().getDimensionPixelOffset(R.dimen.abc_action_bar_content_inset_material)+ (finalHeight / 2)

        if (startToolbarPosition == 0)
            startToolbarPosition = dependency.getY();
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resourceId >0)
            result = context.getResources().getDimensionPixelSize(resourceId);

        return result;
    }
}
