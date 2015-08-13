package com.zhy.sample.demo_recyclerview;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

class MyGridAdapter extends RecyclerView.Adapter<MyGridAdapter.MyViewHolder>
{

	private List<String> mDatas;
	private LayoutInflater mInflater;
	private Context context;

	public interface OnItemClickLitener
	{
		void onItemClick(View view, int position);
		void onItemLongClick(View view , int position);
	}

	private OnItemClickLitener mOnItemClickLitener;

	public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
	{
		this.mOnItemClickLitener = mOnItemClickLitener;
	}
	

	public MyGridAdapter(Context context, List<String> datas)
	{
		mInflater = LayoutInflater.from(context);
		this.context=context;
		mDatas = datas;
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		MyViewHolder holder = new MyViewHolder(mInflater.inflate(
				R.layout.item_grid, parent, false));
		return holder;
	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, final int position)
	{
		holder.tv.setText(mDatas.get(position));

		// 如果设置了回调，则设置点击事件
		if (mOnItemClickLitener != null)
		{
			holder.itemView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					//获得正确的下标位置
					int pos = holder.getLayoutPosition();
					mOnItemClickLitener.onItemClick(holder.itemView, pos);
				}
			});
			
			holder.itemView.setOnLongClickListener(new OnLongClickListener()
			{
				@Override
				public boolean onLongClick(View v)
				{
					int pos = holder.getLayoutPosition();
					mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
					removeData(pos);
					return false;
				}
			});
		}
		
		holder.gridLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "gridlayout", 0).show();
				
			}
		});
		buildGridLayout(holder.gridLayout);
		
	}

	private void buildGridLayout(GridLayout gridLayout) {
		
		
		gridLayout.removeAllViews();
		for(int i=0;i<5;i++){
			
			ImageView iv=new ImageView(context);
			iv.setImageResource(R.drawable.ic_launcher);
			ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(getScreenWidth(context)/3,ViewGroup.LayoutParams.WRAP_CONTENT);
			gridLayout.addView(iv,params);
		}
		
		
	}


	@Override
	public int getItemCount()
	{
		return mDatas.size();
	}

	public void addData(int position)
	{
		mDatas.add(position, "Insert One");
		notifyItemInserted(position);
	}


	public void removeData(int position)
	{
		mDatas.remove(position);
		notifyItemRemoved(position);
		
		
	}

	
	public int getScreenWidth(Context context){
		return context.getResources().getDisplayMetrics().widthPixels;
	}
	
	class MyViewHolder extends ViewHolder
	{

		TextView tv;
		
		GridLayout gridLayout;

		public MyViewHolder(View view)
		{
			super(view);
			tv = (TextView) view.findViewById(R.id.id_num);
			gridLayout=(GridLayout) view.findViewById(R.id.gridlayout);
			
		
		}
	}
}