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

/**
 * <p>
 * Date: 19.09.2015
 * </p>
 *
 * <p>
 * This enum class provides all available data that can be downloaded from Yahoo Finance API.
 * </p>
 * 
 * @author Wojciech Zankowski
 */
public enum DataType {

	/**
	 * Example response: 630.25
	 */
	ASK("a"),
	/**
	 * Example response: 629.43
	 */
	BID("b"),
	/**
	 * Example response:
	 */
	ASK_RT("b2"),
	/**
	 * Example response:
	 */
	BID_RT("b3"),
	/**
	 * Example response: 642.90
	 */
	PREVIOUS_CLOSE("p"),
	/**
	 * Example response: 636.14
	 */
	OPEN("o"),
	/**
	 * Example response: 0.0000
	 */
	DIVIDEND_YIELD("y"),
	/**
	 * Example response: 0.6300
	 */
	DIVIDEND_PER_SHARE("d"),
	/**
	 * Example response: "8/31/2015"
	 */
	DIVIDEND_PAY_DATE("r1"),
	/**
	 * Example response: "8/17/2015"
	 */
	EX_DIVIDEND_DATE("q"),
	/**
	 * Example response: -13.65
	 */
	CHANGE("c1"),
	/**
	 * Example response: "-13.65 - -2.12%"
	 */
	CHANGE_PERCENT("c"),
	/**
	 * Example response:
	 */
	CHANGE_RT("c6"),
	/**
	 * Example response:
	 */
	CHANGE_PERCENT_RT("k2"),
	/**
	 * Example response: "-2.12%"
	 */
	CHANGE_IN_PERCENT("p2"),
	/**
	 * Example response: "9/18/2015"
	 */
	LAST_TRADE_DATE("d1"),
	/**
	 * Example response:
	 */
	TRADE_DATE("d2"),
	/**
	 * Example response: "4:00pm"
	 */
	LAST_TRADE_TIME("t1"),
	/**
	 * Example response:
	 */
	AFTER_HOURS_CHANGE_RT("c8"),
	/**
	 * Example response:
	 */
	COMMISSION("c3"),
	/**
	 * Example response: 627.02
	 */
	DAYS_LOW("g"),
	/**
	 * Example response: 640.00
	 */
	DAYS_HIGH("h"),
	/**
	 * Example response:
	 */
	LAST_TRADE_WITH_TIME_RT("k1"),
	/**
	 * Example response: "4:00pm - <b>629.25</b>"
	 */
	LAST_TRADE_WITH_TIME("l"),
	/**
	 * Example response: 629.25
	 */
	LAST_TRADE_PRICE("l1"),
	/**
	 * Example response: 645.00
	 */
	TARGET_PRICE_1Y("t8"),
	/**
	 * Example response: 58.68
	 */
	CHANGE_200_DAY_MA("m5"),
	/**
	 * Example response: +10.28%
	 */
	CHANGE_PERCENT_200_DAY_MA("m6"),
	/**
	 * Example response: -1.02
	 */
	CHANGE_50_DAY_MA("m7"),
	/**
	 * Example response: -0.16%
	 */
	CHANGE_PERCENT_50_DAY_MA("m8"),
	/**
	 * Example response: 630.27
	 */
	MA_50_DAY("m3"),
	/**
	 * Example response: 570.57
	 */
	MA_200_DAY("m4"),
	/**
	 * Example response:
	 */
	DAYS_VALUE_CHANGE("w1"),
	/**
	 * Example response:
	 */
	DAYS_VALUE_CHANGE_RT("w4"),
	/**
	 * Example response:
	 */
	PRICE_PAID("p1"),
	/**
	 * Example response: "627.02 - 640.00"
	 */
	DAYS_RANGE("m"),
	/**
	 * Example response:
	 */
	DAYS_RANGE_RT("m2"),
	/**
	 * Example response:
	 */
	HOLDINGS_GAIN_PERCENT("g1"),
	/**
	 * Example response:
	 */
	ANNUALIZED_GAIN("g3"),
	/**
	 * Example response:
	 */
	HOLDINGS_GAIN("g4"),
	/**
	 * Example response:
	 */
	HOLDINGS_GAIN_PERCENT_RT("g5"),
	/**
	 * Example response:
	 */
	HOLDINGS_GAIN_RT("g6"),
	/**
	 * Example response: 678.64
	 */
	WEEK_52_HIGH("k"),
	/**
	 * Example response: 486.23
	 */
	WEEK_52_LOW("j"),
	/**
	 * Example response: 143.02
	 */
	CHANGE_WEEK_52_LOW("j5"),
	/**
	 * Example response: -49.39
	 */
	CHANGE_WEEK_52_HIGH("k4"),
	/**
	 * Example response: +29.42%
	 */
	CHANGE_PERCENT_WEEK_52_LOW("j6"),
	/**
	 * Example response: -7.28%
	 */
	CHANGE_PERCENT_WEEK_52_HIGH("k5"),
	/**
	 * Example response: "486.23 - 678.64"
	 */
	WEEK_52_RANGE("w"),
	/**
	 * Example response: 5133386
	 */
	SYMBOL_INFO("v"),
	/**
	 * Example response: 431.37B
	 */
	MARKET_CAPITALIZATION("j1"),
	/**
	 * Example response:
	 */
	MARKET_CAP_RT("j3"),
	/**
	 * Example response: 584617000
	 */
	FLOAT_SHARES("f6"),
	/**
	 * Example response: "EUR"
	 */
	CURRENCY("c4"),
	/**
	 * Example response: "Google Inc."
	 */
	NAME("n"),
	/**
	 * Example response:
	 */
	NOTES("n4"),
	/**
	 * Example response: "GOOG"
	 */
	SYMBOL("s"),
	/**
	 * Example response:
	 */
	SHARES_OWNED("s1"),
	/**
	 * Example response: "NMS"
	 */
	STOCK_EXCHANGE("x"),
	/**
	 * Example response: 685535000
	 */
	SHARES_OUTSTANDINIG("j2"),
	/**
	 * Example response: 5133386
	 */
	VOLUME("v"),
	/**
	 * Example response: 100
	 */
	ASK_SIZE("a5"),
	/**
	 * Example response: 200
	 */
	BID_SIZE("b6"),
	/**
	 * Example response:
	 */
	LAST_TRADE_SIZE("k3"),
	/**
	 * Example response: 2501140
	 */
	AVERAGE_DAILY_VOLUME("a2"),
	/**
	 * Example response:
	 */
	TICKER_TREND("t7"),
	/**
	 * Example response:
	 */
	TRADE_LINKS("t6"),
	/**
	 * Example response:
	 */
	ORDER_BOOK_RT("i5"),
	/**
	 * Example response:
	 */
	HIGH_LIMIT("l2"),
	/**
	 * Example response:
	 */
	LOW_LIMIT("l3"),
	/**
	 * Example response:
	 */
	HOLDINGS_VALUE("v1"),
	/**
	 * Example response:
	 */
	HOLDINGS_VALUE_RT("v7"),
	/**
	 * Example response: 69.61B
	 */
	REVENUE("s6"),
	/**
	 * Example response: 21.22
	 */
	EARNINGS_PER_SHARE("e"),
	/**
	 * Example response: 28.88
	 */
	EPS_ESTIMATE_CURRENT_YEAR("e7"),
	/**
	 * Example response: 33.75
	 */
	EPS_ESTIMATE_NEXT_YEAR("e8"),
	/**
	 * Example response: 8.11
	 */
	EPS_ESTIMATE_NEXT_QUARTER("e9"),
	/**
	 * Example response: 163.07
	 */
	BOOK_VALUE("b4"),
	/**
	 * Example response: 22.62B
	 */
	EBITDA("j4"),
	/**
	 * Example response: 6.33
	 */
	PRICE_SALES("p5"),
	/**
	 * Example response: 3.94
	 */
	PRICE_BOOK("p6"),
	/**
	 * Example response: 29.65
	 */
	PE_RATIO("r"),
	/**
	 * Example response:
	 */
	PE_RATIO_RT("r2"),
	/**
	 * Example response: 1.22
	 */
	PEG_RATIO("r5"),
	/**
	 * Example response: 21.79
	 */
	PRICE_EPS_ESTIMATE_CURRENT_YEAR("r6"),
	/**
	 * Example response: 18.64
	 */
	PRICE_EPS_ESTIMATE_NEXT_YEAR("r7"),
	/**
	 * Example response: 1.90
	 */
	SHORT_RATIO("s7");

	private final String code;

	DataType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
