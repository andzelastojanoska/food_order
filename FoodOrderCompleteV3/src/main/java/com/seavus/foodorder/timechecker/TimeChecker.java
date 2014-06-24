package com.seavus.foodorder.timechecker;

import java.util.Date;

public interface TimeChecker {

	boolean isNowBetweenDateTime(final Date s, final Date e);
	
}
