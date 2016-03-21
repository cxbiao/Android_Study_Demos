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
         HttpHelper.getInstance().getUser();
    }

    @OnClick(R.id.btn_post)
    public void btn_post(){
        HttpHelper.getInstance().getUserPost();
    }

    @OnClick(R.id.btn_post_body)
    public void btn_post_body(){
       HttpHelper.getInstance().getUserPostBody();
    }

    @OnClick(R.id.btn_upload)
    public void btn_upload(){
        HttpHelper.getInstance().upload();
    }


    @OnClick(R.id.btn_down)
    public void btn_down(){
         HttpHelper.getInstance().downFile("kfc.png");
    }


    @OnClick(R.id.btn_rx_get)
    public void btn_rx_get(){
        HttpRxHelper.getInstance().getUser();
    }

    @OnClick(R.id.btn_rx_post)
    public void btn_rx_post(){
       HttpRxHelper.getInstance().getUserPost();
    }

    @OnClick(R.id.btn_rx_post_body)
    public void btn_rx_post_body(){
        HttpRxHelper.getInstance().getUserPostBody();
    }

    @OnClick(R.id.btn_rx_upload)
    public void btn_rx_upload(){
        HttpRxHelper.getInstance().upload();
    }

    @OnClick(R.id.btn_rx_down)
    public void btn_rx_down(){
        HttpRxHelper.getInstance().downFile("kfc.png");
    }

}
