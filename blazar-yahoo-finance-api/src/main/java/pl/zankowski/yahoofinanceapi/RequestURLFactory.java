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

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

/**
 * Date: 23.09.2015
 *
 * @author Wojciech Zankowski
 */
public class RequestURLFactory {

	private static final String STOCK_URL = "http://finance.yahoo.com/d/quotes.csv?";
	private static final String INTRADAY_URL = "http://chartapi.finance.yahoo.com/instrument/1.1/";
	private static final String ENDOFDAY_URL = "http://ichart.yahoo.com/table.csv?s=";

	protected static URL createForexURL(ForexCurrency fromCurrency, ForexCurrency toCurrency)
			throws MalformedURLException {
		return new URL(
				createStockAddress(RequestUtils.convertToCurrencyPairCode(fromCurrency, toCurrency),
						RequestUtils.toDataTypes(DataType.LAST_TRADE_PRICE, DataType.LAST_TRADE_DATE,
								DataType.LAST_TRADE_TIME)));
	}

	protected static URL createStockURL(Contract contract, DataType dataType) throws MalformedURLException {
		return new URL(createStockAddress(RequestUtils.toInstruments(contract), dataType.getCode()));
	}

	protected static URL createCustomURL(String instruments, String dataTypes) throws MalformedURLException {
		return new URL(createStockAddress(instruments, dataTypes));
	}

	private static String createStockAddress(String instruments, String dataTypes) {
		return STOCK_URL + "s=" + instruments + "&f=" + dataTypes;
	}

	protected static URL createEndofdayURL(Contract contract, LocalDate startDate, LocalDate endDate,
			TimePeriod timePeriod) throws MalformedURLException {
		int startMonth = startDate.getMonth().getValue() - 1;
		int startDay = startDate.getDayOfMonth();
		int startYear = startDate.getYear();
		int endMonth = endDate.getMonth().getValue() - 1;
		int endDay = endDate.getDayOfMonth();
		int endYear = endDate.getYear();
		return new URL(ENDOFDAY_URL + contract.getInstrumentCode() + contract.getExchangeCode() + "&a=" + startMonth
				+ "&b=" + startDay + "&c=" + startYear + "&d=" + endMonth + "&e=" + endDay + "&f=" + endYear + "&g"
				+ "=" + timePeriod.getCode() + "&ignore=.csv");
	}

	protected static URL createIntradayURL(Contract contract) throws MalformedURLException {
		return new URL(INTRADAY_URL + contract.getInstrumentCode() + contract.getExchangeCode()
				+ "/chartdata;type=quote;range=1d/csv");
	}

}
