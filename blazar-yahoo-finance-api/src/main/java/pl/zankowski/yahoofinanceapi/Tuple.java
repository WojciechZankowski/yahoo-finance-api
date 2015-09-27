package pl.zankowski.yahoofinanceapi;

/**
 * <p>
 * Date: 25.09.2015
 * </p>
 * 
 * <p>
 * Simple Tuple class that helps provided converted data in {@link ParseUtils}
 * </p>
 *
 * @author Wojciech Zankowski
 */
public class Tuple<T, K> {

	private final T firstValue;
	private final K secondValue;

	public Tuple(T firstValue, K secondValue) {
		this.firstValue = firstValue;
		this.secondValue = secondValue;
	}

	public T getFirstValue() {
		return firstValue;
	}

	public K getSecondValue() {
		return secondValue;
	}
}
