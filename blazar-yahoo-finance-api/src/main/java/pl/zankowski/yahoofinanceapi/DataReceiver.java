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
import java.util.List;

/**
 * <p>
 * Date: 19.09.2015
 * </p>
 *
 * <p>
 * Interface providing methods called when Yahoo Finance API will deliver appropriate data. To handle delivered data you
 * need to create implementation of this interface and pass it to {@link Session} class.
 * </p>
 *
 * @author Wojciech Zankowski
 */
public interface DataReceiver {

	/**
	 * <p>
	 * Method provides appropriate data types from Yahoo Finance API converted to String objects. List of data that can
	 * be received in this method:
	 * </p>
	 *
	 * <ul>
	 * <li>{@link DataType#DIVIDEND_PAY_DATE}</li>
	 * <li>{@link DataType#EX_DIVIDEND_DATE}</li>
	 * <li>{@link DataType#CHANGE_PERCENT}</li>
	 * <li>{@link DataType#CHANGE_PERCENT_RT}</li>
	 * <li>{@link DataType#CHANGE_IN_PERCENT}</li>
	 * <li>{@link DataType#LAST_TRADE_DATE}</li>
	 * <li>{@link DataType#TRADE_DATE}</li>
	 * <li>{@link DataType#LAST_TRADE_TIME}</li>
	 * <li>{@link DataType#AFTER_HOURS_CHANGE_RT}</li>
	 * <li>{@link DataType#COMMISSION}</li>
	 * <li>{@link DataType#LAST_TRADE_WITH_TIME_RT}</li>
	 * <li>{@link DataType#LAST_TRADE_WITH_TIME}</li>
	 * <li>{@link DataType#CHANGE_50_DAY_MA}</li>
	 * <li>{@link DataType#DAYS_RANGE}</li>
	 * <li>{@link DataType#DAYS_RANGE_RT}</li>
	 * <li>{@link DataType#HOLDINGS_GAIN_PERCENT}</li>
	 * <li>{@link DataType#ANNUALIZED_GAIN}</li>
	 * <li>{@link DataType#HOLDINGS_GAIN}</li>
	 * <li>{@link DataType#HOLDINGS_GAIN_PERCENT_RT}</li>
	 * <li>{@link DataType#HOLDINGS_GAIN_RT}</li>
	 * <li>{@link DataType#CHANGE_PERCENT_WEEK_52_LOW}</li>
	 * <li>{@link DataType#CHANGE_PERCENT_WEEK_52_HIGH}</li>
	 * <li>{@link DataType#WEEK_52_RANGE}</li>
	 * <li>{@link DataType#SYMBOL_INFO}</li>
	 * <li>{@link DataType#MARKET_CAPITALIZATION}</li>
	 * <li>{@link DataType#MARKET_CAP_RT}</li>
	 * <li>{@link DataType#NAME}</li>
	 * <li>{@link DataType#NOTES}</li>
	 * <li>{@link DataType#SYMBOL}</li>
	 * <li>{@link DataType#SHARES_OWNED}</li>
	 * <li>{@link DataType#STOCK_EXCHANGE}</li>
	 * <li>{@link DataType#TICKER_TREND}</li>
	 * <li>{@link DataType#TRADE_LINKS}</li>
	 * <li>{@link DataType#ORDER_BOOK_RT}</li>
	 * <li>{@link DataType#HOLDINGS_VALUE}</li>
	 * <li>{@link DataType#HOLDINGS_VALUE_RT}</li>
	 * <li>{@link DataType#REVENUE}</li>
	 * <li>{@link DataType#EBITDA}</li>
	 * </ul>
	 * 
	 * @param requestID
	 *            Same as RequestID provided to {@link Session#reqMarketData(int, Contract, List)} call.
	 * @param dataType
	 *            Type of delivered data.
	 * @param value
	 *            Delivered data.
	 */
	void onStringReceived(int requestID, DataType dataType, String value);

	/**
	 * <p>
	 * Method provides appropriate data types from Yahoo Finance API converted to double objects. List of data that can
	 * be received in this method:
	 * </p>
	 * 
	 * <ul>
	 * <li>{@link DataType#ASK}</li>
	 * <li>{@link DataType#BID}</li>
	 * <li>{@link DataType#ASK_RT}</li>
	 * <li>{@link DataType#BID_RT}</li>
	 * <li>{@link DataType#PREVIOUS_CLOSE}</li>
	 * <li>{@link DataType#OPEN}</li>
	 * <li>{@link DataType#DIVIDEND_YIELD}</li>
	 * <li>{@link DataType#DIVIDEND_PER_SHARE}</li>
	 * <li>{@link DataType#CHANGE}</li>
	 * <li>{@link DataType#CHANGE_RT}</li>
	 * <li>{@link DataType#DAYS_LOW}</li>
	 * <li>{@link DataType#DAYS_HIGH}</li>
	 * <li>{@link DataType#LAST_TRADE_PRICE}</li>
	 * <li>{@link DataType#TARGET_PRICE_1Y}</li>
	 * <li>{@link DataType#CHANGE_200_DAY_MA}</li>
	 * <li>{@link DataType#CHANGE_50_DAY_MA}</li>
	 * <li>{@link DataType#MA_50_DAY}</li>
	 * <li>{@link DataType#MA_200_DAY}</li>
	 * <li>{@link DataType#DAYS_VALUE_CHANGE}</li>
	 * <li>{@link DataType#DAYS_VALUE_CHANGE_RT}</li>
	 * <li>{@link DataType#WEEK_52_HIGH}</li>
	 * <li>{@link DataType#WEEK_52_LOW}</li>
	 * <li>{@link DataType#CHANGE_WEEK_52_HIGH}</li>
	 * <li>{@link DataType#CHANGE_WEEK_52_LOW}</li>
	 * <li>{@link DataType#EARNINGS_PER_SHARE}</li>
	 * <li>{@link DataType#EPS_ESTIMATE_CURRENT_YEAR}</li>
	 * <li>{@link DataType#EPS_ESTIMATE_NEXT_QUARTER}</li>
	 * <li>{@link DataType#EPS_ESTIMATE_NEXT_YEAR}</li>
	 * <li>{@link DataType#BOOK_VALUE}</li>
	 * <li>{@link DataType#PRICE_SALES}</li>
	 * <li>{@link DataType#PRICE_BOOK}</li>
	 * <li>{@link DataType#PE_RATIO}</li>
	 * <li>{@link DataType#PE_RATIO_RT}</li>
	 * <li>{@link DataType#PEG_RATIO}</li>
	 * <li>{@link DataType#PRICE_EPS_ESTIMATE_CURRENT_YEAR}</li>
	 * <li>{@link DataType#PRICE_EPS_ESTIMATE_NEXT_YEAR}</li>
	 * <li>{@link DataType#SHORT_RATIO}</li>
	 * <li>{@link DataType#CURRENCY}</li>
	 * </ul>
	 * 
	 * @param requestID
	 *            Same as RequestID provided to {@link Session#reqMarketData(int, Contract, List)} call.
	 * @param dataType
	 *            Type of delivered data.
	 * @param value
	 *            Delivered data.
	 */
	void onDoubleReceived(int requestID, DataType dataType, double value);

	/**
	 * <p>
	 * Method provides appropriate data types from Yahoo Finance API converted to integer objects. List of data that can
	 * be received in this method:
	 * </p>
	 *
	 * <ul>
	 * <li>{@link DataType#FLOAT_SHARES}</li>
	 * <li>{@link DataType#SHARES_OUTSTANDINIG}</li>
	 * <li>{@link DataType#VOLUME}</li>
	 * <li>{@link DataType#ASK_SIZE}</li>
	 * <li>{@link DataType#BID_SIZE}</li>
	 * <li>{@link DataType#LAST_TRADE_SIZE}</li>
	 * <li>{@link DataType#AVERAGE_DAILY_VOLUME}</li>
	 * <li>{@link DataType#HIGH_LIMIT}</li>
	 * <li>{@link DataType#LOW_LIMIT}</li>
	 * </ul>
	 * 
	 * @param requestID
	 *            Same as RequestID provided to {@link Session#reqMarketData(int, Contract, List)} call.
	 * @param dataType
	 *            Type of delivered data.
	 * @param value
	 *            Delivered data.
	 */
	void onSizeReceived(int requestID, DataType dataType, int value);

	/**
	 * <p>
	 * Method streams appropriate intraday events from Yahoo Finance API.
	 * </p>
	 *
	 * @param requestID
	 *            Same as RequestID provided to {@link Session#reqIntradayData(int, Contract)} call.
	 * @param timestamp
	 *            Intraday timestamp.
	 * @param close
	 *            Intraday close price.
	 * @param high
	 *            Intraday highest price.
	 * @param low
	 *            Intraday lowest price.
	 * @param open
	 *            Intraday open price.
	 * @param volume
	 *            Intraday volume.
	 */
	void onIntradayReceived(int requestID, long timestamp, double close, double high, double low, double open,
			int volume);

	/**
	 * <p>
	 * Method streams appropriate historical events from Yahoo Finance API.
	 * </p>
	 * 
	 * @param requestID
	 *            Same as RequestID provided to
	 *            {@link Session#reqHistoricalData(int, Contract, LocalDate, LocalDate, TimePeriod)} call.
	 * @param date
	 *            Historical data date.
	 * @param close
	 *            Historical close price.
	 * @param high
	 *            Historical period highest price.
	 * @param low
	 *            Historical period lowest price.
	 * @param open
	 *            Historical period open price.
	 * @param volume
	 *            Historical volume.
	 * @param adjustedClose
	 *            Historical period adjusted close price.
	 */
	void onHistoricalReceived(int requestID, LocalDate date, double close, double high, double low, double open,
			int volume, double adjustedClose);

	/**
	 * <p>
	 * Method provides appropriate values from Yahoo Finance API called in
	 * {@link Session#reqCustomData(int, String, String, int)} method. Data is passed in List of String objects.
	 * </p>
	 *
	 * @param requestID
	 *            Same as RequestID provided to {@link Session#reqCustomData(int, String, String, int)} call.
	 * @param value
	 *            List of requested values.
	 */
	void onCustomReceived(int requestID, List<String> value);

	/**
	 * <p>
	 * Method provides Forex data from Yahoo Finance API.
	 * </p>
	 * 
	 * @param requestID
	 *            Same as RequestID provided to {@link Session#reqForexData(int, ForexCurrency, ForexCurrency, int)}
	 *            call.
	 * @param timestamp
	 *            Forex currency pair timestamp.
	 * @param price
	 *            Forex currency pair price.
	 */
	void onForexReceived(int requestID, long timestamp, double price);

}
