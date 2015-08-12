package bryan.com.myswipedemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import bryan.com.myswipedemo.R;

/**
 * Created by Administrator on 2015/8/3.
 */
public class GridViewAdapter extends BaseSwipeAdapter {


    private Context context;

   public GridViewAdapter(Context context){
       this.context=context;
   }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        //返回swipelayout的资源id
        return R.id.swipelayout;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        return View.inflate(context,R.layout.gridview_item,null);
    }

    @Override
    public void fillValues(final int position, View convertView) {
        TextView textView= (TextView) convertView.findViewById(R.id.content);
        textView.setText("这是内容" + position);

        final SwipeLayout swipeLayout= (SwipeLayout) convertView.findViewById(R.id.swipelayout);
        swipeLayout.setDrag(SwipeLayout.DragEdge.Right,R.id.delete);
        convertView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "delete clicked", Toast.LENGTH_SHORT).show();
            }
        });
        swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(swipeLayout.getOpenStatus()==SwipeLayout.Status.Close){
                    Toast.makeText(context, position+"clicked", Toast.LENGTH_SHORT).show();
                }else{
                    swipeLayout.close(true);
                }


            }
        });




    }

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
