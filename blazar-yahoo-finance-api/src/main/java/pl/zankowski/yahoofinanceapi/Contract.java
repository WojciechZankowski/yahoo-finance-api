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
 * <p>Date: 19.09.2015</p>
 *
 * <p>This class has been created to holds Exchange and Instrument codes
 * needed for creation of Yahoo Finance API URL. For bigger user flexibility it holds
 * codes in form of Strings and not enums. However you can still use {@link YahooExchange} enum
 * with {@link YahooExchange#getCode()} method.</p>
 *
 * <p>Example values can look like:</p>
 *
 * <p> Exchange: ".L" &emsp;&emsp; Instrument: "GOOG" </p>
 *
 * @author Wojciech Zankowski
 */
public class Contract {

	private final String exchangeCode;
	private final String instrumentCode;

	public Contract(String exchange, String instrumentCode) {
		this.exchangeCode = exchange;
		this.instrumentCode = instrumentCode;
	}

	public String getExchangeCode() {
		return exchangeCode;
	}

	public String getInstrumentCode() {
		return instrumentCode;
	}

}
