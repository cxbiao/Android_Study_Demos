package com.bryan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bryan on 2016-05-14.
 */
public class RapidBackAdapter extends RecyclerView.Adapter<RapidBackAdapter.MyViewHoder> {


    private List<String> data=new ArrayList<>();
    private Context context;


    public RapidBackAdapter(Context context,List<String> data){
        this.context=context;
        this.data=data;
    }

    @Override
    public MyViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recylcer_item,parent,false);
        return new MyViewHoder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHoder holder, int position) {
        holder.tv_content.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static  class MyViewHoder extends RecyclerView.ViewHolder{


        private TextView tv_content;
        public MyViewHoder(View itemView) {
            super(itemView);
            tv_content= (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
