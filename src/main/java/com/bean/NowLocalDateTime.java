package com.bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NowLocalDateTime {
	public static String formatDateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
	public static String nowDateTime = formatDateTime.substring(0, 10)+" "+formatDateTime.substring(11, 19);
}
