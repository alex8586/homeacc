package com.homeacc.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;

public class DateUtils {

	private static FastDateFormat dateFormat = FastDateFormat.getInstance("dd.MMMM.yyyy");

	public static LocalDate dateToLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static Date localDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static String format(Date date) {
		return dateFormat.format(date).replaceAll("\\.", " ");
	}

	public static Date getStartOfMonth(int month) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.clear();
	    calendar.set(Calendar.getInstance().get(Calendar.YEAR),
	    		 month - 1,
	    		Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
	    calendar.set(Calendar.HOUR, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    return calendar.getTime();
	}

	public static Date getEndOfMonth(int month) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.clear();
	    calendar.set(Calendar.getInstance().get(Calendar.YEAR),
	    		 month,
	    		Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
	    calendar.set(Calendar.SECOND, -1);
	    return calendar.getTime();
	}
}
