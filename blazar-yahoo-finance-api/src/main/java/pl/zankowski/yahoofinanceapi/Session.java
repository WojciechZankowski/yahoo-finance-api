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

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * Date: 19.09.2015
 * </p>
 *
 * <p>
 * This class is a main class of Yahoo Finance API. It handles and process all data requests made by users.
 * </p>
 *
 * @author Wojciech Zankowski
 */
public class Session {

	private final static Logger LOGGER = Logger.getLogger(Session.class.getName());
	private final RequestManager requestManager;
	private final DataReceiver receiver;

	protected Session(DataReceiver receiver, RequestManager requestManager) {
		if (receiver == null) {
			throw new IllegalArgumentException("Illegal value. Data Receiver cannot be null.");
		}
		if (requestManager == null) {
			throw new IllegalArgumentException("Illegal value. Request Manager cannot be null.");
		}
		this.requestManager = requestManager;
		this.receiver = receiver;
	}

	/**
	 * This method handles Market Data requests to Yahoo Finance API. For every {@link DataRequest} is created task that
	 * download and parse data. If delay in {@link DataRequest} equals 0 then task is called only at the beginning and
	 * it's not scheduled for future execution.
	 * 
	 * @param requestID
	 *            Unique requestID that will let identify provided data in implementation of {@link DataReceiver}.
	 * @param contract
	 *            Contract objects containing instrument and exchange code.
	 * @param dataRequestList
	 *            List of data that has to be requested from Yahoo Finance API.
	 * @throws RequestException
	 *             Thrown when requestID is not unique.
	 */
	public void reqMarketData(int requestID, Contract contract, List<DataRequest> dataRequestList)
			throws RequestException {
		validateReqMarketData(contract, dataRequestList);
		List<ScheduledFuture<?>> scheduledTasks = requestManager.getScheduledTask(requestID);
		if (scheduledTasks == null) {
			scheduledTasks = new ArrayList<>();
			for (DataRequest dataRequest : dataRequestList) {
				try {
					if (dataRequest.getDelay() == 0) {
						RequestFactory.marketDataRequest(receiver, requestID, contract, dataRequest.getDataType());
					} else {
						ScheduledFuture<?> scheduledTask = requestManager.getScheduler().scheduleAtFixedRate(
								RequestFactory.createMarketDataRequest(receiver, requestID, contract,
										dataRequest.getDataType()), 0, dataRequest.getDelay(), TimeUnit.MILLISECONDS);
						scheduledTasks.add(scheduledTask);
					}
				} catch (IllegalArgumentException e) {
					LOGGER.log(Level.WARNING, "Couldn't schedule task. " + e.getMessage());
				}
			}
			requestManager.addScheduledTasks(requestID, scheduledTasks);
			return;
		}

		throw new RequestException("RequestID duplicate. Cannot add different request with same ID.");
	}

	/**
	 * This method handles Custom Data requests to Yahoo Finance API. It allows to create unrestricted data request from
	 * Yahoo Finance API. If delay value is equal to 0 then data is downloaded only once.
	 * 
	 * @param requestID
	 *            Unique requestID that will let identify provided data in implementation of {@link DataReceiver}
	 * @param instruments
	 *            Instruments list in format appropriate for Yahoo Finance API. It can be obtained using
	 *            {@link RequestUtils#toInstruments(Contract...)}.
	 * @param dataTypes
	 *            Data list in format appropriate for Yahoo Finance API. It can be obtained using
	 *            {@link RequestUtils#toDataTypes(DataType...)}.
	 * @param delay
	 *            Delay between scheduled task calls. If 0 then task is called only once.
	 * @throws RequestException
	 *             Thrown when requestID is not unique.
	 */
	public void reqCustomData(int requestID, String instruments, String dataTypes, int delay) throws RequestException {
		validateReqCustomData(instruments, dataTypes);
		List<ScheduledFuture<?>> scheduledTasks = requestManager.getScheduledTask(requestID);
		if (scheduledTasks == null) {
			scheduledTasks = new ArrayList<>();
			if (delay == 0) {
				RequestFactory.customDataRequest(receiver, requestID, instruments, dataTypes);
			} else {
				ScheduledFuture<?> scheduledTask = requestManager.getScheduler().scheduleAtFixedRate(
						RequestFactory.createCustomDataRequest(receiver, requestID, instruments, dataTypes), 0, delay,
						TimeUnit.MILLISECONDS);
				scheduledTasks.add(scheduledTask);
			}
			requestManager.addScheduledTasks(requestID, scheduledTasks);
			return;
		}

		throw new RequestException("RequestID duplicate. Cannot add different request with same ID.");
	}

	/**
	 * This method requests Historical Data from Yahoo Finance API and streams it to {@link DataReceiver}. It calls
	 * request with default Daily interval.
	 * 
	 * @param requestID
	 *            Unique requestID that will let identify provided data in implementation of {@link DataReceiver}
	 * @param contract
	 *            Contract objects containing instrument and exchange code.
	 * @param startDate
	 *            Start date of data.
	 * @param endDate
	 *            End date of data.
	 * @throws IOException
	 */
	public void reqHistoricalData(int requestID, Contract contract, LocalDate startDate, LocalDate endDate)
			throws IOException {
		reqHistoricalData(requestID, contract, startDate, endDate, TimePeriod.Daily);
	}

	/**
	 * This method requests Historical Data from Yahoo Finance API and streams it to {@link DataReceiver}.
	 *
	 * @param requestID
	 *            Unique requestID that will let identify provided data in implementation of {@link DataReceiver}
	 * @param contract
	 *            Contract objects containing instrument and exchange code.
	 * @param startDate
	 *            Start date of data.
	 * @param endDate
	 *            End date of data.
	 * @param timePeriod
	 *            Interval for historical data.
	 * @throws IOException
	 */
	public void reqHistoricalData(int requestID, Contract contract, LocalDate startDate, LocalDate endDate,
			TimePeriod timePeriod) throws IOException {
		validateReqHistoricalData(contract, startDate, endDate, timePeriod);
		RequestFactory.createHistoricalRequest(receiver, requestID, contract, startDate, endDate, timePeriod);
	}

	/**
	 * This method handles Intraday data requests. Because of Yahoo Finance limitations it allows to download only data
	 * from last 5 days.
	 * 
	 * @param requestID
	 *            Unique requestID that will let identify provided data in implementation of {@link DataReceiver}
	 * @param contract
	 *            Contract objects containing instrument and exchange code.
	 * @throws IOException
	 */
	public void reqIntradayData(int requestID, Contract contract) throws IOException {
		validateContract(contract);
		RequestFactory.createIntradayRequest(receiver, requestID, contract);
	}

	/**
	 * This method handles Forex data requests. If delay equals 0 then task is called only at the beginning and it's not
	 * scheduled for future execution.
	 * 
	 * @param requestID
	 *            Unique requestID that will let identify provided data in implementation of {@link DataReceiver}
	 * @param fromCurrency
	 *            First of currency pair.
	 * @param toCurrency
	 *            Second of currency pair.
	 * @param delay
	 *            Delay between scheduled task calls. If 0 then task is called only once.
	 * @throws RequestException
	 *             Thrown when requestID is not unique.
	 */
	public void reqForexData(int requestID, ForexCurrency fromCurrency, ForexCurrency toCurrency, int delay)
			throws RequestException {
		validateReqForexData(fromCurrency, toCurrency);
		List<ScheduledFuture<?>> scheduledTasks = requestManager.getScheduledTask(requestID);
		if (scheduledTasks == null) {
			scheduledTasks = new ArrayList<>();

			if (delay == 0) {
				RequestFactory.forexRequest(receiver, requestID, fromCurrency, toCurrency);
			} else {
				ScheduledFuture<?> scheduledTask = requestManager.getScheduler().scheduleAtFixedRate(
						RequestFactory.createForexRequest(receiver, requestID, fromCurrency, toCurrency), 0, delay,
						TimeUnit.MILLISECONDS);
				scheduledTasks.add(scheduledTask);
			}
			requestManager.addScheduledTasks(requestID, scheduledTasks);
			return;
		}

		throw new RequestException("RequestID duplicate. Cannot add different request with same ID.");
	}

	/**
	 * This method allows to cancel every request made in {@link Session} class.
	 * 
	 * @param requestID
	 *            Unique requestID that will let identify request made before.
	 * @throws RequestException
	 */
	public void cancelRequest(int requestID) throws RequestException {
		List<ScheduledFuture<?>> scheduledTasks = requestManager.getScheduledTask(requestID);
		if (scheduledTasks != null) {
			for (ScheduledFuture<?> scheduledTask : scheduledTasks) {
				scheduledTask.cancel(false);
			}
			requestManager.removeScheduledTasks(requestID);
			if (requestManager.isEmpty()) {
				requestManager.shutdownScheduler();
			}
			return;
		}

		throw new RequestException("RequestID missing. Cannot find given request ID.");
	}

	private void validateReqMarketData(Contract contract, List<DataRequest> dataRequestList) {
		validateContract(contract);
		if (dataRequestList == null) {
			throw new IllegalArgumentException("Illegal argument value. Data request list cannot be null.");
		}
	}

	private void validateContract(Contract contract) {
		if (contract == null) {
			throw new IllegalArgumentException("Illegal argument value. Contract cannot be null.");
		}
		if (contract.getInstrumentCode() == null) {
			throw new IllegalArgumentException("Illegal contract value. Instrument code cannot be null.");
		}
		if (contract.getExchangeCode() == null) {
			throw new IllegalArgumentException("Illegal contract value. Exchange code cannot be null.");
		}
	}

	private void validateReqCustomData(String instruments, String dataTypes) {
		if (instruments == null) {
			throw new IllegalArgumentException("Illegal argument value. Instruments cannot be null.");
		}
		if (dataTypes == null) {
			throw new IllegalArgumentException("Illegal argument value. Data types cannot be null.");
		}
	}

	private void validateReqHistoricalData(Contract contract, LocalDate startDate, LocalDate endDate,
			TimePeriod timePeriod) {
		validateContract(contract);
		if (startDate == null || endDate == null) {
			throw new IllegalArgumentException("Illegal argument value. Date cannot be null");
		}
		if (startDate.isAfter(endDate)) {
			throw new IllegalArgumentException("Illegal argument value. Start date cannot be after end date.");
		}
		if (timePeriod == null) {
			throw new IllegalArgumentException("Illegal argument value. Time period cannot be null");
		}
	}

	private void validateReqForexData(ForexCurrency fromCurrency, ForexCurrency toCurrency) {
		if (fromCurrency == null || toCurrency == null) {
			throw new IllegalArgumentException("Illegal argument value. Currency cannot be null");
		}
	}

}
