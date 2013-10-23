package com.spothero.volleyjacksondemo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HeadersReturnType {

	@JsonProperty("Accept-Language")
	public String acceptLanguage;

	@JsonProperty("Host")
	public String host;

	@JsonProperty("Referer")
	public String referrer;

	@JsonProperty("User-Agent")
	public String userAgent;

	@JsonProperty("Accept")
	public String accept;
}
