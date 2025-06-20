package com.artcode.thirtyfifty.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class DateUtilsTest {

	private DateUtils dateUtils = new DateUtils();

	@Test
	void testAddMinToTime() {
		Date currentDate = new Date();
		Date modifiedDate = dateUtils.addMinToTime(currentDate, 10);

		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.MINUTE, 10);

		assertEquals(cal.getTime(), modifiedDate);
	}

	@Test
	public void testGetMonthStartAndEndDate() throws ParseException {
	    // Given a date of September 30, 2024
	    Date testDate = new SimpleDateFormat("dd-MM-yyyy").parse("30-09-2024");
	    
	    // When calling getMonthStartAndEndDate
	    HashMap<String, Date> result = dateUtils.getMonthStartAndEndDate(testDate);
	    
	    // Then expect the start date to be September 1, 2024 at 00:00:00
	    Date expectedStartDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("01-09-2024 00:00:00");
	    assertEquals(expectedStartDate, result.get("startDate"));
	    
	    // And expect the end date to be September 30, 2024 at 23:59:59
	    Date expectedEndDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS").parse("30-09-2024 23:59:59.999");
	    assertEquals(expectedEndDate, result.get("endDate"));
	}


	public Date getMonthStartDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// Set to the first day of the month
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		setTimeToBeginningOfDay(calendar); // Set to 00:00:00
		return calendar.getTime();
	}

	public void setTimeToBeginningOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	@Test
	void testConvertDate() {
		String dateStr = "15-08-2023";
		String expectedFormattedDate = "15-08-2023";

		String result = DateUtils.convertDate(dateStr);

		assertEquals(expectedFormattedDate, result);
	}

	@Test
	void testGetDifferenceInDays() {
		Date date1 = new Date();
		Date date2 = new Date(date1.getTime() + 5 * 24 * 60 * 60 * 1000L); // Add 5 days in milliseconds

		long daysDifference = dateUtils.getDifferenceInDays(date1, date2);

		assertEquals(5, daysDifference);
	}

	@Test
	void testGetWeekEndDate() {
		LocalDate date = LocalDate.of(2023, 8, 15); // Random date
		LocalDate expectedEndDate = LocalDate.of(2023, 8, 20); // Sunday of that week

		LocalDate result = dateUtils.getWeekEndDate(date);

		assertEquals(expectedEndDate, result);
	}

	@Test
	void testGetYearFromDate_NullInput() {
		int result = dateUtils.getYearFromDate(null);
		assertEquals(-1, result); // Should return -1 for null input
	}
}
