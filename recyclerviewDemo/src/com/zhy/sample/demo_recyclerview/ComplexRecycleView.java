package com.zhy.sample.demo_recyclerview;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zhy.sample.demo_recyclerview.MyGridAdapter.OnItemClickLitener;


public class ComplexRecycleView extends AppCompatActivity
{

	
	private String TAG="ComplexRecycleView";
	private RecyclerView mRecyclerView;
	private List<String> mDatas;
	private MyGridAdapter mAdapter;
	
	private SwipeRefreshLayout swipeLayout;
	
	private LinearLayoutManager layoutManager;
	
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				refresh();
				break;
			case 1:
				loadMore();
				break;
			default:
				break;
			}
			
		};
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_recyclerview);

		initData();

//		swipeLayout=(SwipeRefreshLayout) findViewById(R.id.swipelayout);
//		swipeLayout.setOnRefreshListener(new OnRefreshListener() {
//			
//			@Override
//			public void onRefresh() {
//				Toast.makeText(getBaseContext(), "正在刷新", 0).show();
//				mHandler.sendEmptyMessageDelayed(0, 3000);
//			}
//		});
//		//设置刷新颜色
//		swipeLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.BLUE);
		mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
		mAdapter = new MyGridAdapter(this, mDatas);
		layoutManager=new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setAdapter(mAdapter);

		mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
		// 设置item动画
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());

		initEvent();

	}
	
	private int firstVisiblePosition, lastVisiblePosition,visibleItemCount, totalItemCount;
	//是否在底部
	private boolean isBottom;
	ProgressDialog loadDialog;
	
	private void initEvent()
	{
		
		loadDialog=new ProgressDialog(this);
		loadDialog.setMessage("正在加载更多数据");
//		mAdapter.setOnItemClickLitener(new OnItemClickLitener()
//		{
//			@Override
//			public void onItemClick(View view, int position)
//			{
//				Toast.makeText(ComplexRecycleView.this, position + " click",
//						Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onItemLongClick(View view, int position)
//			{
//				Toast.makeText(ComplexRecycleView.this, position + " long click",
//						Toast.LENGTH_SHORT).show();
//			}
//		});
		
		
		//实现加载更多
		mRecyclerView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				// TODO Auto-generated method stub
				//当recyclerview处于空闲时才加载
				if(newState==RecyclerView.SCROLL_STATE_IDLE && isReadyForPullEnd()){
					loadDialog.show();
		        	mHandler.sendEmptyMessageDelayed(1, 2000);
				}
				
				if(newState==RecyclerView.SCROLL_STATE_IDLE && isReadyForPullStart()){
					loadDialog.show();
		        	mHandler.sendEmptyMessageDelayed(0, 2000);
				}
				super.onScrollStateChanged(recyclerView, newState);
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				// TODO Auto-generated method stub
				super.onScrolled(recyclerView, dx, dy);
				//dy:上拉为正， 下拉为负
				//Log.e(TAG, "dx:"+dx);
				//Log.e(TAG, "dy:"+dy);
			
				
			
			}
			
			
		});
		
		
		
	}

	
	private void refresh(){
		Toast.makeText(getBaseContext(), "刷新完成", 0).show();
		mAdapter.addData(1);
		//swipeLayout.setRefreshing(false);
		loadDialog.dismiss();
	}
	
	private void loadMore(){
		Toast.makeText(getBaseContext(), "加载完成", 0).show();
		mAdapter.addData(totalItemCount);
		loadDialog.dismiss();
	}
	
	protected void initData()
	{
		mDatas = new ArrayList<String>();
		for (int i = 'A'; i < 'z'; i++)
		{
			mDatas.add("" + (char) i);
		}
	}

	
	protected boolean isReadyForPullStart() {
		
		if(mAdapter==null || mAdapter.getItemCount()==0){
			return true;
		}else{
		    firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
		    if(firstVisiblePosition==0){
		    	View firstVisibleChild=mRecyclerView.getChildAt(0);
		    	if(firstVisibleChild!=null){
		    		//Log.e(TAG, "firstVisibleChild.getTop():"+firstVisibleChild.getTop()+",mRecyclerView.getTop():"+mRecyclerView.getTop());
		    		return firstVisibleChild.getTop()>=0;
		    	}
		    }
		}
		
		return false;
	}

	protected boolean isReadyForPullEnd() {
		
		
		if(mAdapter==null || mAdapter.getItemCount()==0){
			return true;
		}else{
			//visibleItemCount = mRecyclerView.getChildCount();
			firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
			totalItemCount = layoutManager.getItemCount();
			lastVisiblePosition=layoutManager.findLastVisibleItemPosition();
			
			//Log.e(TAG, "visibleItemCount:"+visibleItemCount+",firstVisiblePosition:"+firstVisiblePosition+",totalItemCount:"+totalItemCount);
			if(lastVisiblePosition==totalItemCount-1){
				//这两种方法都可以
				//View lastVisibleChild=mRecyclerView.getChildAt(lastVisiblePosition-firstVisiblePosition);
				View lastVisibleChild=layoutManager.findViewByPosition(lastVisiblePosition);
				if (lastVisibleChild != null) {
					//Log.e(TAG, "lastVisibleChild.getBottom():"+lastVisibleChild.getBottom()+",mRecyclerView.getBottom():"+mRecyclerView.getBottom());
					return lastVisibleChild.getBottom() <= mRecyclerView.getBottom()-mRecyclerView.getTop();
					
				}
			}
		}
        return false;
	}
	



}
