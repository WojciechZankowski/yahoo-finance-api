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

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.ValidationException;

/**
 * Date: 23.09.2015
 *
 * @author Wojciech Zankowski
 */
public class RequestFactory {

	private final static Logger LOGGER = Logger.getLogger(RequestFactory.class.getName());

	protected static Runnable createDoubleRequest(DataReceiver receiver, int requestID, Contract contract,
			DataType dataType) {
		return () -> doubleRequest(receiver, requestID, contract, dataType);
	}

	protected static void doubleRequest(DataReceiver receiver, int requestID, Contract contract, DataType dataType) {
		try {
			String value = UrlDataParser.oneLineReader(RequestURLFactory.createStockURL(contract, dataType));
			if (ParseUtils.isParsable(value)) {
				receiver.onDoubleReceived(requestID, dataType, Double.parseDouble(value));
			}
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Couldn't download data. " + e.getMessage());
		} catch (ValidationException e) {
			LOGGER.log(Level.WARNING, "Coludn't parse data, empty value. " + e.getMessage());
		}
	}

	protected static Runnable createStringRequest(DataReceiver receiver, int requestID, Contract contract,
			DataType dataType) {
		return () -> stringRequest(receiver, requestID, contract, dataType);
	}

	protected static void stringRequest(DataReceiver receiver, int requestID, Contract contract, DataType dataType) {
		try {
			String value = UrlDataParser.oneLineReader(RequestURLFactory.createStockURL(contract, dataType));
			if (ParseUtils.isParsable(value)) {
				receiver.onStringReceived(requestID, dataType, value);
			}
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Couldn't download data. " + e.getMessage());
		} catch (ValidationException e) {
			LOGGER.log(Level.WARNING, "Coludn't parse data, empty value. " + e.getMessage());
		}
	}

	protected static Runnable createSizeRequest(DataReceiver receiver, int requestID, Contract contract,
			DataType dataType) {
		return () -> sizeRequest(receiver, requestID, contract, dataType);
	}

	protected static void sizeRequest(DataReceiver receiver, int requestID, Contract contract, DataType dataType) {
		try {
			String value = UrlDataParser.oneLineReader(RequestURLFactory.createStockURL(contract, dataType));
			if (ParseUtils.isParsable(value)) {
				receiver.onSizeReceived(requestID, dataType, Integer.parseInt(value));
			}
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Couldn't download data. " + e.getMessage());
		} catch (ValidationException e) {
			LOGGER.log(Level.WARNING, "Coludn't parse data, empty value. " + e.getMessage());
		}
	}

	protected static Runnable createForexRequest(DataReceiver receiver, int requestID, ForexCurrency fromCurrency,
			ForexCurrency toCurrency) {
		return () -> forexRequest(receiver, requestID, fromCurrency, toCurrency);
	}

	protected static void forexRequest(DataReceiver receiver, int requestID, ForexCurrency fromCurrency,
			ForexCurrency toCurrency) {
		try {
			String value = UrlDataParser.oneLineReader(RequestURLFactory.createForexURL(fromCurrency, toCurrency));
			String[] elements = value.split(DataConstants.ELEMENT_SPLITTER);
			for (String element : elements) {
				if (!ParseUtils.isParsable(element)) {
					return;
				}
			}

			long timestamp = ParseUtils.toTimestamp(elements[DataConstants.FOREX_DATE],
					elements[DataConstants.FOREX_TIME]);
			double price = Double.parseDouble(elements[DataConstants.FOREX_PRICE]);

			receiver.onForexReceived(requestID, timestamp, price);
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Couldn't download data. " + e.getMessage());
		} catch (ValidationException e) {
			LOGGER.log(Level.WARNING, "Coludn't parse data, empty value. " + e.getMessage());
		}
	}

	protected static Runnable createCustomDataRequest(DataReceiver receiver, int requestID, String instruments,
			String dataTypes) {
		return () -> customDataRequest(receiver, requestID, instruments, dataTypes);
	}

	protected static void customDataRequest(DataReceiver receiver, int requestID, String instruments, String dataTypes) {
		try {
			List<String> value = UrlDataParser.manyLinesReader(RequestURLFactory.createCustomURL(instruments, dataTypes));
			receiver.onCustomReceived(requestID, value);
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Couldn't download data. " + e.getMessage());
		}
	}

	protected static void createHistoricalRequest(DataReceiver receiver, int requestID, Contract contract,
			LocalDate startDate, LocalDate endDate, TimePeriod timePeriod) throws IOException {
		BufferedReader br = null;
		try {
			br = UrlDataParser.historicalDataStream(RequestURLFactory.createEndofdayURL(contract, startDate, endDate,
					timePeriod));
			String line;
			skipHeader(br, DataConstants.ENDOFDAY_HEADER);
			while ((line = br.readLine()) != null) {
				String[] lineElements = line.split(DataConstants.ELEMENT_SPLITTER);
				LocalDate localDate = LocalDate.parse(lineElements[DataConstants.ENDOFDAY_DATE],
						DateTimeFormatter.ofPattern(DataConstants.ENDOFDAY_DATE_FORMAT));
				double openPrice = Double.parseDouble(lineElements[DataConstants.ENDOFDAY_OPEN]);
				double highPrice = Double.parseDouble(lineElements[DataConstants.ENDOFDAY_HIGH]);
				double lowPrice = Double.parseDouble(lineElements[DataConstants.ENDOFDAY_LOW]);
				double closePrice = Double.parseDouble(lineElements[DataConstants.ENDOFDAY_CLOSE]);
				int volume = Integer.parseInt(lineElements[DataConstants.ENDOFDAY_VOLUME]);
				double adjClose = Double.parseDouble(lineElements[DataConstants.ENDOFDAY_ADJCLOSE]);

				receiver.onHistoricalReceived(requestID, localDate, closePrice, highPrice, lowPrice, openPrice, volume,
						adjClose);
			}
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					LOGGER.log(Level.WARNING, "Couldn't download data. " + e.getMessage());
				}
			}
		}
	}

	protected static void createIntradayRequest(DataReceiver receiver, int requestID, Contract contract)
			throws IOException {
		BufferedReader br = null;
		try {
			br = UrlDataParser.historicalDataStream(RequestURLFactory.createIntradayURL(contract));
			String line;
			skipHeader(br, DataConstants.INTRADAY_HEADER);
			while ((line = br.readLine()) != null) {
				String[] lineElements = line.split(DataConstants.ELEMENT_SPLITTER);
				long timestamp = Long.parseLong(lineElements[DataConstants.INTRADAY_TIMESTAMP]);
				double closePrice = Double.parseDouble(lineElements[DataConstants.INTRADAY_CLOSE]);
				double highPrice = Double.parseDouble(lineElements[DataConstants.INTRADAY_HIGH]);
				double lowPrice = Double.parseDouble(lineElements[DataConstants.INTRADAY_LOW]);
				double openPrice = Double.parseDouble(lineElements[DataConstants.INTRADAY_OPEN]);
				int volume = Integer.parseInt(lineElements[DataConstants.INTRADAY_VOLUME]);

				receiver.onIntradayReceived(requestID, timestamp, closePrice, highPrice, lowPrice, openPrice, volume);
			}
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					LOGGER.log(Level.WARNING, "Couldn't download data. " + e.getMessage());
				}
			}
		}
	}

	protected static Runnable createMarketDataRequest(DataReceiver receiver, int requestID, Contract contract,
			DataType dataType) {
		switch (dataType) {
		case ASK:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case BID:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case ASK_RT:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case BID_RT:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case PREVIOUS_CLOSE:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case OPEN:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case DIVIDEND_YIELD:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case DIVIDEND_PER_SHARE:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case DIVIDEND_PAY_DATE:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case EX_DIVIDEND_DATE:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case CHANGE:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case CHANGE_PERCENT:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case CHANGE_RT:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case CHANGE_PERCENT_RT:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case CHANGE_IN_PERCENT:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case LAST_TRADE_DATE:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case TRADE_DATE:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case LAST_TRADE_TIME:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case AFTER_HOURS_CHANGE_RT:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case COMMISSION:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case DAYS_LOW:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case DAYS_HIGH:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case LAST_TRADE_WITH_TIME_RT:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case LAST_TRADE_WITH_TIME:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case LAST_TRADE_PRICE:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case TARGET_PRICE_1Y:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case CHANGE_200_DAY_MA:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case CHANGE_PERCENT_200_DAY_MA:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case CHANGE_50_DAY_MA:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case CHANGE_PERCENT_50_DAY_MA:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case MA_50_DAY:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case MA_200_DAY:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case DAYS_VALUE_CHANGE:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case DAYS_VALUE_CHANGE_RT:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case PRICE_PAID:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case DAYS_RANGE:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case DAYS_RANGE_RT:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case HOLDINGS_GAIN_PERCENT:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case ANNUALIZED_GAIN:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case HOLDINGS_GAIN:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case HOLDINGS_GAIN_PERCENT_RT:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case HOLDINGS_GAIN_RT:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case WEEK_52_HIGH:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case WEEK_52_LOW:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case CHANGE_WEEK_52_LOW:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case CHANGE_WEEK_52_HIGH:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case CHANGE_PERCENT_WEEK_52_LOW:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case CHANGE_PERCENT_WEEK_52_HIGH:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case WEEK_52_RANGE:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case SYMBOL_INFO:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case MARKET_CAPITALIZATION:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case MARKET_CAP_RT:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case FLOAT_SHARES:
			return RequestFactory.createSizeRequest(receiver, requestID, contract, dataType);
		case NAME:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case NOTES:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case SYMBOL:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case SHARES_OWNED:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case STOCK_EXCHANGE:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case SHARES_OUTSTANDINIG:
			return RequestFactory.createSizeRequest(receiver, requestID, contract, dataType);
		case VOLUME:
			return RequestFactory.createSizeRequest(receiver, requestID, contract, dataType);
		case ASK_SIZE:
			return RequestFactory.createSizeRequest(receiver, requestID, contract, dataType);
		case BID_SIZE:
			return RequestFactory.createSizeRequest(receiver, requestID, contract, dataType);
		case LAST_TRADE_SIZE:
			return RequestFactory.createSizeRequest(receiver, requestID, contract, dataType);
		case AVERAGE_DAILY_VOLUME:
			return RequestFactory.createSizeRequest(receiver, requestID, contract, dataType);
		case TICKER_TREND:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case TRADE_LINKS:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case ORDER_BOOK_RT:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case HIGH_LIMIT:
			return RequestFactory.createSizeRequest(receiver, requestID, contract, dataType);
		case LOW_LIMIT:
			return RequestFactory.createSizeRequest(receiver, requestID, contract, dataType);
		case HOLDINGS_VALUE:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case HOLDINGS_VALUE_RT:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case REVENUE:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case EARNINGS_PER_SHARE:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case EPS_ESTIMATE_CURRENT_YEAR:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case EPS_ESTIMATE_NEXT_YEAR:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case EPS_ESTIMATE_NEXT_QUARTER:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case BOOK_VALUE:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case EBITDA:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		case PRICE_SALES:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case PRICE_BOOK:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case PE_RATIO:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case PE_RATIO_RT:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case PEG_RATIO:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case PRICE_EPS_ESTIMATE_CURRENT_YEAR:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case PRICE_EPS_ESTIMATE_NEXT_YEAR:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case SHORT_RATIO:
			return RequestFactory.createDoubleRequest(receiver, requestID, contract, dataType);
		case CURRENCY:
			return RequestFactory.createStringRequest(receiver, requestID, contract, dataType);
		default:
			throw new IllegalArgumentException("Wrong data type - " + dataType + ".");
		}
	}

	protected static void marketDataRequest(DataReceiver receiver, int requestID, Contract contract, DataType dataType) {
		switch (dataType) {
		case ASK:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case BID:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case ASK_RT:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case BID_RT:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case PREVIOUS_CLOSE:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case OPEN:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case DIVIDEND_YIELD:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case DIVIDEND_PER_SHARE:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case DIVIDEND_PAY_DATE:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case EX_DIVIDEND_DATE:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case CHANGE:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case CHANGE_PERCENT:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case CHANGE_RT:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case CHANGE_PERCENT_RT:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case CHANGE_IN_PERCENT:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case LAST_TRADE_DATE:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case TRADE_DATE:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case LAST_TRADE_TIME:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case AFTER_HOURS_CHANGE_RT:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case COMMISSION:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case DAYS_LOW:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case DAYS_HIGH:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case LAST_TRADE_WITH_TIME_RT:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case LAST_TRADE_WITH_TIME:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case LAST_TRADE_PRICE:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case TARGET_PRICE_1Y:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case CHANGE_200_DAY_MA:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case CHANGE_PERCENT_200_DAY_MA:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case CHANGE_50_DAY_MA:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case CHANGE_PERCENT_50_DAY_MA:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case MA_50_DAY:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case MA_200_DAY:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case DAYS_VALUE_CHANGE:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case DAYS_VALUE_CHANGE_RT:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case PRICE_PAID:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case DAYS_RANGE:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case DAYS_RANGE_RT:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case HOLDINGS_GAIN_PERCENT:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case ANNUALIZED_GAIN:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case HOLDINGS_GAIN:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case HOLDINGS_GAIN_PERCENT_RT:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case HOLDINGS_GAIN_RT:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case WEEK_52_HIGH:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case WEEK_52_LOW:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case CHANGE_WEEK_52_LOW:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case CHANGE_WEEK_52_HIGH:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case CHANGE_PERCENT_WEEK_52_LOW:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case CHANGE_PERCENT_WEEK_52_HIGH:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case WEEK_52_RANGE:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case SYMBOL_INFO:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case MARKET_CAPITALIZATION:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case MARKET_CAP_RT:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case FLOAT_SHARES:
			sizeRequest(receiver, requestID, contract, dataType);
			return;
		case NAME:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case NOTES:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case SYMBOL:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case SHARES_OWNED:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case STOCK_EXCHANGE:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case SHARES_OUTSTANDINIG:
			sizeRequest(receiver, requestID, contract, dataType);
			return;
		case VOLUME:
			sizeRequest(receiver, requestID, contract, dataType);
			return;
		case ASK_SIZE:
			sizeRequest(receiver, requestID, contract, dataType);
			return;
		case BID_SIZE:
			sizeRequest(receiver, requestID, contract, dataType);
			return;
		case LAST_TRADE_SIZE:
			sizeRequest(receiver, requestID, contract, dataType);
			return;
		case AVERAGE_DAILY_VOLUME:
			sizeRequest(receiver, requestID, contract, dataType);
			return;
		case TICKER_TREND:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case TRADE_LINKS:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case ORDER_BOOK_RT:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case HIGH_LIMIT:
			sizeRequest(receiver, requestID, contract, dataType);
			return;
		case LOW_LIMIT:
			sizeRequest(receiver, requestID, contract, dataType);
			return;
		case HOLDINGS_VALUE:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case HOLDINGS_VALUE_RT:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case REVENUE:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case EARNINGS_PER_SHARE:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case EPS_ESTIMATE_CURRENT_YEAR:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case EPS_ESTIMATE_NEXT_YEAR:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case EPS_ESTIMATE_NEXT_QUARTER:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case BOOK_VALUE:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case EBITDA:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		case PRICE_SALES:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case PRICE_BOOK:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case PE_RATIO:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case PE_RATIO_RT:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case PEG_RATIO:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case PRICE_EPS_ESTIMATE_CURRENT_YEAR:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case PRICE_EPS_ESTIMATE_NEXT_YEAR:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case SHORT_RATIO:
			doubleRequest(receiver, requestID, contract, dataType);
			return;
		case CURRENCY:
			stringRequest(receiver, requestID, contract, dataType);
			return;
		default:
			throw new IllegalArgumentException("Wrong data type - " + dataType + ".");
		}
	}

	private static void skipHeader(BufferedReader br, int headerLength) throws IOException {
		int counter = 0;
		while (counter < headerLength) {
			br.readLine();
			counter++;
		}
	}

}