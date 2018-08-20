package com.xhwbaselibrary.customview;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * 避免短时间内重复点击而做到监听回掉
 * 这是为了避免自动化测试
 * 以及疯狂的QA的疯狂的点击
 */
public abstract class OnAvoidRepaetViewClickListener implements OnClickListener {
	
	private long lastEventTime = 0 ;
	private long currentEventTime = 0 ;
	private int interval = 100 ;
	@Override
	public void onClick(View v) {
		currentEventTime = System.currentTimeMillis();
		
		if(currentEventTime - lastEventTime < interval ){
			return ;
		}else{
			lastEventTime = currentEventTime ;
			onViewClick(v);
		}
	}
	
	public abstract void onViewClick(View view);
	

}
