package com.bryan.property_animation;


import java.util.ArrayList;
import java.util.List;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

public class ArcMenuActivity extends Activity implements OnClickListener {

	private ImageView menuImg;
	// 默认为没有展开菜单状态
	private boolean flag = true;
	
	int[] ids={R.id.image_a,R.id.image_b,R.id.image_c,R.id.image_d,R.id.image_e,R.id.image_f,R.id.image_g,R.id.image_h};
    List<ImageView> imageList=new ArrayList<ImageView>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_arcmenu);

		
		for (int i = 0; i <ids.length; i++) {
			ImageView menu = (ImageView) findViewById(ids[i]);
			menu.setOnClickListener(this);
            imageList.add(menu);
		}
        
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_a:
			if (flag) {

				startAnim();
			} else {
				closeAnim();
			}
			flag = !flag;
			break;
		default:
			Toast.makeText(this, v.getId() + "", 0).show();
			break;
		}

	}

	private void startAnim() {
       
		for(int i=1;i<ids.length;i++){
			ObjectAnimator animator=ObjectAnimator.ofFloat(imageList.get(i), "translationY", 
					0F,i*80F);
			
			//设置加速插值器
			//animator.setInterpolator(new AccelerateInterpolator());
			
			//设置弹跳插值器
			animator.setInterpolator(new BounceInterpolator());
			animator.setDuration(1000);
			animator.setStartDelay(i*300);
			animator.start();
			
		}
	}

	private void closeAnim() {
		for(int i=1;i<ids.length;i++){
			
			ObjectAnimator animator=ObjectAnimator.ofFloat(imageList.get(i), "translationY", 
					i*80F,0F);
			//设置加速插值器
			//animator.setInterpolator(new AccelerateInterpolator());
			
			//设置弹跳插值器
			animator.setInterpolator(new BounceInterpolator());
			animator.setDuration(1000);
			animator.setStartDelay(i*300);
			animator.start();
		} 
	}

}
