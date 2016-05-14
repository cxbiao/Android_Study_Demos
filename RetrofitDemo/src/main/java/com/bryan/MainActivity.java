package com.bryan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_get)
    public void btn_get(){
         HttpHelper.getInstance().findUserForGet();
    }

    @OnClick(R.id.btn_post)
    public void btn_post(){
        HttpHelper.getInstance().findUserForPost();
    }

    @OnClick(R.id.btn_post_body)
    public void btn_post_body(){
       HttpHelper.getInstance().postBodyJson();
    }

    @OnClick(R.id.btn_upload)
    public void btn_upload(){
        HttpHelper.getInstance().uploads();
    }


    @OnClick(R.id.btn_down)
    public void btn_down(){
         HttpHelper.getInstance().downFile("girl.jpg");
    }

    @OnClick(R.id.btn_findList)
    public void btn_findList(){
        HttpHelper.getInstance().findUserList();
    }


    @OnClick(R.id.btn_rx_get)
    public void btn_rx_get(){
        HttpRxHelper.getInstance().findUserForGet();
    }

    @OnClick(R.id.btn_rx_post)
    public void btn_rx_post(){
       HttpRxHelper.getInstance().findUserForPost();
    }

    @OnClick(R.id.btn_rx_post_body)
    public void btn_rx_post_body(){
        HttpRxHelper.getInstance().postBodyJson();
    }

    @OnClick(R.id.btn_rx_upload)
    public void btn_rx_upload(){
        HttpRxHelper.getInstance().uploads();
    }

    @OnClick(R.id.btn_rx_down)
    public void btn_rx_down(){
        HttpRxHelper.getInstance().downFile("girl.jpg");
    }

    @OnClick(R.id.btn_rx_findList)
    public void btn_rx_findList(){
        HttpRxHelper.getInstance().findUserList();
    }

}
