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
 * @author Wojciech Zankowski Date: 20.09.2015
 */
public class MarketDataExample implements DataReceiver {

	private static Integer requestIDGenerator = -1;

	private final Map<Integer, Contract> requestMap = new HashMap<>();

	public static void main(String[] args) throws IOException, ValidationException {
		MarketDataExample marketDataExample = new MarketDataExample();
		marketDataExample.startExample();
	}

	public void startExample() {
		RequestManager requestManager = new RequestManager(5);
		Session session = SessionFactory.createNewSession(this, requestManager);

		Contract contractGOOG = new Contract(YahooExchange.NASDAQ.getCode(), "GOOG");
		Contract contractYHOO = new Contract(YahooExchange.NASDAQ.getCode(), "YHOO");

		try {
			int requestID_GOOG = getRequestID();
			requestMap.put(requestID_GOOG, contractGOOG);
			session.reqMarketData(requestID_GOOG, contractGOOG, getAllDataRequests());

			int requestID_YHOO = getRequestID();
			requestMap.put(requestID_YHOO, contractYHOO);
			session.reqMarketData(requestID_YHOO, contractYHOO, getAllDataRequests());
		} catch (RequestException e) {
			System.err.println(e.getMessage());
		}
	}

	private Integer getRequestID() {
		return ++requestIDGenerator;
	}

	private List<DataRequest> getAllDataRequests() {
		List<DataRequest> dataRequests = new ArrayList<>();
		dataRequests.add(new DataRequest(DataType.ASK, 10000));
		dataRequests.add(new DataRequest(DataType.BID, 10000));
		dataRequests.add(new DataRequest(DataType.ASK_RT, 10000));
		dataRequests.add(new DataRequest(DataType.BID_RT, 10000));
		dataRequests.add(new DataRequest(DataType.PREVIOUS_CLOSE, 3600000));
		dataRequests.add(new DataRequest(DataType.OPEN, 3600000));
		dataRequests.add(new DataRequest(DataType.DIVIDEND_YIELD, 3600000));
		dataRequests.add(new DataRequest(DataType.DIVIDEND_PER_SHARE, 3600000));
		dataRequests.add(new DataRequest(DataType.DIVIDEND_PAY_DATE, 3600000));
		dataRequests.add(new DataRequest(DataType.EX_DIVIDEND_DATE, 3600000));
		dataRequests.add(new DataRequest(DataType.CHANGE, 10000));
		dataRequests.add(new DataRequest(DataType.CHANGE_PERCENT, 10000));
		dataRequests.add(new DataRequest(DataType.CHANGE_RT, 10000));
		dataRequests.add(new DataRequest(DataType.LAST_TRADE_DATE, 10000));
		dataRequests.add(new DataRequest(DataType.TRADE_DATE, 10000));
		dataRequests.add(new DataRequest(DataType.LAST_TRADE_TIME, 10000));
		dataRequests.add(new DataRequest(DataType.AFTER_HOURS_CHANGE_RT, 1000000));
		dataRequests.add(new DataRequest(DataType.COMMISSION, 3600000));
		dataRequests.add(new DataRequest(DataType.DAYS_LOW, 3600000));
		dataRequests.add(new DataRequest(DataType.DAYS_HIGH, 3600000));
		dataRequests.add(new DataRequest(DataType.LAST_TRADE_WITH_TIME_RT, 10000));
		dataRequests.add(new DataRequest(DataType.LAST_TRADE_WITH_TIME, 10000));
		dataRequests.add(new DataRequest(DataType.LAST_TRADE_PRICE, 10000));
		dataRequests.add(new DataRequest(DataType.TARGET_PRICE_1Y, 250000));
		dataRequests.add(new DataRequest(DataType.CHANGE_200_DAY_MA, 100000));
		dataRequests.add(new DataRequest(DataType.CHANGE_PERCENT_200_DAY_MA, 100000));
		dataRequests.add(new DataRequest(DataType.MA_50_DAY, 100000));
		dataRequests.add(new DataRequest(DataType.MA_200_DAY, 100000));
		dataRequests.add(new DataRequest(DataType.DAYS_VALUE_CHANGE, 3600000));
		dataRequests.add(new DataRequest(DataType.DAYS_VALUE_CHANGE_RT, 3600000));
		dataRequests.add(new DataRequest(DataType.PRICE_PAID, 3600000));
		dataRequests.add(new DataRequest(DataType.DAYS_RANGE, 100000));
		dataRequests.add(new DataRequest(DataType.DAYS_RANGE_RT, 10000));
		dataRequests.add(new DataRequest(DataType.HOLDINGS_GAIN_PERCENT, 10000));
		dataRequests.add(new DataRequest(DataType.ANNUALIZED_GAIN, 3600000));
		dataRequests.add(new DataRequest(DataType.HOLDINGS_GAIN_PERCENT_RT, 3600000));
		dataRequests.add(new DataRequest(DataType.HOLDINGS_GAIN_RT, 3600000));
		dataRequests.add(new DataRequest(DataType.WEEK_52_HIGH, 3600000));
		dataRequests.add(new DataRequest(DataType.WEEK_52_LOW, 3600000));
		dataRequests.add(new DataRequest(DataType.CHANGE_WEEK_52_LOW, 3600000));
		dataRequests.add(new DataRequest(DataType.CHANGE_WEEK_52_HIGH, 3600000));
		dataRequests.add(new DataRequest(DataType.CHANGE_PERCENT_WEEK_52_LOW, 3600000));
		dataRequests.add(new DataRequest(DataType.CHANGE_PERCENT_WEEK_52_HIGH, 3600000));
		dataRequests.add(new DataRequest(DataType.WEEK_52_RANGE, 3600000));
		dataRequests.add(new DataRequest(DataType.SYMBOL_INFO, Integer.MAX_VALUE));
		dataRequests.add(new DataRequest(DataType.MARKET_CAPITALIZATION, 36000000));
		dataRequests.add(new DataRequest(DataType.MARKET_CAP_RT, 1000000));
		dataRequests.add(new DataRequest(DataType.FLOAT_SHARES, 1000000));
		dataRequests.add(new DataRequest(DataType.NAME, Integer.MAX_VALUE));
		dataRequests.add(new DataRequest(DataType.NOTES, Integer.MAX_VALUE));
		dataRequests.add(new DataRequest(DataType.SHARES_OWNED, Integer.MAX_VALUE));
		dataRequests.add(new DataRequest(DataType.STOCK_EXCHANGE, Integer.MAX_VALUE));
		dataRequests.add(new DataRequest(DataType.SHARES_OUTSTANDINIG, 72000000));
		dataRequests.add(new DataRequest(DataType.VOLUME, 10000));
		dataRequests.add(new DataRequest(DataType.ASK_SIZE, 10000));
		dataRequests.add(new DataRequest(DataType.BID_SIZE, 10000));
		dataRequests.add(new DataRequest(DataType.LAST_TRADE_SIZE, 10000));
		dataRequests.add(new DataRequest(DataType.AVERAGE_DAILY_VOLUME, 7200000));
		dataRequests.add(new DataRequest(DataType.TICKER_TREND, 144000000));
		dataRequests.add(new DataRequest(DataType.TRADE_LINKS, 7200000));
		dataRequests.add(new DataRequest(DataType.ORDER_BOOK_RT, 10000));
		dataRequests.add(new DataRequest(DataType.HIGH_LIMIT, 200000));
		dataRequests.add(new DataRequest(DataType.LOW_LIMIT, 200000));
		dataRequests.add(new DataRequest(DataType.HOLDINGS_VALUE, 36000000));
		dataRequests.add(new DataRequest(DataType.HOLDINGS_VALUE_RT, 100000));
		dataRequests.add(new DataRequest(DataType.REVENUE, 1440000000));
		dataRequests.add(new DataRequest(DataType.EARNINGS_PER_SHARE, 7200000));
		dataRequests.add(new DataRequest(DataType.EPS_ESTIMATE_CURRENT_YEAR, 1440000000));
		dataRequests.add(new DataRequest(DataType.EPS_ESTIMATE_NEXT_QUARTER, 144000000));
		dataRequests.add(new DataRequest(DataType.EPS_ESTIMATE_NEXT_YEAR, 144000000));
		dataRequests.add(new DataRequest(DataType.BOOK_VALUE, 100000));
		dataRequests.add(new DataRequest(DataType.EBITDA, 100000));
		dataRequests.add(new DataRequest(DataType.PRICE_SALES, 400000));
		dataRequests.add(new DataRequest(DataType.PRICE_BOOK, 4500000));
		dataRequests.add(new DataRequest(DataType.PE_RATIO, 23400400));
		dataRequests.add(new DataRequest(DataType.PE_RATIO_RT, 100000));
		dataRequests.add(new DataRequest(DataType.PEG_RATIO, 20000));
		dataRequests.add(new DataRequest(DataType.PRICE_EPS_ESTIMATE_CURRENT_YEAR, 33600000));
		dataRequests.add(new DataRequest(DataType.PRICE_EPS_ESTIMATE_NEXT_YEAR, 72000000));
		dataRequests.add(new DataRequest(DataType.SHORT_RATIO, 3600000));
		dataRequests.add(new DataRequest(DataType.CURRENCY, 0));
		return dataRequests;
	}

	@Override
	public void onStringReceived(int requestID, DataType dataType, String value) {
		Contract contract = requestMap.get(requestID);

		switch (dataType) {
		case CURRENCY:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case DIVIDEND_PAY_DATE:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case EX_DIVIDEND_DATE:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case CHANGE_PERCENT:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case CHANGE_PERCENT_RT:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case CHANGE_IN_PERCENT:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case LAST_TRADE_DATE:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case TRADE_DATE:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case LAST_TRADE_TIME:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case AFTER_HOURS_CHANGE_RT:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case COMMISSION:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case LAST_TRADE_WITH_TIME_RT:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case LAST_TRADE_WITH_TIME:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case CHANGE_PERCENT_200_DAY_MA:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case CHANGE_PERCENT_50_DAY_MA:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case DAYS_RANGE:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case DAYS_RANGE_RT:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case HOLDINGS_GAIN_PERCENT:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case ANNUALIZED_GAIN:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case HOLDINGS_GAIN:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case HOLDINGS_GAIN_PERCENT_RT:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case HOLDINGS_GAIN_RT:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case CHANGE_PERCENT_WEEK_52_LOW:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case CHANGE_PERCENT_WEEK_52_HIGH:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case WEEK_52_RANGE:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case SYMBOL_INFO:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case MARKET_CAPITALIZATION:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case MARKET_CAP_RT:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case NAME:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case NOTES:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case SYMBOL:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case SHARES_OWNED:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case STOCK_EXCHANGE:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case TICKER_TREND:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case TRADE_LINKS:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case ORDER_BOOK_RT:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case HOLDINGS_VALUE:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case HOLDINGS_VALUE_RT:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case REVENUE:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case EBITDA:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		}
	}

	@Override
	public void onDoubleReceived(int requestID, DataType dataType, double value) {
		Contract contract = requestMap.get(requestID);

		switch (dataType) {
		case ASK:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case BID:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case ASK_RT:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case BID_RT:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case PREVIOUS_CLOSE:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case OPEN:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case DIVIDEND_YIELD:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case DIVIDEND_PER_SHARE:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case CHANGE:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case CHANGE_RT:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case DAYS_LOW:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case DAYS_HIGH:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case LAST_TRADE_PRICE:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case TARGET_PRICE_1Y:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case CHANGE_200_DAY_MA:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case CHANGE_50_DAY_MA:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case MA_50_DAY:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case MA_200_DAY:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case DAYS_VALUE_CHANGE:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case DAYS_VALUE_CHANGE_RT:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case PRICE_PAID:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case WEEK_52_HIGH:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case WEEK_52_LOW:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case CHANGE_WEEK_52_LOW:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case CHANGE_WEEK_52_HIGH:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case EARNINGS_PER_SHARE:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case EPS_ESTIMATE_CURRENT_YEAR:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case EPS_ESTIMATE_NEXT_YEAR:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case EPS_ESTIMATE_NEXT_QUARTER:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case BOOK_VALUE:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case PRICE_SALES:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case PRICE_BOOK:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case PE_RATIO:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case PE_RATIO_RT:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case PEG_RATIO:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case PRICE_EPS_ESTIMATE_CURRENT_YEAR:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case PRICE_EPS_ESTIMATE_NEXT_YEAR:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case SHORT_RATIO:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		}
	}

	@Override
	public void onSizeReceived(int requestID, DataType dataType, int value) {
		Contract contract = requestMap.get(requestID);

		switch (dataType) {
		case FLOAT_SHARES:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case SHARES_OUTSTANDINIG:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case VOLUME:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case ASK_SIZE:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case BID_SIZE:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case LAST_TRADE_SIZE:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case AVERAGE_DAILY_VOLUME:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case HIGH_LIMIT:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		case LOW_LIMIT:
			System.out.println("Instrument: " + contract.getInstrumentCode() + " - Data Type: " + dataType.name()
					+ ", value: " + value);
			break;
		}
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
		// EMPTY
	}
}
