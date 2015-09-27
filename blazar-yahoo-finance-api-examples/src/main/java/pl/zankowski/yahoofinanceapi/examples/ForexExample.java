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
public class ForexExample implements DataReceiver {

	private static Integer requestIDGenerator = -1;

	private final Map<Integer, String> requestMap = new HashMap<>();

	public static void main(String[] args) throws IOException, ValidationException {
		ForexExample forexExample = new ForexExample();
		forexExample.startExample();
	}

	public void startExample() {
		RequestManager requestManager = new RequestManager(5);
		Session session = SessionFactory.createNewSession(this, requestManager);

		try {
			int requestID_USDGBP = getRequestID();
			requestMap.put(requestID_USDGBP,
					RequestUtils.convertToCurrencyPairCode(ForexCurrency.USD, ForexCurrency.GBP));
			session.reqForexData(requestID_USDGBP, ForexCurrency.USD, ForexCurrency.GBP, 10000);

			int requestID_CHFEUR = getRequestID();
			requestMap.put(requestID_CHFEUR,
					RequestUtils.convertToCurrencyPairCode(ForexCurrency.CHF, ForexCurrency.EUR));
			session.reqForexData(requestID_CHFEUR, ForexCurrency.CHF, ForexCurrency.EUR, 20000);
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
		// EMPTY
	}

	@Override
	public void onForexReceived(int requestID, long timestamp, double price) {
		String currencyPair = requestMap.get(requestID);

		System.out.println("Currency Pair: " + currencyPair + ", Timestamp: " + timestamp + ", Price: " + "" + price);
	}

}
