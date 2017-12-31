package kr.co.wooltari.custom;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import kr.co.wooltari.R;

/**
 * Created by Kyung on 2017-12-23.
 */

public class DoubleCheckLayout extends ConstraintLayout {

    private Button btnDoubleCheck;
    private OnFocusChangeListener onFocusChangeListener;
    private OnTouchListener onTouchListener;
    private OnClickListener onClickListener;
    private ConstraintSet constraintSet;

    private GradientDrawable btnGD;
    AnimatorSet beforeAniSet;

    private int btnWidth;
    private int btnHeight;

    public DoubleCheckLayout(Context context) {
        super(context);
        init();
    }

    public DoubleCheckLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DoubleCheckLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
    }

    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private void init(){
        constraintSet = new ConstraintSet();
        constraintSet.clone(this);

        initDoubleCheckButton();
    }

    @SuppressLint("ResourceType")
    private void initDoubleCheckButton(){
        btnDoubleCheck = new Button(getContext());
        btnDoubleCheck.setText(getResources().getString(R.string.user_sign_up_double_check));
        btnDoubleCheck.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
        // setId를 안할 경우 default로 -1 값이 저장되므로 여러 위젯 사용시 setId를 넣어준다.
        btnDoubleCheck.setId(100);
        addView(btnDoubleCheck);
        // constraint layout 설정
        constraintSet.connect(btnDoubleCheck.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.connect(btnDoubleCheck.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(btnDoubleCheck.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.constrainHeight(btnDoubleCheck.getId(),ConstraintSet.MATCH_CONSTRAINT);
        // constraint 적용
        constraintSet.applyTo(this);
        btnWidth = btnDoubleCheck.getWidth();
        btnHeight = btnDoubleCheck.getHeight();

        setDoubleCheckBackground();
        setBtnCheckingAnimation();
        setDoubleCheckingClick();
    }

    private void setDoubleCheckBackground(){
        btnDoubleCheck.setBackgroundResource(R.drawable.button_double_check);
        btnGD = new GradientDrawable();
        btnGD.setColor(Color.GRAY);
        btnGD.setCornerRadius(20);
        btnGD.setStroke(2,Color.DKGRAY);
        btnDoubleCheck.setBackground(btnGD);
    }

    private void setDoubleCheckingClick(){
        btnDoubleCheck.setOnClickListener(v -> {
            beforeAniSet.start();
        });
    }

    private void setBtnCheckingAnimation(){
        ObjectAnimator aniScale = ObjectAnimator.ofFloat(btnDoubleCheck, "scaleX", btnHeight);
        ValueAnimator aniColor = ValueAnimator.ofObject(new ArgbEvaluator(), Color.GRAY, ContextCompat.getColor(getContext(),R.color.wooltariColor));
        aniColor.addUpdateListener(animation -> btnGD.setColor((Integer)animation.getAnimatedValue()));
        beforeAniSet = new AnimatorSet();
        beforeAniSet.playTogether(aniScale, aniColor);
        beforeAniSet.setDuration(1000);
        beforeAniSet.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    public void setbtnCheckingAfter(boolean isSuccess){
        if (isSuccess){
            // 성공일 경우 세팅
        } else {
            // 실패일 경우 세팅
        }
    }
}
