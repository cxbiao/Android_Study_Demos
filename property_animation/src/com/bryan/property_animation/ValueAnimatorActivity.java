package com.bryan.property_animation;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class ValueAnimatorActivity extends Activity
{
	protected static final String TAG = "MainActivity";

	private ImageView mBlueBall;

	private float mScreenHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		mBlueBall = (ImageView) findViewById(R.id.id_ball);

		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		mScreenHeight = outMetrics.heightPixels;

	}

	/**
	 * 自由落体
	 * 
	 * @param view
	 */
	public void verticalRun(View view)
	{
		ValueAnimator animator = ValueAnimator.ofFloat(0, mScreenHeight
				- mBlueBall.getHeight());
		//animator.setTarget(mBlueBall);
		animator.setDuration(1000).start();
		animator.setInterpolator(new BounceInterpolator());
		animator.addUpdateListener(new AnimatorUpdateListener()
		{
			@Override
			public void onAnimationUpdate(ValueAnimator animation)
			{
				mBlueBall.setTranslationY((Float) animation.getAnimatedValue());
			}
		});
	}

	/**
	 * 抛物线
	 * 
	 * @param view
	 */
	public void paowuxian(View view)
	{

		ValueAnimator valueAnimator = new ValueAnimator();
		valueAnimator.setDuration(3000);
		valueAnimator.setObjectValues(new PointF(0, 0));
		valueAnimator.setInterpolator(new LinearInterpolator());
		valueAnimator.setEvaluator(new TypeEvaluator<PointF>()
		{
			// fraction = t / duration
			// fraction就是一个比值，代表流逝的时间与持续时间之比
			@Override
			public PointF evaluate(float fraction, PointF startValue,
					PointF endValue)
			{
				Log.e(TAG, fraction*3 + "");
				// x方向200px/s ，则y方向0.5 * g * t (g = 100px / s*s)
				PointF point = new PointF();
				point.x = 200 * fraction * 3;
				point.y = 0.5f * 100 * (fraction * 3) * (fraction * 3);
				return point;
			}
		});

		valueAnimator.start();
		valueAnimator.addUpdateListener(new AnimatorUpdateListener()
		{
			@Override
			public void onAnimationUpdate(ValueAnimator animation)
			{
				PointF point = (PointF) animation.getAnimatedValue();
				mBlueBall.setX(point.x);
				mBlueBall.setY(point.y);

			}
		});
	}

	public void fadeOut(View view)
	{
		ObjectAnimator anim = ObjectAnimator.ofFloat(mBlueBall, "alpha", 0.5f);
		anim.setDuration(300);
		anim.addListener(new AnimatorListenerAdapter()
		{
			@Override
			public void onAnimationEnd(Animator animation)
			{
				Log.e(TAG, "onAnimationEnd1");
				ViewGroup parent = (ViewGroup) mBlueBall.getParent();
				if (parent != null)
					parent.removeView(mBlueBall);
			}
		});
		
		anim.addListener(new AnimatorListener()
		{

			@Override
			public void onAnimationStart(Animator animation)
			{
				Log.e(TAG, "onAnimationStart");
			}

			@Override
			public void onAnimationRepeat(Animator animation)
			{
				// TODO Auto-generated method stub
				Log.e(TAG, "onAnimationRepeat");
			}

			@Override
			public void onAnimationEnd(Animator animation)
			{
				Log.e(TAG, "onAnimationEnd2");
				ViewGroup parent = (ViewGroup) mBlueBall.getParent();
				if (parent != null)
					parent.removeView(mBlueBall);
			}

			@Override
			public void onAnimationCancel(Animator animation)
			{
				// TODO Auto-generated method stub
				Log.e(TAG, "onAnimationCancel");
			}
		});
		anim.start();
	}
	
	public void onClick(View view)
	{
	}

}
