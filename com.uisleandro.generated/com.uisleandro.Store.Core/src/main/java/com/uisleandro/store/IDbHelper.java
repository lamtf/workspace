package com.uisleandro.store;

public interface IDbHelper {

	/*
	  Na interface eh possivel declarar variaveis publicas,
	  estaticas e finais somente.

	  Tambem é possivel declarar metodos

	*/
	String TABLE_COOKIE = "cookie";
	String COOKIE_ID = "id";
	String COOKIE_BASE_DOMAIN ="baseDomain";
	String COOKIE_NAME = "name";
	String COOKIE_VALUE ="value";
	String COOKIE_HOST ="host";
	String COOKIE_PATH = "path";
	String COOKIE_EXPIRY = "expiry";
	String COOKIE_CREATION_TIME = "creationTime";
	String COOKIE_IS_SECURE = "isSecure";
	String COOKIE_LAST_ACESSED = "lastAcessed";
	String COOKIE_IS_HTTP_ONLY = "isHttpOnly";

}
