package com.bryan.property_animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Xml4AnimActivity extends Activity
{

	private ImageView mMv;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xml_for_anim);

		mMv = (ImageView) findViewById(R.id.id_mv);

	}

	public void scaleX(View view)
	{
		// 加载动画
		Animator anim = AnimatorInflater.loadAnimator(this, R.animator.scalex);
		anim.setTarget(mMv);
		anim.start();
	}

	public void scaleXandScaleY(View view)
	{
		// 加载动画
		Animator anim = AnimatorInflater.loadAnimator(this, R.animator.scale);
		mMv.setPivotX(0);
		mMv.setPivotY(0);
		//显示的调用invalidate
		mMv.invalidate();
		anim.setTarget(mMv);
		anim.start();
	}

}
