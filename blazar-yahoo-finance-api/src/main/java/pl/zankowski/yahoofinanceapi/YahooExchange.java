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
 * Date: 20.09.2015
 * </p>
 *
 * <p>
 * This enum class provides all available exchanges with codes in Yahoo Finance API.
 * </p>
 *
 * @author Wojciech Zankowski
 */
public enum YahooExchange {

	ASE(""),
	BATS(""),
	CHICAGO_BOARD(".CBT"),
	CHICAGO_MERCANTILE(".CME"),
	DOW_JONES(""),
	NASDAQ(""),
	NY_BOARD(".NYB"),
	NY_COMMODITIES(".CMX"),
	NY_MERCANTILE(".NYM"),
	NY_STOCK(""),
	OTC(".OB"),
	PINK_SHEETS(".PK"),
	SP(""),
	BUENOS_AIRES(".BA"),
	VIENNA(".VI"),
	AUSTRALIAN(".AX"),
	BRUSSELS(".BR"),
	BOVESPA(".SA"),
	TORONTO(".TO"),
	TSX_VENTURE(".V"),
	SANTIAGO(".SN"),
	SHANGHAI(".SS"),
	SHENZHEN(".SZ"),
	COPENHAGEN(".CO"),
	EURONEXT(".NX"),
	PARIS(".PA"),
	BERLIN(".BE"),
	BREMEN(".BM"),
	DUSSELDORF(".DU"),
	FRANKFURT(".F"),
	HAMBURG(".HM"),
	HANOVER(".HA"),
	MUNICH(".MU"),
	STUTTGART(".SG"),
	XETRA(".DE"),
	HONG_KONG(".HK"),
	BOMBAY(".BO"),
	NATIONAL_INDIA(".NS"),
	JAKARTA(".JK"),
	TEL_AVIV(".TA"),
	MILAN(".MI"),
	NIKKEI(""),
	MEXICO(".MX"),
	AMSTERDAM(".AS"),
	NEW_ZELAND(".NZ"),
	OSLO(".OL"),
	LISBON(".LS"),
	SINGAPORE(".SI"),
	KOREA(".KS"),
	KOSDAQ(".KQ"),
	BARCELONA(".BC"),
	BILBAO(".BI"),
	MADRID_FIXED(".MF"),
	MADRID_CATS(".MC"),
	MADRID_STOCK(".MA"),
	STOCKHOLM(".ST"),
	SWISS(".SW"),
	TAIWAN_OTC(".TWO"),
	TAIWAN(".TW"),
	FTSE(""),
	LONDON(".L");

	private final String code;

	YahooExchange(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
