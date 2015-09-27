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
 * This class has been made to hold {@link DataType} and delay (in milliseconds) value for it needed to requests in
 * {@link Session} calls. Example values holded in object:
 * </p>
 * 
 * <p>
 * DataType: {@link DataType#LAST_TRADE_PRICE} &emsp;&emsp; Delay: 1000
 * </p>
 *
 * @author Wojciech Zankowski
 */
public class DataRequest {

	private final DataType dataType;
	private final int delay;

	/**
	 * Public constructor.
	 * 
	 * @param dataType
	 *            Data type
	 * @param delay
	 *            delay in milliseconds
	 */
	public DataRequest(DataType dataType, int delay) {
		this.dataType = dataType;
		this.delay = delay;
	}

	public DataType getDataType() {
		return dataType;
	}

	public int getDelay() {
		return delay;
	}

}
