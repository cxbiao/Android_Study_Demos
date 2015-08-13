package bryan.com.myswipedemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.List;

import bryan.com.myswipedemo.R;

/**
 * Created by Administrator on 2015/8/4.
 */
public class RecyclerViewAdapter extends RecyclerSwipeAdapter<RecyclerViewAdapter.MyViewHolder> {



   private Context context;
    private List<String> mData;

    public RecyclerViewAdapter(Context context,List<String> data){
        this.context=context;
        this.mData=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
          viewHolder.contentTv.setText(mData.get(position));
          viewHolder.delBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  //获得真实的下标位置
                  int index = viewHolder.getLayoutPosition();
                  Toast.makeText(context, "position:" + index, Toast.LENGTH_SHORT).show();
                  mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                  mData.remove(index);
                  notifyItemRemoved(index);
                  mItemManger.closeAllItems();
              }
          });

       //设置了些监听器不会触发itemview的click事件
        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = viewHolder.getLayoutPosition();
                if( viewHolder.swipeLayout.getOpenStatus()==SwipeLayout.Status.Close){
                    Toast.makeText(context, index+"clicked", Toast.LENGTH_SHORT).show();
                }else{
                    viewHolder.swipeLayout.close(true);
                }
            }
        });



        mItemManger.bind(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipelayout;
    }

    public static class MyViewHolder  extends RecyclerView.ViewHolder{

        public ImageView delBtn;
        public TextView contentTv;
        public SwipeLayout swipeLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            delBtn= (ImageView) itemView.findViewById(R.id.delete);
            contentTv= (TextView) itemView.findViewById(R.id.content);
            swipeLayout= (SwipeLayout) itemView.findViewById(R.id.swipelayout);


        }
    }
}
