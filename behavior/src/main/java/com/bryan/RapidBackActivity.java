package com.bryan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bryan on 2016-05-14.
 */
public class RapidBackActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    List<String> data=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapid_back);


        recyclerView= (RecyclerView) findViewById(R.id.rv_behavior);
        initData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RapidBackAdapter(this,data));
    }

    private void initData(){

        for(int i=0;i<20;i++){
            data.add("item"+i);
        }

    }


}
