package bryan.com.myswipedemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.List;

import bryan.com.myswipedemo.adapter.RecyclerViewAdapter;

/**
 * Created by Administrator on 2015/8/4.
 */
public class RecyclerViewExample extends Activity {


    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_demo);

        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> data=new ArrayList<String>();
        for(int i=0;i<50;i++){
            data.add("主体内容:"+i);
        }

        RecyclerViewAdapter adapter=new RecyclerViewAdapter(this,data);
        adapter.setMode(Attributes.Mode.Single);
        recyclerView.setAdapter(adapter);

    }
}
