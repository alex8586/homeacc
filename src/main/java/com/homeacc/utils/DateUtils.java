package com.homeacc.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
}
