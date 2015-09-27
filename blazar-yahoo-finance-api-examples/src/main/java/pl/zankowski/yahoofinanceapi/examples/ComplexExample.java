package pl.zankowski.yahoofinanceapi.examples;

import pl.zankowski.yahoofinanceapi.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 20.09.2015
 *
 * @author Wojciech Zankowski
 */
public class ComplexExample {

	public static void main(String[] args) throws InterruptedException {
		Session session = SessionFactory.createNewSession(new Receiver());

		List<DataRequest> dataRequestList = new ArrayList<>();
		dataRequestList.add(new DataRequest(DataType.LAST_TRADE_PRICE, 0));
		dataRequestList.add(new DataRequest(DataType.ASK, 0));
		dataRequestList.add(new DataRequest(DataType.BID, 0));

		Contract contract = new Contract(YahooExchange.NASDAQ.getCode(), "GOOG");

		try {
			session.reqMarketData(1, contract, dataRequestList);
			session.reqHistoricalData(2, contract, LocalDate.of(2000, 2, 5), LocalDate.of(2015, 5, 5));
			session.reqIntradayData(3, contract);
			session.reqForexData(4, ForexCurrency.USD, ForexCurrency.GBP, 10000);
			session.reqCustomData(5, "GOOG", RequestUtils.toDataTypes(DataType.ASK, DataType.BID, DataType.BID_SIZE),
					5000);
		} catch (RequestException | IOException e) {
			System.err.println(e.getMessage());
		}

		Thread.sleep(60000);

		try {
			session.cancelRequest(1);
			session.cancelRequest(4);
			session.cancelRequest(5);
		} catch (RequestException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("Requests canceled");
		Thread.sleep(3000);
	}

	public static class Receiver implements DataReceiver {
		@Override
		public void onStringReceived(int requestID, DataType dataType, String value) {
			System.out.println(value);
		}

		@Override
		public void onDoubleReceived(int requestID, DataType dataType, double price) {
			System.out.println(price);
		}

		@Override
		public void onSizeReceived(int requestID, DataType dataType, int size) {
			System.out.println(size);
		}

		@Override
		public void onIntradayReceived(int requestID, long timestamp, double close, double high, double low,
				double open, int volume) {
			System.out.println(timestamp + ", close: " + close + ", high: " + high + ", low: " + low + ", open:" + open
					+ ", volume: " + volume);
		}

		@Override
		public void onHistoricalReceived(int requestID, LocalDate date, double close, double high, double low,
				double open, int volume, double adjustedClose) {
			System.out.println(date + ", close: " + close + ", high: " + high + ", low: " + low + ", open:" + open
					+ ", volume: " + volume + ", adjClose: " + adjustedClose);
		}

		@Override
		public void onCustomReceived(int requestID, List<String> value) {
			System.out.println(value);
		}

		@Override
		public void onForexReceived(int requestID, long timestamp, double price) {
			System.out.println("timestamp: " + timestamp + ", price: " + price);
		}

	}

}
