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
 * <p>Date: 20.09.2015</p>
 *
 * <p>This class contains constants used to parsing intraday, historical and Forex data
 * downloaded from Yahoo Finance API.</p>
 *
 * @author Wojciech Zankowski
 */
public class DataConstants {

	protected static final int ENDOFDAY_HEADER = 1;
	protected static final int ENDOFDAY_DATE = 0;
	protected static final int ENDOFDAY_OPEN = 1;
	protected static final int ENDOFDAY_HIGH = 2;
	protected static final int ENDOFDAY_LOW = 3;
	protected static final int ENDOFDAY_CLOSE = 4;
	protected static final int ENDOFDAY_VOLUME = 5;
	protected static final int ENDOFDAY_ADJCLOSE = 6;
	protected static final String ENDOFDAY_DATE_FORMAT = "yyyy-MM-dd";

	protected static final int INTRADAY_HEADER = 17;
	protected static final int INTRADAY_TIMESTAMP = 0;
	protected static final int INTRADAY_CLOSE = 1;
	protected static final int INTRADAY_HIGH = 2;
	protected static final int INTRADAY_LOW = 3;
	protected static final int INTRADAY_OPEN = 4;
	protected static final int INTRADAY_VOLUME = 5;

	protected static final int FOREX_DATE = 1;
	protected static final int FOREX_TIME = 2;
	protected static final int FOREX_PRICE = 0;

	protected static final String ELEMENT_SPLITTER = ",";

}
