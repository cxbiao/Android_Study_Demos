package com.itheima.myscrollview28;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.itheima.myscrollview28.MyScrollView.MyPageChangedListener;

public class MainActivity extends Activity {

	private MyScrollView msv;
	
	//图片资源ID 数组
	private int[] ids = new int[]{R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6};
	
	private RadioGroup radioGroup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		msv =(MyScrollView) findViewById(R.id.myscroll_view);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		
	
		
		for (int i = 0; i < ids.length; i++) {
			ImageView image = new ImageView(this);
			image.setBackgroundResource(ids[i]);
			msv.addView(image);
			
			
		}
		
		msv.setPageChangedListener(new MyPageChangedListener() {
			
			@Override
			public void moveToDest(int currid) {
				((RadioButton)radioGroup.getChildAt(currid)).setChecked(true);
			}
		});
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				msv.moveToDest(checkedId);
				
			}
		});
		
		//给自定义viewGroup添加测试的布局
		View temp = getLayoutInflater().inflate(R.layout.temp, null);
		msv.addView(temp, 2);
		
		for (int i = 0; i < msv.getChildCount(); i++) {
			//添加radioButton
			RadioButton rbtn = new RadioButton(this);
			rbtn.setId(i);
			
			radioGroup.addView(rbtn);
			if(i == 0){
				rbtn.setChecked(true);
			}
		}
		
	}
}
