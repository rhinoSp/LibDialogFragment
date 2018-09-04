package com.rhino.dialog.utils;

import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * @author LuoLin
 * @since Create on 2018/9/4.
 */
public class AnimationUtils {


    public static Animation buildTranslateAnimation() {
        //实例化TranslateAnimation
        //以自身为坐标系和长度单位，从(0,0)移动到(1,1)
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f);
        //设置动画插值器 被用来修饰动画效果,定义动画的变化率
        animation.setInterpolator(new DecelerateInterpolator());
        //设置动画执行时间
        animation.setDuration(1000);
        return animation;
    }

}
