package com.itheima.myscrollview28;

import android.content.Context;
import android.os.SystemClock;

/**
 * 计算位移距离的工具类
 * @author leo
 *
 */
public class MyScroller {

	
	private int startX;
	private int startY;
	private int distanceX;
	private int distanceY;
	
	/**
	 * 开始执行动画的时间
	 */
	private long startTime;
	/**
	 * 判断是否正在执行动画
	 * true 是还在运行
	 * false  已经停止
	 */
	private boolean isFinish;

	public MyScroller(Context ctx){
		
	}

	/**
	 * 开移移动
	 * @param startX	开始时的X坐标
	 * @param startY	开始时的Y坐标
	 * @param disX		X方向 要移动的距离
	 * @param disY		Y方向 要移动的距离
	 */
	public void startScroll(int startX, int startY, int disX, int disY) {
		this.startX = startX;
		this.startY = startY;
		this.distanceX = disX;
		this.distanceY = disY;
		this.startTime = SystemClock.uptimeMillis();
		
		this.isFinish = false;
	}

	/**
	 * 默认运行的时间
	 * 毫秒值
	 */
	private int duration = 500;
	/**
	 * 当前的X值
	 */
	private long currX;
	
	/**
	 * 当前的Y值
	 */
	private long currY;
	
	/**
	 * 计算一下当前的运行状况
	 * 返回值：
	 * true  还在运行
	 * false 运行结束
	 */
	public boolean computeScrollOffset() {

		if (isFinish) {
			return false;
		}

		// 获得所用的时间
		long passTime = SystemClock.uptimeMillis() - startTime;

		// 如果时间还在允许的范围内
		if (passTime < duration) {
			
			// 当前的位置  =  开始的位置  +  移动的距离（距离 = 速度*时间）
			currX = startX + distanceX * passTime / duration;
			currY = startY + distanceY * passTime / duration;

		} else {
			currX = startX + distanceX;
			currY = startY + distanceY;
			isFinish = true;
		}

		return true;
	}

	public long getCurrX() {
		return currX;
	}

	public void setCurrX(long currX) {
		this.currX = currX;
	}
	
	
}
