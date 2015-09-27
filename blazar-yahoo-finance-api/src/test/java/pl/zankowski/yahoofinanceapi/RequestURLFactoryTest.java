package pl.zankowski.yahoofinanceapi;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * Date: 26.09.2015
 *
 * @author Wojciech Zankowski
 */
public class RequestURLFactoryTest {

	@Test
	public void testCreateForexURL() throws MalformedURLException {
		URL createdURL = RequestURLFactory.createForexURL(ForexCurrency.USD, ForexCurrency.GBP);
		URL expectedURL = new URL("http://finance.yahoo.com/d/quotes.csv?s=USDGBP=X&f=l1d1t1");

		assertEquals(expectedURL, createdURL);
	}

	@Test
	public void testCreateStockURL() throws MalformedURLException {
		Contract contract = new Contract(YahooExchange.NASDAQ.getCode(), "GOOG");
		URL createdURL = RequestURLFactory.createStockURL(contract, DataType.LAST_TRADE_PRICE);
		URL expectedURL = new URL("http://finance.yahoo.com/d/quotes.csv?s=GOOG&f=l1");
		assertEquals(expectedURL, createdURL);

		contract = new Contract(YahooExchange.LONDON.getCode(), "GOOG");
		createdURL = RequestURLFactory.createStockURL(contract, DataType.LAST_TRADE_PRICE);
		expectedURL = new URL("http://finance.yahoo.com/d/quotes.csv?s=GOOG.L&f=l1");
		assertEquals(expectedURL, createdURL);
	}

	@Test
	public void testCreateCustomURL() throws MalformedURLException {
		Contract contractGOOG = new Contract(YahooExchange.NASDAQ.getCode(), "GOOG");
		Contract contractWD = new Contract(YahooExchange.LONDON.getCode(), "WD");
		String instruments = RequestUtils.toInstruments(contractGOOG, contractWD);
		String dataTypes = RequestUtils.toDataTypes(DataType.ASK, DataType.BID);

		URL createdURL = RequestURLFactory.createCustomURL(instruments, dataTypes);
		URL expectedURL = new URL("http://finance.yahoo.com/d/quotes.csv?s=GOOG+WD.L&f=ab");

		assertEquals(expectedURL, createdURL);
	}

	@Test
	public void testCreateEndofdayURL() throws MalformedURLException {
		Contract contractGOOG = new Contract(YahooExchange.NASDAQ.getCode(), "GOOG");
		URL createdURL = RequestURLFactory.createEndofdayURL(contractGOOG, LocalDate.of(2015, 5,
				25), LocalDate.of(2015, 6, 25), TimePeriod.Daily);
		URL expectedURL = new URL(
				"http://ichart.yahoo.com/table.csv?s=GOOG&a=4&b=25&c=2015&d=5&e=25&f=2015&g=d&ignore=.csv");

		assertEquals(expectedURL, createdURL);
	}

	@Test
	public void testCreateIntradayURL() throws MalformedURLException {
		Contract contractGOOG = new Contract(YahooExchange.NASDAQ.getCode(), "GOOG");
		URL createdURL = RequestURLFactory.createIntradayURL(contractGOOG);
		URL expectedURL = new URL("http://chartapi.finance.yahoo.com/instrument/1.1/GOOG/chartdata;type=quote;range=1d/csv");

		assertEquals(expectedURL, createdURL);
	}

}
