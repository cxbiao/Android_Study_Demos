package bryan.com.myswipedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;

public class MainActivity extends AppCompatActivity {

    private String TAG="MainActivity";
    private SwipeLayout swipeLayout,sample1,sample2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeLayout= (SwipeLayout) findViewById(R.id.my_swipelayout);


        //set mode
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
                Log.i(TAG, "onStartOpen");
            }

            @Override
            public void onOpen(SwipeLayout layout) {
                Log.i(TAG, "onOpen");
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
                Log.i(TAG, "onStartClose");
            }

            @Override
            public void onClose(SwipeLayout layout) {
                Log.i(TAG, "onClose");
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                Log.i(TAG, "onUpdate");
            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                Log.i(TAG, "onHandRelease");
            }
        });


        sample1= (SwipeLayout) findViewById(R.id.sample1);
        //推荐PullOut样式
        sample1.setShowMode(SwipeLayout.ShowMode.PullOut);

        //在xml中配置过layout_gravity以下就可以不写
        sample1.addDrag(SwipeLayout.DragEdge.Right, R.id.bottom1);
        sample1.addDrag(SwipeLayout.DragEdge.Left, R.id.bottom2);


        //添加监听
        sample1.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Click on surface", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "click on surface");
            }
        });
        sample1.getSurfaceView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "longClick on surface", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "longClick on surface");
                return true;
            }
        });
       //设置swipeaction的监听
        sample1.findViewById(R.id.magnifier).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "magnifier click", Toast.LENGTH_SHORT).show();
            }
        });
        sample1.findViewById(R.id.star).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "star click", Toast.LENGTH_SHORT).show();
            }
        });
        sample1.findViewById(R.id.trash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "trash click", Toast.LENGTH_SHORT).show();
            }
        });

        sample1.findViewById(R.id.archive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "archive click", Toast.LENGTH_SHORT).show();
            }
        });

        sample1.findViewById(R.id.publish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "publish click", Toast.LENGTH_SHORT).show();
            }
        });




        sample2= (SwipeLayout) findViewById(R.id.sample2);

        //laydown有种渐变的风格，个人不太喜欢
        sample2.setShowMode(SwipeLayout.ShowMode.LayDown);
        sample2.addDrag(SwipeLayout.DragEdge.Right, R.id.bottom3);

        sample2.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Click on surface", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "click on surface");
            }
        });
        sample2.getSurfaceView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "longClick on surface", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "longClick on surface");
                return true;
            }
        });

        sample2.findViewById(R.id.star).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "star click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_listview:
                startActivity(new Intent(this,ListViewExample.class));
                break;
            case R.id.action_gridview:
                startActivity(new Intent(this,GridViewExample.class));
                break;
            case R.id.action_recyclerview:
                startActivity(new Intent(this,RecyclerViewExample.class));
                break;

        }


        return super.onOptionsItemSelected(item);
    }
}
