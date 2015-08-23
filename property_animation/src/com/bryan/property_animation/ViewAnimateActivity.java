package com.bryan.property_animation;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class ViewAnimateActivity extends Activity
{
	protected static final String TAG = "ViewAnimateActivity";

	private ImageView mBlueBall;
	private float mScreenHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_animator);

		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		mScreenHeight = outMetrics.heightPixels;
		mBlueBall = (ImageView) findViewById(R.id.id_ball);

	}

	public void viewAnim(View view)
	{
		// need API12
		mBlueBall.animate()//
				.alpha(0)//
				.y(mScreenHeight / 2).setDuration(1000)
				// need API 12
				.withStartAction(new Runnable()
				{
					@Override
					public void run()
					{
						Log.e(TAG, "START");
					}
					// need API 16
				}).withEndAction(new Runnable()
				{

					@Override
					public void run()
					{
						Log.e(TAG, "END");
						runOnUiThread(new Runnable()
						{
							@Override
							public void run()
							{
								mBlueBall.setY(0);
								mBlueBall.setAlpha(1.0f);
							}
						});
					}
				}).start();
	}

	public void propertyValuesHolder(View view)
	{
		PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
				0f, 1f);
		PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
				0, 1f);
		PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
				0, 1f);
		ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY,pvhZ).setDuration(1000).start();
	}

}
