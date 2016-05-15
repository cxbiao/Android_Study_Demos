package com.bryan;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bryan on 2016-05-14.
 */
public class FooterBehaviorDependAppBar extends CoordinatorLayout.Behavior<View> {

    private static final String TAG = "FooterBehaviorDependAppBar";

    public FooterBehaviorDependAppBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //设置AppBarLayout为依赖
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        //此方法行不通  getTranslationY一直是0
        float translationY=Math.abs(dependency.getTranslationY());
        child.setTranslationY(translationY);
        return true;
    }
}
