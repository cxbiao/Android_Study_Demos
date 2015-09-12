package com.bryan.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.itheima.alipay.ISafePay;

public class SafePayService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		System.out.println("服务被绑定 onbind");
		return new MyBinder();
	}
	/**
	 * 安全支付的方法
	 */
	private boolean pay(long time,String pwd,double money){
		if("123".equals(pwd)){
			return true;
		}else{
			return false;
		}
	}

	private class MyBinder extends ISafePay.Stub {
		/**
		 * 调用安全支付的逻辑
		 */
		@Override
		public boolean callPay(long time, String pwd, double money)
				throws RemoteException {
			return pay(time, pwd, money);
		}

	}

	@Override
	public void onCreate() {
		System.out.println("oncreate支付宝服务被创建，一直在后台运行，检查手机的安全状态");
		super.onCreate();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("服务onstart");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		System.out.println("onunbind");
		return super.onUnbind(intent);

	}
	@Override
	public void onDestroy() {
		System.out.println("ondestory支付宝服务被销毁");
		super.onDestroy();
	}

}
