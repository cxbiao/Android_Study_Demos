package com.bryan.buy;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import com.itheima.alipay.ISafePay;


public class MainActivity extends Activity {
	private ISafePay iSafePay;
	private MyConn conn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		Intent intent = new Intent();
//		intent.setAction("com.itheima.alipay");
//		startService(intent);
		//保证服务长期后台运行。

	}

	public void start(View view){
		Intent intent = new Intent();
		intent.setAction("com.itheima.alipay");
		startService(intent);
	}

	public void stop(View view){
		Intent intent = new Intent();
		intent.setAction("com.itheima.alipay");
		stopService(intent);
	}
	public void bind(View view){
		Intent intent = new Intent();
		intent.setAction("com.itheima.alipay");
		conn = new MyConn();
		bindService(intent, conn, BIND_AUTO_CREATE);//异步的操作
	}
	public void unbind(View view){
		unbindService(conn);
	}



	public void click(View view){
		Intent intent = new Intent();
		intent.setAction("com.itheima.alipay");
		conn = new MyConn();
		bindService(intent, conn, BIND_AUTO_CREATE);//异步的操作
		//绑定服务调用服务的方法。

	}

	private class MyConn implements ServiceConnection{
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			iSafePay = ISafePay.Stub.asInterface(service);
			try {
				boolean result = iSafePay.callPay(System.currentTimeMillis(), "123", 3.52f);
				if(result){
					Toast.makeText(getApplicationContext(), "支付成功，获取大炮弹", 0).show();
				}else{
					Toast.makeText(getApplicationContext(), "支付失败，请重试", 0).show();
				}
//				unbindService(conn);
//				conn = null;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}
	}
}
