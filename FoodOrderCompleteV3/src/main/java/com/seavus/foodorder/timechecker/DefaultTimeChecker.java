package com.seavus.foodorder.timechecker;

import java.util.Date;
import java.util.TimerTask;

public class DefaultTimeChecker extends TimerTask implements TimeChecker {

	@Override
	public boolean isNowBetweenDateTime(Date s, Date e) {
		final Date now = new Date();
	    return now.after(s) && now.before(e);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
