package pl.zankowski.yahoofinanceapi.examples;

import pl.zankowski.yahoofinanceapi.*;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 25.09.2015
 *
 * @author Wojciech Zankowski
 */
public class HistoricalDataExample implements DataReceiver {

	private static Integer requestIDGenerator = -1;

	private final Map<Integer, Contract> requestMap = new HashMap<>();

	public static void main(String[] args) throws IOException, ValidationException {
		HistoricalDataExample historicalDataExample = new HistoricalDataExample();
		historicalDataExample.startExample();
	}

	public void startExample() {
		RequestManager requestManager = new RequestManager(1);
		Session session = SessionFactory.createNewSession(this, requestManager);

		Contract contractGOOG = new Contract(YahooExchange.NASDAQ.getCode(), "GOOG");
		Contract contractYHOO = new Contract(YahooExchange.NASDAQ.getCode(), "YHOO");
		Contract contractMSFT = new Contract(YahooExchange.NASDAQ.getCode(), "MSFT");

		try {
			int requestID_GOOG = getRequestID();
			requestMap.put(requestID_GOOG, contractGOOG);
			session.reqHistoricalData(requestID_GOOG, contractGOOG, LocalDate.of(2014, 5, 5), LocalDate.of(2015, 5, 5));

			int requestID_YHOO = getRequestID();
			requestMap.put(requestID_YHOO, contractYHOO);
			session.reqHistoricalData(requestID_YHOO, contractYHOO, LocalDate.of(2014, 5, 5), LocalDate.of(2015, 5, 5),
					TimePeriod.Weekly);

			int requestID_MSFT = getRequestID();
			requestMap.put(requestID_MSFT, contractMSFT);
			session.reqHistoricalData(requestID_MSFT, contractMSFT, LocalDate.of(2014, 5, 5), LocalDate.of(2015, 5, 5),
					TimePeriod.Monthly);
		} catch (IOException e) {
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
		Contract contract = requestMap.get(requestID);

		System.out.println("Instrument: " + contract.getInstrumentCode() + ", Date: " + date + ", " + "Close "
				+ "Price: " + "" + close + ", High Price: " + high + ", Low Price: " + low + ", Open" + " Price: "
				+ open + ", " + "Volume: " + volume + "Adjusted Close: " + adjustedClose);
	}

	@Override
	public void onCustomReceived(int requestID, List<String> value) {
		// EMPTY
	}

	@Override
	public void onForexReceived(int requestID, long timestamp, double price) {
		// EMPTY
	}

}
