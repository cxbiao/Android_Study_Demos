package com.zhy.sample.refresh;

import java.util.ArrayList;
import java.util.List;

import com.zhy.sample.demo_recyclerview.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class RefreshActivity extends AppCompatActivity {

	TextView tv;
	ScrollView scrollview;
	ListView listView;
	LinearLayout root;
	private List<String> mDatas;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refresh);
		
		tv=(TextView) findViewById(R.id.tv);
		scrollview=(ScrollView) findViewById(R.id.myScrollview);
		listView=(ListView) findViewById(R.id.listview);
		root=(LinearLayout) findViewById(R.id.root);
		initData();
		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.item_home, R.id.id_num, mDatas);
		listView.setAdapter(adapter);
	}

	protected void initData()
	{
		mDatas = new ArrayList<String>();
		for (int i = 'A'; i < 'Z'; i++)
		{
			mDatas.add("" + (char) i);
		}
	}
	
	
	
	public void refresh(View v) {
		
		if(!ViewCompat.canScrollVertically(v, -1)){
			Toast.makeText(this, "拉到顶部了", 0).show();
		}else{
			Toast.makeText(this, "没拉到顶部了", 0).show();
		}

	}

	public void loadmore(View v) {
		if(!ViewCompat.canScrollVertically(v, 1)){
			Toast.makeText(this, "拉到底部了", 0).show();
		}else{
			Toast.makeText(this, "没拉到底部了", 0).show();
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.men_fresh, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case R.id.id_action_refresh:
			refresh(root);
			break;
       case R.id.id_action_more:
    	    loadmore(root);
			break;
		default:
			break;
		}
		return true;
	}
}
