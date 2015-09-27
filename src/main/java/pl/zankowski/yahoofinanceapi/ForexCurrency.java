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

/**
 * <p>
 * Date: 21.09.2015
 * </p>
 *
 * <p>
 * This enum class provides all available currencies that can be downloaded from Forex Yahoo Finance API.
 * </p>
 *
 * @author Wojciech Zankowski
 */
public enum ForexCurrency {

	AUD("AUD"),
	EUR("EUR"),
	GBP("GBP"),
	JPY("JPY"),
	CAD("CAD"),
	CHF("CHF"),
	NZD("NZD"),
	ZAR("ZAR"),
	SGD("SGD"),
	RUB("RUB"),
	PLN("PLN"),
	CZK("CZK"),
	HUF("HUF"),
	MXN("MXN"),
	USD("USD"),
	KRW("KRW"),
	VND("VND"),
	BOB("BOB"),
	MOP("MOP"),
	BDT("BDT"),
	VEF("VEF"),
	GEL("GEL"),
	ISK("ISK"),
	BYR("BYR"),
	THB("THB"),
	MXV("MXV"),
	TND("TND"),
	JMD("JMD"),
	DKK("DKK"),
	SRD("SRD"),
	BWP("BWP"),
	NOK("NOK"),
	MUR("MUR"),
	AZN("AZN"),
	INR("INR"),
	MGA("MGA"),
	XAF("XAF"),
	LBP("LBP"),
	XDR("XDR"),
	IDR("IDR"),
	IEP("IEP"),
	MMK("MMK"),
	LYD("LYD"),
	IQD("IQD"),
	XPF("XPF"),
	TJS("TJS"),
	CUP("CUP"),
	UGX("UGX"),
	NGN("NGN"),
	PGK("PGK"),
	TOP("TOP"),
	TMT("TMT"),
	KES("KES"),
	CRC("CRC"),
	MZN("MZN"),
	SYP("SYP"),
	ANG("ANG"),
	ZMW("ZMW"),
	BRL("BRL"),
	BSD("BSD"),
	NIO("NIO"),
	GNF("GNF"),
	BMD("BMD"),
	SLL("SLL"),
	MKD("MKD"),
	BIF("BIF"),
	LAK("LAK"),
	BHD("BHD"),
	SHP("SHP"),
	BGN("BGN"),
	TTD("TTD"),
	SCR("SCR"),
	BBD("BBD"),
	SBD("SBD"),
	MAD("MAD"),
	GTQ("GTQ"),
	MWK("MWK"),
	PKR("PKR"),
	ITL("ITL"),
	PEN("PEN"),
	AED("AED"),
	LVL("LVL"),
	XPD("XPD"),
	UAH("UAH"),
	FRF("FRF"),
	LRD("LRD"),
	LSL("LSL"),
	SEK("SEK"),
	RON("RON"),
	XOF("XOF"),
	COP("COP"),
	CDF("CDF"),
	TZS("TZS"),
	GHS("GHS"),
	NPR("NPR"),
	ZWL("ZWL"),
	SOS("SOS"),
	DZD("DZD"),
	FKP("FKP"),
	LKR("LKR"),
	KYD("KYD"),
	CLP("CLP"),
	IRR("IRR"),
	AFN("AFN"),
	DJF("DJF"),
	SVC("SVC"),
	PYG("PYG"),
	ERN("ERN"),
	ETB("ETB"),
	ILS("ILS"),
	TWD("TWD"),
	KPW("KPW"),
	SIT("SIT"),
	GIP("GIP"),
	BND("BND"),
	HNL("HNL"),
	BZD("BZD"),
	DEM("DEM"),
	JOD("JOD"),
	RWF("RWF"),
	LTL("LTL"),
	RSD("RSD"),
	WST("WST"),
	XPT("XPT"),
	NAD("NAD"),
	PAB("PAB"),
	DOP("DOP"),
	ALL("ALL"),
	HTG("HTG"),
	AMD("AMD"),
	KMF("KMF"),
	MRO("MRO"),
	HRK("HRK"),
	ECS("ECS"),
	KHR("KHR"),
	PHP("PHP"),
	CYP("CYP"),
	KWD("KWD"),
	XCD("XCD"),
	XCP("XCP"),
	CNH("CNH"),
	SDG("SDG"),
	CLF("CLF"),
	KZT("KZT"),
	TRY("TRY"),
	FJD("FJD"),
	BAM("BAM"),
	BTN("BTN"),
	STD("STD"),
	VUV("VUV"),
	MVR("MVR"),
	AOA("AOA"),
	EGP("EGP"),
	QAR("QAR"),
	OMR("OMR"),
	CVE("CVE"),
	KGS("KGS"),
	MYR("MYR"),
	GYD("GYD"),
	SZL("SZL"),
	YER("YER"),
	SAR("SAR"),
	UYU("UYU"),
	UZS("UZS"),
	GMD("GMD"),
	AWG("AWG"),
	HKD("HKD"),
	MNT("MNT"),
	ARS("ARS"),

	GOLD_1OZ("XAU"),
	SILVER_1OZ("XAG");

	private final String code;

	ForexCurrency(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
