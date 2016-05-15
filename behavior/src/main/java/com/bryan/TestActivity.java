package com.bryan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by bryan on 2016-05-14.
 */
public class TestActivity extends AppCompatActivity {

   TextView content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        content= (TextView) findViewById(R.id.test);

        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void small(View v){
      //  content.animate().scaleX(0).scaleY(0).alpha(0).setDuration(200).start();
       content.offsetTopAndBottom(-20);

    }


    public void big(View v){
       content.animate().scaleX(1).scaleY(1).alpha(1).setDuration(200).start();

    }
}
