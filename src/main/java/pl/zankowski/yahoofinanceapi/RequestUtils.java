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
 * Date: 22.09.2015
 * </p>
 *
 * <p>
 * This class provides bunch of methods facilitating making custom and forex requests -
 * {@link Session#reqCustomData(int, String, String, int)},
 * {@link Session#reqForexData(int, ForexCurrency, ForexCurrency, int)}.
 * </p>
 *
 * @author Wojciech Zankowski
 */
public class RequestUtils {

	/**
	 * This method converts all passed Data Types to one String object that fits custom and forex requests -
	 * {@link Session#reqCustomData(int, String, String, int)},
	 * {@link Session#reqForexData(int, ForexCurrency, ForexCurrency, int)}.
	 * 
	 * @param dataTypes
	 *            Data Types for conversion.
	 * @return Aggregated data types.
	 */
	public static String toDataTypes(DataType... dataTypes) {
		if (dataTypes == null) {
			throw new IllegalArgumentException("Data types cannot be null.");
		}

		StringBuilder builder = new StringBuilder();
		for (DataType dataType : dataTypes) {
			builder.append(dataType.getCode());
		}
		return builder.toString();
	}

	/**
	 * This method converts all passed Data Types to one String object that fits custom request -
	 * {@link Session#reqCustomData(int, String, String, int)}.
	 *
	 * @param contracts
	 *            Contracts for conversion.
	 * @return Aggregated instruments.
	 */
	public static String toInstruments(Contract... contracts) {
		if (contracts == null) {
			throw new IllegalArgumentException("Contracts cannot be null.");
		}

		StringBuilder builder = new StringBuilder();
		int counter = 0;
		for (Contract contract : contracts) {
			builder.append(contract.getInstrumentCode());
			builder.append(contract.getExchangeCode());
			if (counter < contracts.length - 1) {
				builder.append("+");
			}
			counter++;
		}
		return builder.toString();
	}

	/**
	 * This method converts Currency to String currency pair needed for Forex request -
	 * {@link Session#reqForexData(int, ForexCurrency, ForexCurrency, int)}.
	 * 
	 * @param fromCurrency
	 *            {@link ForexCurrency} value.
	 * @param toCurrency
	 *            {@link ForexCurrency} value.
	 * @return Converted to currency pair.
	 */
	public static String convertToCurrencyPairCode(ForexCurrency fromCurrency, ForexCurrency toCurrency) {
		if (fromCurrency == null || toCurrency == null) {
			throw new IllegalArgumentException("Currency pair cannot be null");
		}

		return fromCurrency.getCode() + toCurrency.getCode() + "=X";
	}

}
