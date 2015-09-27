package pl.zankowski.yahoofinanceapi;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Date: 26.09.2015
 *
 * @author Wojciech Zankowski
 */
public class RequestUtilsTest {

	@Test
	public void testToInstruments() {
		Contract contractGOOG = new Contract(YahooExchange.NASDAQ.getCode(), "GOOG");
		Contract contractYHOO = new Contract(YahooExchange.BATS.getCode(), "YHOO");
		Contract contractWD = new Contract(YahooExchange.LONDON.getCode(), "WD");

		toInstruments("", new Contract[] {});
		toInstruments("GOOG", contractGOOG);
		toInstruments("GOOG+YHOO", contractGOOG, contractYHOO);
		toInstruments("GOOG+WD.L", contractGOOG, contractWD);
		toInstruments("YHOO+GOOG+WD.L", contractYHOO, contractGOOG, contractWD);
	}

	private void toInstruments(String expectedValue, Contract... contracts) {
		String createdValue = RequestUtils.toInstruments(contracts);
		assertEquals(expectedValue, createdValue);
	}

	@Test
	public void testToDataTypes() {
		toDataTypes("", new DataType[] {});
		toDataTypes("l1", DataType.LAST_TRADE_PRICE);
		toDataTypes("l1d1", DataType.LAST_TRADE_PRICE, DataType.LAST_TRADE_DATE);
		toDataTypes("l1d1t1", DataType.LAST_TRADE_PRICE, DataType.LAST_TRADE_DATE, DataType.LAST_TRADE_TIME);
	}

	private void toDataTypes(String expectedValue, DataType... dataTypes) {
		String createdValue = RequestUtils.toDataTypes(dataTypes);
		assertEquals(expectedValue, createdValue);
	}

	@Test
	public void testConvertToCurrencyPairCode() {
		String createdPair = RequestUtils.convertToCurrencyPairCode(ForexCurrency.USD, ForexCurrency.GBP);
		assertEquals("USDGBP=X", createdPair);
	}

}
