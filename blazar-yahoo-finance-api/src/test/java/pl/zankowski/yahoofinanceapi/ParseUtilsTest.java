package pl.zankowski.yahoofinanceapi;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Date: 26.09.2015
 *
 * @author Wojciech Zankowski
 */
public class ParseUtilsTest {

	@Test
	public void testProperDataToLocalDate() {
		testToLocalDate("5/25/2015");
	}

	@Test
	public void testQuotedDataToLocalDate() {
		testToLocalDate("\"5/25/2015\"");
	}

	@Test(expected = DateTimeParseException.class)
	public void testWrongDataToLocalDate() {
		testToLocalDate("asdas");
	}

	private void testToLocalDate(String value) {
		LocalDate expectedLocalDate = LocalDate.of(2015, 5, 25);
		LocalDate parsedLocalDate = ParseUtils.toLocalDate(value);
		assertEquals(expectedLocalDate, parsedLocalDate);
	}

	@Test
	public void testProperDataToLocalTime() {
		testToLocalTime(15, 53, "3:53pm");
		testToLocalTime(3, 53, "3:53am");
	}

	@Test
	public void testQuotedDataToLocalTime() {
		testToLocalTime(15, 53, "\"3:53pm\"");
		testToLocalTime(3, 53, "\"3:53am\"");
	}

	@Test(expected = DateTimeParseException.class)
	public void testWrongDataToLocalTime() {
		testToLocalTime(15, 53, "15:53");
		testToLocalTime(3, 53, "3:53");
	}

	private void testToLocalTime(int hour, int minute, String value) {
		LocalTime expectedLocalTime = LocalTime.of(hour, minute);
		LocalTime parsedLocalTime = ParseUtils.toLocalTime(value);
		assertEquals(expectedLocalTime, parsedLocalTime);
	}

	@Test
	public void testProperDataToLocalDateTime() {
		testToLocalDateTime(2015, 5, 25, 15, 35, "5/25/2015", "3:35pm");
		testToLocalDateTime(2015, 5, 25, 3, 35, "5/25/2015", "3:35am");
	}

	@Test
	public void testQuotedDataToLocalDateTime() {
		testToLocalDateTime(2015, 5, 25, 15, 35, "\"5/25/2015\"", "\"3:35pm\"");
		testToLocalDateTime(2015, 5, 25, 3, 35, "\"5/25/2015\"", "\"3:35am\"");
	}

	@Test(expected = DateTimeParseException.class)
	public void testWrongDataToLocalDateTime() {
		testToLocalDateTime(2015, 5, 25, 15, 35, "\"5/25/2015\"", "\"13:35pm\"");
		testToLocalDateTime(2015, 5, 25, 3, 35, "\"5/25/2015\"", "\"3:35\"");
	}

	private void testToLocalDateTime(int year, int month, int day, int hour, int minute, String dateValue,
			String timeValue) {
		LocalDateTime expectedLocalDateTime = LocalDateTime.of(year, month, day, hour, minute);
		LocalDateTime parsedLocalDateTime = ParseUtils.toLocalDateTime(dateValue, timeValue);
		assertEquals(expectedLocalDateTime, parsedLocalDateTime);
	}

	@Test
	public void testProperDataChangePercentToTuple() {
		testChangePercentToTuple(-13.65d, -2.12d, "\"-13.65 - -2.12%\"");
		testChangePercentToTuple(13.65d, -2.12d, "\"13.65 - -2.12%\"");
		testChangePercentToTuple(13.65d, 2.12d, "\"13.65 - 2.12%\"");
		testChangePercentToTuple(13.65d, 2.12d, "13.65 - 2.12%");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDataChangePercentToTuple() {
		testChangePercentToTuple(-13.65d, -2.12d, "\"13.65 - 2.12\"");
	}

	private void testChangePercentToTuple(Double expectedChangeInMoney, Double expectedChangeInPercent, String value) {
		Tuple<Double, Double> parsedTuple = ParseUtils.changePercentToTuple(value);
		assertEquals(expectedChangeInMoney, parsedTuple.getFirstValue());
		assertEquals(expectedChangeInPercent, parsedTuple.getSecondValue());
	}

	@Test
	public void testProperDataTradeWithTimeToTuple() {
		testTradeWithTimeToTuple(LocalTime.of(16, 0), 629.25d, "\"4:00pm - <b>629.25</b>\"");
		testTradeWithTimeToTuple(LocalTime.of(4, 0), 629.25d, "\"4:00am - <b>629.25</b>\"");
		testTradeWithTimeToTuple(LocalTime.of(4, 0), 629.25d, "4:00am - <b>629.25</b>");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDataTradeWithTimeToTuple() {
		testTradeWithTimeToTuple(LocalTime.of(16, 0), 629.25d, "\"4:00pm - <b>629.25\"");
	}

	private void testTradeWithTimeToTuple(LocalTime expectedFirstValue, Double expectedSecondValue, String value) {
		Tuple<LocalTime, Double> parsedTuple = ParseUtils.tradeWithTimeToTuple(value);
		assertEquals(expectedFirstValue, parsedTuple.getFirstValue());
		assertEquals(expectedSecondValue, parsedTuple.getSecondValue());
	}

	@Test
	public void testProperDataPriceRangeToTuple() {
		testPriceRangeToTuple(627.02, 640.00, "627.02 - 640.00");
		testPriceRangeToTuple(627.02, 640.00, "\"627.02 - 640.00\"");
		testPriceRangeToTuple(- 627.02, - 640.00, "\"-627.02 - -640.00\"");
		testPriceRangeToTuple(- 627.02, - 640.00, "-627.02 - -640.00");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDataPriceRangeToTuple() {
		testPriceRangeToTuple(-627.02, -640.00, "a-627.02 - -640.00d");
	}

	private void testPriceRangeToTuple(Double expectedFirstValue, Double expectedSecondValue, String value) {
		Tuple<Double, Double> parsedTuple = ParseUtils.priceRangeToTuple(value);
		assertEquals(expectedFirstValue, parsedTuple.getFirstValue());
		assertEquals(expectedSecondValue, parsedTuple.getSecondValue());
	}

	@Test
	public void testProperDataBigNumberToLong() {
		testBigNumberToLong(345, "345");
		testBigNumberToLong(23123, "23123");
		testBigNumberToLong(1200000, "1.2M");
		testBigNumberToLong(1340000000, "1.34B");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongDataBigNumberToLong() {
		testBigNumberToLong(1200000, "1.2G");
	}

	private void testBigNumberToLong(long expectedValue, String value) {
		long parsedValue = ParseUtils.bigNumberToLong(value);
		assertEquals(expectedValue, parsedValue);
	}

	@Test
	public void testPercentValueToDouble() {
		Double parsedValue = ParseUtils.percentValueToDouble("3.45%");
		assertEquals(new Double(3.45), parsedValue);
	}

	@Test
	public void testSplitValue() {
		String[] parsedValues = ParseUtils.splitValue("345.34 - 231.32");
		assertEquals("345.34", parsedValues[0]);
		assertEquals("231.32", parsedValues[1]);
	}

	@Test
	public void testTrimQuotes() {
		String parsedValue = ParseUtils.trimQuotes("\"34.3\"");
		assertEquals("34.3", parsedValue);
	}

	@Test
	public void testTrimPercent() {
		String parsedValue = ParseUtils.trimPercent("3.32%");
		assertEquals("3.32", parsedValue);
	}

	@Test
	public void testTrimBold() {
		String parsedValue = ParseUtils.trimBold("<b>23.3</b>");
		assertEquals("23.3", parsedValue);
	}

	@Test
	public void testIsParsable() {
		assertTrue(ParseUtils.isParsable("34.3"));
		assertTrue(ParseUtils.isParsable("GOOG"));
		assertFalse(ParseUtils.isParsable("N/A"));
		assertFalse(ParseUtils.isParsable(""));
		assertFalse(ParseUtils.isParsable("\"\""));
		assertFalse(ParseUtils.isParsable(null));
	}

}