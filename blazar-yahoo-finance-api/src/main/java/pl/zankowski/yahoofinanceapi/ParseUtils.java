/*
 * Copyright 2015 Wojciech Zankowski
 *
 * http://www.zankowski.pl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.zankowski.yahoofinanceapi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * Date: 22.09.2015
 * </p>
 * 
 * <p>
 * Complex set of tools useful for parsing data provided by Yahoo Finance API.
 * </p>
 *
 * @author Wojciech Zankowski
 */
public class ParseUtils {

	/**
	 * This method parse date from String object to LocalDate object.
	 *
	 * @param dateValue
	 *            Data value in format "MM/dd/yyyy".
	 * @return Parsed date to LocalDate object.
	 */
	public static LocalDate toLocalDate(String dateValue) {
		return LocalDate.parse(trimQuotes(dateValue), DateTimeFormatter.ofPattern("M/dd/yyyy"));
	}

	/**
	 * This method parse time from String object to LocalTime object.
	 *
	 * @param timeValue
	 *            Time value in format "h:ma" - "4:00pm".
	 * @return Parsed time to LocalTime object.
	 */
	public static LocalTime toLocalTime(String timeValue) {
		return LocalTime.parse(trimQuotes(timeValue).toUpperCase(), DateTimeFormatter.ofPattern("h:ma"));
	}

	/**
	 * This method parse date and time from String objects to LocalDateTime object.
	 * 
	 * @param dateValue
	 *            Data value in format "MM/dd/yyyy".
	 * @param timeValue
	 *            Time value in format "K:ma" - "4:00pm".
	 * @return Parsed date and time to LocalDateTime object.
	 */
	public static LocalDateTime toLocalDateTime(String dateValue, String timeValue) {
		return LocalDateTime.of(toLocalDate(dateValue), toLocalTime(timeValue));
	}

	/**
	 * This method parse date and time from String objects to UNIX EPOCH timestamp.
	 * 
	 * @param dateValue
	 *            Data value in format "MM/dd/yyyy".
	 * @param timeValue
	 *            Time value in format "K:ma" - "4:00pm".
	 * @return Parsed date and time to UNIX EPOCH timestamp object.
	 */
	public static long toTimestamp(String dateValue, String timeValue) {
		return toLocalDateTime(dateValue, timeValue).toInstant(ZoneOffset.UTC).toEpochMilli();
	}

	/**
	 * <p>
	 * This method converts {@link DataType#CHANGE_PERCENT} String object to Tuple of double objects. Example value that
	 * can be passed:
	 * </p>
	 *
	 * <p>
	 * "13.65 - 2.12%"
	 * </p>
	 *
	 * @param value
	 *            Value for convertions.
	 * @return Converted value.
	 */
	public static Tuple<Double, Double> changePercentToTuple(String value) {
		String[] elements = splitValue(value);

		if (elements.length != 2 || !value.matches("^(\"[0-9]+|\"-[0-9]+|[0-9]+|-[0-9]+).*-+.*([0-9]+%|[0-9]+%\")$")) {
			throw new IllegalArgumentException("Illegal arguments. Value should be in format: \"13.65 - 2.12%\".");
		}

		return new Tuple<>(Double.parseDouble(elements[0]), Double.parseDouble(trimPercent(elements[1])));
	}

	/**
	 * <p>
	 * This method converts {@link DataType#LAST_TRADE_WITH_TIME} String object to Tuple of LocalTime and double
	 * objects. Example value that can be passed:
	 * </p>
	 *
	 * <p>
	 * "4:00pm - <b>629.25</b>"
	 * </p>
	 *
	 * @param value
	 *            Value for convertions.
	 * @return Converted value.
	 */
	public static Tuple<LocalTime, Double> tradeWithTimeToTuple(String value) {
		String[] elements = splitValue(value);

		if (elements.length != 2
				|| !value.matches("^(\"[0-9]+|\"-[0-9]+|[0-9]+|-[0-9]+).*-+.*([0-9]+</b>|[0-9]+</b>\")$")) {
			throw new IllegalArgumentException("Illegal arguments. Value should be in format: \"4"
					+ ":00pm - <b>629.25</b>\".");
		}

		return new Tuple<>(toLocalTime(elements[0]), Double.parseDouble(trimBold(elements[1])));
	}

	/**
	 * <p>
	 * This method converts {@link DataType#DAYS_RANGE} String object to Tuple of double objects. Example value that can
	 * be passed:
	 * </p>
	 *
	 * <p>
	 * "627.02 - 640.00"
	 * </p>
	 * 
	 * @param value
	 *            Value for convertions.
	 * @return Converted value.
	 */
	public static Tuple<Double, Double> priceRangeToTuple(String value) {
		String[] elements = splitValue(value);

		if (elements.length != 2 || !value.matches("^(\"[0-9]+|\"-[0-9]+|[0-9]+|-[0-9]+).*-+.*([0-9]+|[0-9]+\")$")) {
			throw new IllegalArgumentException("Illegal arguments. Value should be in format: \"627.02 - 640.00\".");
		}

		return new Tuple<>(Double.parseDouble(elements[0]), Double.parseDouble(elements[1]));
	}

	/**
	 * This method converts big number passed by Yahoo Finance API with symbols at the end. It supports M and B suffix.
	 *
	 * @param value
	 *            Value for convertions.
	 * @return Converted value.
	 */
	public static long bigNumberToLong(String value) {
		if (!isParsable(value)) {
			throw new IllegalArgumentException("Illegal argument value.");
		}

		if (!value.matches("(([0-9]*.[0-9]*)|([0-9]*))[a-zA-Z]{1}$")) {
			return Long.parseLong(value);
		}

		char suffix = value.charAt(value.length() - 1);
		value = value.substring(0, value.length() - 1);

		if (suffix == 'B') {
			return (long) (Double.parseDouble(value) * 1000000000);
		} else if (suffix == 'M') {
			return (long) (Double.parseDouble(value) * 1000000);
		}

		throw new IllegalArgumentException("Number couldn't be resolved.");
	}

	/**
	 * This method converts 3.45% String object value to double object. In result return double value in form - 3.45.
	 *
	 * @param value
	 *            Value for convertions.
	 * @return Converted value.
	 */
	public static double percentValueToDouble(String value) {
		if (!isParsable(value)) {
			throw new IllegalArgumentException("Illegal argument value.");
		}
		return Double.parseDouble(trimPercent(value));
	}

	/**
	 * This method splits passed value with delimiter "-".
	 * 
	 * @param value
	 *            Value to split.
	 * @return Splitted value.
	 */
	public static String[] splitValue(String value) {
		return trimQuotes(value).split(" - ");
	}

	/**
	 * This method trim " from passed value.
	 * 
	 * @param value
	 *            Value to trim.
	 * @return Trimmed value.
	 */
	public static String trimQuotes(String value) {
		if (!isParsable(value)) {
			throw new IllegalArgumentException("Illegal argument value.");
		}
		return value.replaceAll("^\"|\"$", "");
	}

	/**
	 * This method trim % symbol from passed value.
	 *
	 * @param value
	 *            Value to trim.
	 * @return Trimmed value.
	 */
	public static String trimPercent(String value) {
		if (!isParsable(value)) {
			throw new IllegalArgumentException("Illegal argument value.");
		}
		return value.replaceAll("%", "");
	}

	/**
	 * This method trim <code><b></b></code> from passed value.
	 * 
	 * @param value
	 *            Value to trim.
	 * @return Trimmed value.
	 */
	public static String trimBold(String value) {
		if (!isParsable(value)) {
			throw new IllegalArgumentException("Illegal argument value.");
		}
		return value.replaceAll("<b>|</b>", "");
	}

	/**
	 * This method verificate if passed value is appropriate for parsing.
	 *
	 * @param value
	 *            Value to check.
	 * @return Result of verification.
	 */
	public static boolean isParsable(String value) {
		return !(value == null || value.equals("N/A") || value.equals("") || value.equals("\"\""));
	}

}
