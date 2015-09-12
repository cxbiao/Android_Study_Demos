package com.itheima.alipay;
interface ISafePay{
	boolean callPay(long time,String pwd,double money);
}