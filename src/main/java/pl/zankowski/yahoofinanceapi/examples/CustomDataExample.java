package pl.zankowski.yahoofinanceapi.examples;

import pl.zankowski.yahoofinanceapi.*;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 26.09.2015
 *
 * @author Wojciech Zankowski
 */
public class CustomDataExample implements DataReceiver {

	private static Integer requestIDGenerator = -1;

	private final Map<Integer, List<Contract>> requestMap = new HashMap<>();

	public static void main(String[] args) throws IOException, ValidationException {
		CustomDataExample customDataExample = new CustomDataExample();
		customDataExample.startExample();
	}

	public void startExample() {
		RequestManager requestManager = new RequestManager(1);
		Session session = SessionFactory.createNewSession(this, requestManager);

		Contract contractGOOG = new Contract(YahooExchange.NASDAQ.getCode(), "GOOG");
		Contract contractYHOO = new Contract(YahooExchange.NASDAQ.getCode(), "YHOO");
		Contract contractMSFT = new Contract(YahooExchange.NASDAQ.getCode(), "MSFT");

		List<Contract> contracts = new ArrayList<>();
		contracts.add(contractGOOG);
		contracts.add(contractYHOO);
		contracts.add(contractMSFT);

		try {
			int requestID_Custom = getRequestID();
			requestMap.put(requestID_Custom, contracts);
			String instruments = RequestUtils.toInstruments(contractGOOG, contractYHOO, contractMSFT);
			String dataTypes = RequestUtils.toDataTypes(DataType.LAST_TRADE_DATE, DataType.LAST_TRADE_TIME,
					DataType.LAST_TRADE_PRICE, DataType.LAST_TRADE_SIZE);
			session.reqCustomData(requestID_Custom, instruments, dataTypes, 10000);
		} catch (RequestException e) {
			System.err.println(e.getMessage());
		}
	}

	private Integer getRequestID() {
		return ++requestIDGenerator;
	}

	@Override
	public void onStringReceived(int requestID, DataType dataType, String value) {
		// EMPTY
	}

	@Override
	public void onDoubleReceived(int requestID, DataType dataType, double value) {
		// EMPTY
	}

	@Override
	public void onSizeReceived(int requestID, DataType dataType, int value) {
		// EMPTY
	}

	@Override
	public void onIntradayReceived(int requestID, long timestamp, double close, double high, double low, double open,
			int volume) {
		// EMPTY
	}

	@Override
	public void onHistoricalReceived(int requestID, LocalDate date, double close, double high, double low, double open,
			int volume, double adjustedClose) {
		// EMPTY
	}

	@Override
	public void onCustomReceived(int requestID, List<String> value) {
		List<Contract> contracts = requestMap.get(requestID);

		for (int i = 0; i < value.size(); i++) {
			System.out.println(contracts.get(i).getInstrumentCode() + " - " + value.get(i));
		}
	}

	@Override
	public void onForexReceived(int requestID, long timestamp, double price) {
		// EMPTY
	}
}
