package bryan.com.myswipedemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;

import bryan.com.myswipedemo.adapter.GridViewAdapter;

/**
 * Created by Administrator on 2015/8/3.
 */
public class GridViewExample extends Activity {


    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview_demo);

        gridView= (GridView) findViewById(R.id.gridview);

        GridViewAdapter adapter=new GridViewAdapter(this);
        gridView.setAdapter(adapter);
        adapter.setMode(Attributes.Mode.Multiple);


        //OnItemClick在swipe关闭时会触发，应使用getSurfaceView().setOnClickListener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GridViewExample.this, "position:" + position + "OnItemClick", Toast.LENGTH_SHORT).show();
            }
        });

        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("GridView", "OnTouch");
                return false;
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GridViewExample.this, "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e("GridView", "onScrollStateChanged");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });




    }
}
