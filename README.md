# Blazar Yahoo Finance API
Another Yahoo Finance API wrapper.

## Getting started



```java
public class DataReceiverImpl implements DataReceiver {
	@Override
	public void onStringReceived(int requestID, DataType dataType, String value) {}
	
	@Override
	public void onDoubleReceived(int requestID, DataType dataType, double value) {}
	
	@Override
	public void onSizeReceived(int requestID, DataType dataType, int value) {}
	
	@Override
	public void onIntradayReceived(int requestID, long timestamp, double close, double high, 
	                               double low, double open, int volume) {}
	
	@Override
	public void onHistoricalReceived(int requestID, LocalDate date, double close, double high, 
	                                 double low, double open, int volume, double adjustedClose) {}
	
	@Override
	public void onCustomReceived(int requestID, List<String> value) {}
	
	@Override
	public void onForexReceived(int requestID, long timestamp, double price) {}
}
```

```java
Session session = SessionFactory.createNewSession(new DataReceiverImpl());
```


```java
Session session = SessionFactory.createNewSession(new DataReceiverImpl(), new RequestManager(1));
```

## Data Requests

### Market Data Request

```java
List<DataRequest> dataRequests = new ArrayList<>();
dataRequests.add(new DataRequest(DataType.ASK, 10000));
dataRequests.add(new DataRequest(DataType.BID, 10000));
Contract contractYHOO = new Contract(YahooExchange.NASDAQ.getCode(), "YHOO");

session.reqMarketData(0, contractYHOO, dataRequests);
```

### Forex Data Request

```java
session.reqForexData(1, ForexCurrency.USD, ForexCurrency.GBP, 10000);
```

### Historical Data Request

```java
Contract contractYHOO = new Contract(YahooExchange.NASDAQ.getCode(), "YHOO");

session.reqHistoricalData(1, contractYHOO, LocalDate.of(2014, 5, 5), LocalDate.of(2015, 5, 5));
```

### Intraday Data Request

```java
Contract contractYHOO = new Contract(YahooExchange.NASDAQ.getCode(), "YHOO");

session.reqIntradayData(1, contractYHOO);
```

### Custom Data Request

```java
Contract contractYHOO = new Contract(YahooExchange.NASDAQ.getCode(), "YHOO");
String instruments = RequestUtils.toInstruments(contractYHOO);
String dataTypes = RequestUtils.toDataTypes(DataType.LAST_TRADE_DATE, DataType.LAST_TRADE_TIME,
				                                    DataType.LAST_TRADE_PRICE, DataType.LAST_TRADE_SIZE);
				                                    
session.reqCustomData(1, instruments, dataTypes, 10000);
```

### Cancel Data Request

```java
session.cancelRequest(1);
```
