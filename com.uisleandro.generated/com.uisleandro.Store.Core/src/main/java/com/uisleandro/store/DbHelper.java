package com.uisleandro.store;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*

if i have got a remote_id,
	use the remote_id
else
	use the local_id

i can maybe keep translation tables

if i got the id on the translation table
it means that my local data is updated

if i dont have the id on the translation table
sent id to the server

so i can keep the internal relations
without dealing with external relations

the server should create the relations.. 1 by 1
but not necessarially keep the relations, which are not
necessary for the server 

1. don't keep local data (system.. etc)
2. keep a local data only for situations with no internet (adding clients..)
3. keep local data


1. don't send data
2. send when the data comes to a given number of rows

TODO: on the server, translate from user_id to system_id
or translate from token_id to system_id

*/


/*
	Better one for each module??
*/
public class DbHelper extends SQLiteOpenHelper {

	private static DbHelper db_instance = null;
	private Context context;

	private static final String DATABASE_NAME = "store.db";
	private static final int DATABASE_VERSION = 1;

// Start of user code cookies

	public static final String TABLE_UPDATE_HISTORY = "update_history";
	public static final String UPDATE_HISTORY_TABLE_NAME = "table_name";
		public static final String UPDATE_HISTORY_LAST_UPDATE_TIME = "last_update_time";

	public static final String CREATE_TABLE_UPDATE_HISTORY = "CREATE TABLE IF NOT EXISTS " + TABLE_UPDATE_HISTORY + " (" +
			UPDATE_HISTORY_TABLE_NAME + " CHAR(30) NOT NULL, "+
			UPDATE_HISTORY_LAST_UPDATE_TIME + " INTEGER  NULL " +
			" );";

	private static final String CREATE_INDEX_UPDATE_HISTORY_TNAME = "CREATE UNIQUE INDEX " + TABLE_UPDATE_HISTORY + "_serverid_idx" +
			" ON " + TABLE_UPDATE_HISTORY + " ("+ UPDATE_HISTORY_TABLE_NAME +");";


	public static final String TABLE_COOKIE = "cookie";
	public static final String COOKIE_ID = "id";
	public static final String COOKIE_BASE_DOMAIN ="baseDomain";
	public static final String COOKIE_NAME = "name";
	public static final String COOKIE_VALUE ="value";
	public static final String COOKIE_HOST ="host";
	public static final String COOKIE_PATH = "path";
	public static final String COOKIE_EXPIRY = "expiry";
	public static final String COOKIE_CREATION_TIME = "creationTime";
	public static final String COOKIE_IS_SECURE = "isSecure";
	public static final String COOKIE_LAST_ACESSED = "lastAcessed";
	public static final String COOKIE_IS_HTTP_ONLY = "isHttpOnly";

	private static final String CREATE_TABLE_COOKIE = "CREATE TABLE IF NOT EXISTS "+TABLE_COOKIE+" ("+
		COOKIE_ID + " INTEGER PRIMARY KEY ASC AUTOINCREMENT,"+
		COOKIE_BASE_DOMAIN + " TEXT,"+
		COOKIE_NAME + " TEXT,"+
		COOKIE_VALUE + " TEXT,"+
		COOKIE_HOST + " TEXT,"+
		COOKIE_PATH + " TEXT,"+
		COOKIE_EXPIRY + " INTEGER,"+
		COOKIE_CREATION_TIME + " INTEGER,"+
		COOKIE_IS_SECURE + " BOOLEAN,"+
		COOKIE_LAST_ACESSED + " INTEGER,"+
		COOKIE_IS_HTTP_ONLY +" BOOLEAN" +
	");";
//End of user code


//TODO: controlar a versao do modulo


// cash

	public static final String TABLE_CASH_REGISTER = "cash_cash_register";
	public static final String CASH_REGISTER_ID = "id";
	public static final String CASH_REGISTER_SERVER_ID = "server_id";
	public static final String CASH_REGISTER_DIRTY = "dirty";
	public static final String CASH_REGISTER_LAST_UPDATE = "last_update";
	public static final String CASH_REGISTER_FK_USER = "fk_user";
	public static final String CASH_REGISTER_OPENING_VALUE = "opening_value";
	public static final String CASH_REGISTER_RECEIVED_VALUE = "received_value";
	public static final String CASH_REGISTER_CLOSING_VALUE = "closing_value";

	private static final String CREATE_TABLE_CASH_REGISTER = "CREATE TABLE " + 
	TABLE_CASH_REGISTER + " ("+ 
	CASH_REGISTER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	CASH_REGISTER_SERVER_ID + " INTEGER NULL, " +
	CASH_REGISTER_DIRTY + " BOOLEAN NOT NULL, " +
	CASH_REGISTER_LAST_UPDATE + " INTEGER NULL, " + 
	CASH_REGISTER_FK_USER + " INTEGER NULL, " + 
	CASH_REGISTER_OPENING_VALUE + " REAL(10,2) NOT NULL, " + 
	CASH_REGISTER_RECEIVED_VALUE + " REAL(10,2) NOT NULL, " + 
	CASH_REGISTER_CLOSING_VALUE + " REAL(10,2) NOT NULL " + 
	");";

	private static final String CREATE_INDEX_CASH_REGISTER_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_CASH_REGISTER + "_serverid_idx" +
	" ON " + TABLE_CASH_REGISTER + " ("+ CASH_REGISTER_SERVER_ID +")"; 


	public static final String TABLE_CASH_LAUNCH = "cash_cash_launch";
	public static final String CASH_LAUNCH_ID = "id";
	public static final String CASH_LAUNCH_SERVER_ID = "server_id";
	public static final String CASH_LAUNCH_DIRTY = "dirty";
	public static final String CASH_LAUNCH_LAST_UPDATE = "last_update";
	public static final String CASH_LAUNCH_FK_CASH_REGISTER = "fk_cash_register";
	public static final String CASH_LAUNCH_JUSTIFICATION = "justification";
	public static final String CASH_LAUNCH_AMOUNT_SPENT = "amount_spent";

	private static final String CREATE_TABLE_CASH_LAUNCH = "CREATE TABLE " + 
	TABLE_CASH_LAUNCH + " ("+ 
	CASH_LAUNCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	CASH_LAUNCH_SERVER_ID + " INTEGER NULL, " +
	CASH_LAUNCH_DIRTY + " BOOLEAN NOT NULL, " +
	CASH_LAUNCH_LAST_UPDATE + " INTEGER NULL, " + 
	CASH_LAUNCH_FK_CASH_REGISTER + " INTEGER NULL, " + 
	CASH_LAUNCH_JUSTIFICATION + " CHAR(30) NOT NULL, " + 
	CASH_LAUNCH_AMOUNT_SPENT + " REAL(10,2) NOT NULL " + 
	");";

	private static final String CREATE_INDEX_CASH_LAUNCH_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_CASH_LAUNCH + "_serverid_idx" +
	" ON " + TABLE_CASH_LAUNCH + " ("+ CASH_LAUNCH_SERVER_ID +")"; 

// supply

	public static final String TABLE_STOCK_REVIEW = "supply_stock_review";
	public static final String STOCK_REVIEW_ID = "id";
	public static final String STOCK_REVIEW_SERVER_ID = "server_id";
	public static final String STOCK_REVIEW_DIRTY = "dirty";
	public static final String STOCK_REVIEW_LAST_UPDATE = "last_update";
	public static final String STOCK_REVIEW_FK_PRODUCT = "fk_product";
	public static final String STOCK_REVIEW_ACTUAL_AMOUNT = "actual_amount";
	public static final String STOCK_REVIEW_SOLD_ITEMS = "sold_items";
	public static final String STOCK_REVIEW_PREVIOUS_AMOUNT = "previous_amount";
	public static final String STOCK_REVIEW_MISSING_AMOUNT = "missing_amount";

	private static final String CREATE_TABLE_STOCK_REVIEW = "CREATE TABLE " + 
	TABLE_STOCK_REVIEW + " ("+ 
	STOCK_REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	STOCK_REVIEW_SERVER_ID + " INTEGER NULL, " +
	STOCK_REVIEW_DIRTY + " BOOLEAN NOT NULL, " +
	STOCK_REVIEW_LAST_UPDATE + " INTEGER NULL, " + 
	STOCK_REVIEW_FK_PRODUCT + " INTEGER NULL, " + 
	STOCK_REVIEW_ACTUAL_AMOUNT + " INTEGER NOT NULL, " + 
	STOCK_REVIEW_SOLD_ITEMS + " INTEGER NOT NULL, " + 
	STOCK_REVIEW_PREVIOUS_AMOUNT + " INTEGER NOT NULL, " + 
	STOCK_REVIEW_MISSING_AMOUNT + " INTEGER NOT NULL " + 
	");";

	private static final String CREATE_INDEX_STOCK_REVIEW_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_STOCK_REVIEW + "_serverid_idx" +
	" ON " + TABLE_STOCK_REVIEW + " ("+ STOCK_REVIEW_SERVER_ID +")"; 


	public static final String TABLE_UNIT = "supply_unit";
	public static final String UNIT_ID = "id";
	public static final String UNIT_SERVER_ID = "server_id";
	public static final String UNIT_DIRTY = "dirty";
	public static final String UNIT_LAST_UPDATE = "last_update";
	public static final String UNIT_NAME = "name";

	private static final String CREATE_TABLE_UNIT = "CREATE TABLE " + 
	TABLE_UNIT + " ("+ 
	UNIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	UNIT_SERVER_ID + " INTEGER NULL, " +
	UNIT_DIRTY + " BOOLEAN NOT NULL, " +
	UNIT_LAST_UPDATE + " INTEGER NULL, " + 
	UNIT_NAME + " CHAR(30) NOT NULL " + 
	");";

	private static final String CREATE_INDEX_UNIT_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_UNIT + "_serverid_idx" +
	" ON " + TABLE_UNIT + " ("+ UNIT_SERVER_ID +")"; 


	public static final String TABLE_CATEGORY = "supply_category";
	public static final String CATEGORY_ID = "id";
	public static final String CATEGORY_SERVER_ID = "server_id";
	public static final String CATEGORY_DIRTY = "dirty";
	public static final String CATEGORY_LAST_UPDATE = "last_update";
	public static final String CATEGORY_FK_CATEGORY = "fk_category";
	public static final String CATEGORY_NAME = "name";

	private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + 
	TABLE_CATEGORY + " ("+ 
	CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	CATEGORY_SERVER_ID + " INTEGER NULL, " +
	CATEGORY_DIRTY + " BOOLEAN NOT NULL, " +
	CATEGORY_LAST_UPDATE + " INTEGER NULL, " + 
	CATEGORY_FK_CATEGORY + " INTEGER NULL, " + 
	CATEGORY_NAME + " CHAR(30) NOT NULL " + 
	");";

	private static final String CREATE_INDEX_CATEGORY_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_CATEGORY + "_serverid_idx" +
	" ON " + TABLE_CATEGORY + " ("+ CATEGORY_SERVER_ID +")"; 


	public static final String TABLE_BRAND = "supply_brand";
	public static final String BRAND_ID = "id";
	public static final String BRAND_SERVER_ID = "server_id";
	public static final String BRAND_DIRTY = "dirty";
	public static final String BRAND_LAST_UPDATE = "last_update";
	public static final String BRAND_COMPANY_NAME = "company_name";
	public static final String BRAND_FANTASY_NAME = "fantasy_name";

	private static final String CREATE_TABLE_BRAND = "CREATE TABLE " + 
	TABLE_BRAND + " ("+ 
	BRAND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	BRAND_SERVER_ID + " INTEGER NULL, " +
	BRAND_DIRTY + " BOOLEAN NOT NULL, " +
	BRAND_LAST_UPDATE + " INTEGER NULL, " + 
	BRAND_COMPANY_NAME + " CHAR(30) NOT NULL, " + 
	BRAND_FANTASY_NAME + " CHAR(30) NOT NULL " + 
	");";

	private static final String CREATE_INDEX_BRAND_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_BRAND + "_serverid_idx" +
	" ON " + TABLE_BRAND + " ("+ BRAND_SERVER_ID +")"; 


	public static final String TABLE_GENDER = "supply_gender";
	public static final String GENDER_ID = "id";
	public static final String GENDER_SERVER_ID = "server_id";
	public static final String GENDER_DIRTY = "dirty";
	public static final String GENDER_LAST_UPDATE = "last_update";
	public static final String GENDER_NAME = "name";

	private static final String CREATE_TABLE_GENDER = "CREATE TABLE " + 
	TABLE_GENDER + " ("+ 
	GENDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	GENDER_SERVER_ID + " INTEGER NULL, " +
	GENDER_DIRTY + " BOOLEAN NOT NULL, " +
	GENDER_LAST_UPDATE + " INTEGER NULL, " + 
	GENDER_NAME + " CHAR(30) NOT NULL " + 
	");";

	private static final String CREATE_INDEX_GENDER_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_GENDER + "_serverid_idx" +
	" ON " + TABLE_GENDER + " ("+ GENDER_SERVER_ID +")"; 


	public static final String TABLE_PRODUCT = "supply_product";
	public static final String PRODUCT_ID = "id";
	public static final String PRODUCT_SERVER_ID = "server_id";
	public static final String PRODUCT_DIRTY = "dirty";
	public static final String PRODUCT_LAST_UPDATE = "last_update";
	public static final String PRODUCT_FK_SYSTEM = "fk_system";
	public static final String PRODUCT_BARCODE = "barcode";
	public static final String PRODUCT_DESCRIPTION = "description";
	public static final String PRODUCT_AMOUNT = "amount";
	public static final String PRODUCT_FK_GENDER = "fk_gender";
	public static final String PRODUCT_PURCHASE_PRICE = "purchase_price";
	public static final String PRODUCT_SALE_PRICE = "sale_price";
	public static final String PRODUCT_FK_CATEGORY = "fk_category";
	public static final String PRODUCT_SIZE = "size";
	public static final String PRODUCT_FK_UNIT = "fk_unit";
	public static final String PRODUCT_EXPIRATION_DATE = "expiration_date";
	public static final String PRODUCT_FK_BRAND = "fk_brand";

	private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE " + 
	TABLE_PRODUCT + " ("+ 
	PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	PRODUCT_SERVER_ID + " INTEGER NULL, " +
	PRODUCT_DIRTY + " BOOLEAN NOT NULL, " +
	PRODUCT_LAST_UPDATE + " INTEGER NULL, " + 
	PRODUCT_FK_SYSTEM + " INTEGER NULL, " + 
	PRODUCT_BARCODE + " CHAR(64) NOT NULL, " + 
	PRODUCT_DESCRIPTION + " VARCHAR(45) NOT NULL, " + 
	PRODUCT_AMOUNT + " INTEGER NOT NULL, " + 
	PRODUCT_FK_GENDER + " INTEGER NULL, " + 
	PRODUCT_PURCHASE_PRICE + " REAL(10,2) NOT NULL, " + 
	PRODUCT_SALE_PRICE + " REAL(10,2) NOT NULL, " + 
	PRODUCT_FK_CATEGORY + " INTEGER NULL, " + 
	PRODUCT_SIZE + " CHAR(30) NOT NULL, " + 
	PRODUCT_FK_UNIT + " INTEGER NULL, " + 
	PRODUCT_EXPIRATION_DATE + " INTEGER NOT NULL, " + 
	PRODUCT_FK_BRAND + " INTEGER NULL " + 
	");";

	private static final String CREATE_INDEX_PRODUCT_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_PRODUCT + "_serverid_idx" +
	" ON " + TABLE_PRODUCT + " ("+ PRODUCT_SERVER_ID +")"; 


	public static final String TABLE_DISTRIBUTOR_CONTACT = "supply_distributor_contact";
	public static final String DISTRIBUTOR_CONTACT_ID = "id";
	public static final String DISTRIBUTOR_CONTACT_SERVER_ID = "server_id";
	public static final String DISTRIBUTOR_CONTACT_DIRTY = "dirty";
	public static final String DISTRIBUTOR_CONTACT_LAST_UPDATE = "last_update";
	public static final String DISTRIBUTOR_CONTACT_NAME = "name";
	public static final String DISTRIBUTOR_CONTACT_EMAIL1 = "email1";
	public static final String DISTRIBUTOR_CONTACT_EMAIL2 = "email2";
	public static final String DISTRIBUTOR_CONTACT_PHONE_NUMBER1 = "phone_number1";
	public static final String DISTRIBUTOR_CONTACT_PHONE_NUMBER2 = "phone_number2";
	public static final String DISTRIBUTOR_CONTACT_PHONE_NUMBER3 = "phone_number3";
	public static final String DISTRIBUTOR_CONTACT_PHONE_NUMBER4 = "phone_number4";
	public static final String DISTRIBUTOR_CONTACT_FK_BRAND = "fk_brand";

	private static final String CREATE_TABLE_DISTRIBUTOR_CONTACT = "CREATE TABLE " + 
	TABLE_DISTRIBUTOR_CONTACT + " ("+ 
	DISTRIBUTOR_CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	DISTRIBUTOR_CONTACT_SERVER_ID + " INTEGER NULL, " +
	DISTRIBUTOR_CONTACT_DIRTY + " BOOLEAN NOT NULL, " +
	DISTRIBUTOR_CONTACT_LAST_UPDATE + " INTEGER NULL, " + 
	DISTRIBUTOR_CONTACT_NAME + " CHAR(30) NOT NULL, " + 
	DISTRIBUTOR_CONTACT_EMAIL1 + " CHAR(30) NOT NULL, " + 
	DISTRIBUTOR_CONTACT_EMAIL2 + " CHAR(30) NOT NULL, " + 
	DISTRIBUTOR_CONTACT_PHONE_NUMBER1 + " CHAR(30) NOT NULL, " + 
	DISTRIBUTOR_CONTACT_PHONE_NUMBER2 + " CHAR(30) NOT NULL, " + 
	DISTRIBUTOR_CONTACT_PHONE_NUMBER3 + " CHAR(30) NOT NULL, " + 
	DISTRIBUTOR_CONTACT_PHONE_NUMBER4 + " CHAR(30) NOT NULL, " + 
	DISTRIBUTOR_CONTACT_FK_BRAND + " INTEGER NULL " + 
	");";

	private static final String CREATE_INDEX_DISTRIBUTOR_CONTACT_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_DISTRIBUTOR_CONTACT + "_serverid_idx" +
	" ON " + TABLE_DISTRIBUTOR_CONTACT + " ("+ DISTRIBUTOR_CONTACT_SERVER_ID +")"; 

// credit_protection

	public static final String TABLE_ISSUE = "credit_protection_Issue";
	public static final String ISSUE_ID = "id";
	public static final String ISSUE_SERVER_ID = "server_id";
	public static final String ISSUE_DIRTY = "dirty";
	public static final String ISSUE_LAST_UPDATE = "last_update";
	public static final String ISSUE_FK_SHARED_CLIENT = "fk_shared_client";
	public static final String ISSUE_FK_SYSTEM = "fk_system";
	public static final String ISSUE_DESCRIPTION = "description";
	public static final String ISSUE_ACTIVE = "active";
	public static final String ISSUE_ISANSWER = "isAnswer";
	public static final String ISSUE_FK_ISSUE = "fk_issue";

	private static final String CREATE_TABLE_ISSUE = "CREATE TABLE " + 
	TABLE_ISSUE + " ("+ 
	ISSUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	ISSUE_SERVER_ID + " INTEGER NULL, " +
	ISSUE_DIRTY + " BOOLEAN NOT NULL, " +
	ISSUE_LAST_UPDATE + " INTEGER NULL, " + 
	ISSUE_FK_SHARED_CLIENT + " INTEGER NULL, " + 
	ISSUE_FK_SYSTEM + " INTEGER NULL, " + 
	ISSUE_DESCRIPTION + " VARCHAR(45) NOT NULL, " + 
	ISSUE_ACTIVE + " BOOLEAN NOT NULL, " + 
	ISSUE_ISANSWER + " BOOLEAN NOT NULL, " + 
	ISSUE_FK_ISSUE + " INTEGER NULL " + 
	");";

	private static final String CREATE_INDEX_ISSUE_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_ISSUE + "_serverid_idx" +
	" ON " + TABLE_ISSUE + " ("+ ISSUE_SERVER_ID +")"; 


	public static final String TABLE_SHARED_CLIENT = "credit_protection_shared_client";
	public static final String SHARED_CLIENT_ID = "id";
	public static final String SHARED_CLIENT_SERVER_ID = "server_id";
	public static final String SHARED_CLIENT_DIRTY = "dirty";
	public static final String SHARED_CLIENT_LAST_UPDATE = "last_update";
	public static final String SHARED_CLIENT_NAME = "name";
	public static final String SHARED_CLIENT_BIRTH_DATE = "birth_date";
	public static final String SHARED_CLIENT_BIRTH_CITY = "birth_city";
	public static final String SHARED_CLIENT_BIRTH_STATE = "birth_state";
	public static final String SHARED_CLIENT_MOTHERS_NAME = "mothers_name";
	public static final String SHARED_CLIENT_FATHERS_NAME = "fathers_name";
	public static final String SHARED_CLIENT_PROFESSION = "profession";
	public static final String SHARED_CLIENT_ZIP_CODE = "zip_code";
	public static final String SHARED_CLIENT_ADDRESS = "address";
	public static final String SHARED_CLIENT_NEIGHBORHOOD = "neighborhood";
	public static final String SHARED_CLIENT_CITY = "city";
	public static final String SHARED_CLIENT_STATE = "state";
	public static final String SHARED_CLIENT_COMPLEMENT = "complement";
	public static final String SHARED_CLIENT_FK_COUNTRY = "fk_country";

	private static final String CREATE_TABLE_SHARED_CLIENT = "CREATE TABLE " + 
	TABLE_SHARED_CLIENT + " ("+ 
	SHARED_CLIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	SHARED_CLIENT_SERVER_ID + " INTEGER NULL, " +
	SHARED_CLIENT_DIRTY + " BOOLEAN NOT NULL, " +
	SHARED_CLIENT_LAST_UPDATE + " INTEGER NULL, " + 
	SHARED_CLIENT_NAME + " CHAR(30) NOT NULL, " + 
	SHARED_CLIENT_BIRTH_DATE + " INTEGER NOT NULL, " + 
	SHARED_CLIENT_BIRTH_CITY + " CHAR(30) NOT NULL, " + 
	SHARED_CLIENT_BIRTH_STATE + " CHAR(30) NOT NULL, " + 
	SHARED_CLIENT_MOTHERS_NAME + " CHAR(30) NOT NULL, " + 
	SHARED_CLIENT_FATHERS_NAME + " CHAR(30) NOT NULL, " + 
	SHARED_CLIENT_PROFESSION + " CHAR(30) NOT NULL, " + 
	SHARED_CLIENT_ZIP_CODE + " CHAR(15) NOT NULL, " + 
	SHARED_CLIENT_ADDRESS + " CHAR(30) NOT NULL, " + 
	SHARED_CLIENT_NEIGHBORHOOD + " CHAR(30) NOT NULL, " + 
	SHARED_CLIENT_CITY + " CHAR(30) NOT NULL, " + 
	SHARED_CLIENT_STATE + " CHAR(2) NOT NULL, " + 
	SHARED_CLIENT_COMPLEMENT + " VARCHAR(45) NOT NULL, " + 
	SHARED_CLIENT_FK_COUNTRY + " INTEGER NULL " + 
	");";

	private static final String CREATE_INDEX_SHARED_CLIENT_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_SHARED_CLIENT + "_serverid_idx" +
	" ON " + TABLE_SHARED_CLIENT + " ("+ SHARED_CLIENT_SERVER_ID +")"; 

// sales

	public static final String TABLE_PRODUCT_ON_SALE = "sales_product_on_sale";
	public static final String PRODUCT_ON_SALE_ID = "id";
	public static final String PRODUCT_ON_SALE_SERVER_ID = "server_id";
	public static final String PRODUCT_ON_SALE_DIRTY = "dirty";
	public static final String PRODUCT_ON_SALE_LAST_UPDATE = "last_update";
	public static final String PRODUCT_ON_SALE_FK_SALE = "fk_sale";
	public static final String PRODUCT_ON_SALE_FK_PRODUCT = "fk_product";

	private static final String CREATE_TABLE_PRODUCT_ON_SALE = "CREATE TABLE " + 
	TABLE_PRODUCT_ON_SALE + " ("+ 
	PRODUCT_ON_SALE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	PRODUCT_ON_SALE_SERVER_ID + " INTEGER NULL, " +
	PRODUCT_ON_SALE_DIRTY + " BOOLEAN NOT NULL, " +
	PRODUCT_ON_SALE_LAST_UPDATE + " INTEGER NULL, " + 
	PRODUCT_ON_SALE_FK_SALE + " INTEGER NULL, " + 
	PRODUCT_ON_SALE_FK_PRODUCT + " INTEGER NULL " + 
	");";

	private static final String CREATE_INDEX_PRODUCT_ON_SALE_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_PRODUCT_ON_SALE + "_serverid_idx" +
	" ON " + TABLE_PRODUCT_ON_SALE + " ("+ PRODUCT_ON_SALE_SERVER_ID +")"; 


	public static final String TABLE_SALE_TYPE = "sales_sale_type";
	public static final String SALE_TYPE_ID = "id";
	public static final String SALE_TYPE_SERVER_ID = "server_id";
	public static final String SALE_TYPE_DIRTY = "dirty";
	public static final String SALE_TYPE_LAST_UPDATE = "last_update";
	public static final String SALE_TYPE_NAME = "name";

	private static final String CREATE_TABLE_SALE_TYPE = "CREATE TABLE " + 
	TABLE_SALE_TYPE + " ("+ 
	SALE_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	SALE_TYPE_SERVER_ID + " INTEGER NULL, " +
	SALE_TYPE_DIRTY + " BOOLEAN NOT NULL, " +
	SALE_TYPE_LAST_UPDATE + " INTEGER NULL, " + 
	SALE_TYPE_NAME + " CHAR(30) NOT NULL " + 
	");";

	private static final String CREATE_INDEX_SALE_TYPE_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_SALE_TYPE + "_serverid_idx" +
	" ON " + TABLE_SALE_TYPE + " ("+ SALE_TYPE_SERVER_ID +")"; 


	public static final String TABLE_SALE = "sales_sale";
	public static final String SALE_ID = "id";
	public static final String SALE_SERVER_ID = "server_id";
	public static final String SALE_DIRTY = "dirty";
	public static final String SALE_LAST_UPDATE = "last_update";
	public static final String SALE_FK_SALE_TYPE = "fk_sale_type";
	public static final String SALE_FK_SYSTEM = "fk_system";
	public static final String SALE_TOTAL_VALUE = "total_value";
	public static final String SALE_FK_USER = "fk_user";
	public static final String SALE_FK_CLIENT_FROM_SYSTEM = "fk_client_from_system";

	private static final String CREATE_TABLE_SALE = "CREATE TABLE " + 
	TABLE_SALE + " ("+ 
	SALE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	SALE_SERVER_ID + " INTEGER NULL, " +
	SALE_DIRTY + " BOOLEAN NOT NULL, " +
	SALE_LAST_UPDATE + " INTEGER NULL, " + 
	SALE_FK_SALE_TYPE + " INTEGER NULL, " + 
	SALE_FK_SYSTEM + " INTEGER NULL, " + 
	SALE_TOTAL_VALUE + " REAL(10,2) NOT NULL, " + 
	SALE_FK_USER + " INTEGER NULL, " + 
	SALE_FK_CLIENT_FROM_SYSTEM + " INTEGER NULL " + 
	");";

	private static final String CREATE_INDEX_SALE_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_SALE + "_serverid_idx" +
	" ON " + TABLE_SALE + " ("+ SALE_SERVER_ID +")"; 

// core

	public static final String TABLE_DB_LOG = "core_db_log";
	public static final String DB_LOG_ID = "id";
	public static final String DB_LOG_SERVER_ID = "server_id";
	public static final String DB_LOG_DIRTY = "dirty";
	public static final String DB_LOG_LAST_UPDATE = "last_update";
	public static final String DB_LOG_ACTION_NAME = "action_name";
	public static final String DB_LOG_PARAMETER = "parameter";
	public static final String DB_LOG_FK_USER = "fk_user";

	private static final String CREATE_TABLE_DB_LOG = "CREATE TABLE " + 
	TABLE_DB_LOG + " ("+ 
	DB_LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	DB_LOG_SERVER_ID + " INTEGER NULL, " +
	DB_LOG_DIRTY + " BOOLEAN NOT NULL, " +
	DB_LOG_LAST_UPDATE + " INTEGER NULL, " + 
	DB_LOG_ACTION_NAME + " CHAR(30) NOT NULL, " + 
	DB_LOG_PARAMETER + " VARCHAR(45) NOT NULL, " + 
	DB_LOG_FK_USER + " INTEGER NULL " + 
	");";

	private static final String CREATE_INDEX_DB_LOG_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_DB_LOG + "_serverid_idx" +
	" ON " + TABLE_DB_LOG + " ("+ DB_LOG_SERVER_ID +")"; 


	public static final String TABLE_CURRENCY = "core_currency";
	public static final String CURRENCY_ID = "id";
	public static final String CURRENCY_SERVER_ID = "server_id";
	public static final String CURRENCY_DIRTY = "dirty";
	public static final String CURRENCY_LAST_UPDATE = "last_update";
	public static final String CURRENCY_ABBREVIATURE = "abbreviature";
	public static final String CURRENCY_DESCRIPTION = "description";

	private static final String CREATE_TABLE_CURRENCY = "CREATE TABLE " + 
	TABLE_CURRENCY + " ("+ 
	CURRENCY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	CURRENCY_SERVER_ID + " INTEGER NULL, " +
	CURRENCY_DIRTY + " BOOLEAN NOT NULL, " +
	CURRENCY_LAST_UPDATE + " INTEGER NULL, " + 
	CURRENCY_ABBREVIATURE + " CHAR(30) NOT NULL, " + 
	CURRENCY_DESCRIPTION + " CHAR(30) NOT NULL " + 
	");";

	private static final String CREATE_INDEX_CURRENCY_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_CURRENCY + "_serverid_idx" +
	" ON " + TABLE_CURRENCY + " ("+ CURRENCY_SERVER_ID +")"; 


	public static final String TABLE_TOKEN_TYPE = "core_token_type";
	public static final String TOKEN_TYPE_ID = "id";
	public static final String TOKEN_TYPE_SERVER_ID = "server_id";
	public static final String TOKEN_TYPE_DIRTY = "dirty";
	public static final String TOKEN_TYPE_LAST_UPDATE = "last_update";
	public static final String TOKEN_TYPE_NAME = "name";

	private static final String CREATE_TABLE_TOKEN_TYPE = "CREATE TABLE " + 
	TABLE_TOKEN_TYPE + " ("+ 
	TOKEN_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	TOKEN_TYPE_SERVER_ID + " INTEGER NULL, " +
	TOKEN_TYPE_DIRTY + " BOOLEAN NOT NULL, " +
	TOKEN_TYPE_LAST_UPDATE + " INTEGER NULL, " + 
	TOKEN_TYPE_NAME + " CHAR(30) NOT NULL " + 
	");";

	private static final String CREATE_INDEX_TOKEN_TYPE_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_TOKEN_TYPE + "_serverid_idx" +
	" ON " + TABLE_TOKEN_TYPE + " ("+ TOKEN_TYPE_SERVER_ID +")"; 


	public static final String TABLE_USER = "core_user";
	public static final String USER_ID = "id";
	public static final String USER_SERVER_ID = "server_id";
	public static final String USER_DIRTY = "dirty";
	public static final String USER_LAST_UPDATE = "last_update";
	public static final String USER_FK_SYSTEM = "fk_system";
	public static final String USER_FK_ROLE = "fk_role";
	public static final String USER_USERNAME = "username";
	public static final String USER_PASSWORD = "password";
	public static final String USER_NAME = "name";
	public static final String USER_EMAIL = "email";
	public static final String USER_LAST_USE_TIME = "last_use_time";
	public static final String USER_LAST_ERROR_TIME = "last_error_time";
	public static final String USER_ERROR_COUNT = "error_count";
	public static final String USER_ACTIVE = "active";

	private static final String CREATE_TABLE_USER = "CREATE TABLE " + 
	TABLE_USER + " ("+ 
	USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	USER_SERVER_ID + " INTEGER NULL, " +
	USER_DIRTY + " BOOLEAN NOT NULL, " +
	USER_LAST_UPDATE + " INTEGER NULL, " + 
	USER_FK_SYSTEM + " INTEGER NULL, " + 
	USER_FK_ROLE + " INTEGER NULL, " + 
	USER_USERNAME + " CHAR(30) NOT NULL, " + 
	USER_PASSWORD + " CHAR(30) NOT NULL, " + 
	USER_NAME + " VARCHAR(45) NOT NULL, " + 
	USER_EMAIL + " CHAR(30) NOT NULL, " + 
	USER_LAST_USE_TIME + " INTEGER NOT NULL, " + 
	USER_LAST_ERROR_TIME + " INTEGER NOT NULL, " + 
	USER_ERROR_COUNT + " INTEGER NOT NULL, " + 
	USER_ACTIVE + " BOOLEAN NOT NULL " + 
	");";

	private static final String CREATE_INDEX_USER_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_USER + "_serverid_idx" +
	" ON " + TABLE_USER + " ("+ USER_SERVER_ID +")"; 


	public static final String TABLE_TOKEN = "core_token";
	public static final String TOKEN_ID = "id";
	public static final String TOKEN_SERVER_ID = "server_id";
	public static final String TOKEN_DIRTY = "dirty";
	public static final String TOKEN_LAST_UPDATE = "last_update";
	public static final String TOKEN_FK_USER = "fk_user";
	public static final String TOKEN_FK_SYSTEM = "fk_system";
	public static final String TOKEN_FK_TOKEN_TYPE = "fk_token_type";
	public static final String TOKEN_GUID = "guid";
	public static final String TOKEN_LAST_USE_TIME = "last_use_time";
	public static final String TOKEN_EXPIRATION_TIME = "expiration_time";

	private static final String CREATE_TABLE_TOKEN = "CREATE TABLE " + 
	TABLE_TOKEN + " ("+ 
	TOKEN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	TOKEN_SERVER_ID + " INTEGER NULL, " +
	TOKEN_DIRTY + " BOOLEAN NOT NULL, " +
	TOKEN_LAST_UPDATE + " INTEGER NULL, " + 
	TOKEN_FK_USER + " INTEGER NULL, " + 
	TOKEN_FK_SYSTEM + " INTEGER NULL, " + 
	TOKEN_FK_TOKEN_TYPE + " INTEGER NULL, " + 
	TOKEN_GUID + " CHAR(36) NOT NULL, " + 
	TOKEN_LAST_USE_TIME + " INTEGER NOT NULL, " + 
	TOKEN_EXPIRATION_TIME + " INTEGER NOT NULL " + 
	");";

	private static final String CREATE_INDEX_TOKEN_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_TOKEN + "_serverid_idx" +
	" ON " + TABLE_TOKEN + " ("+ TOKEN_SERVER_ID +")"; 


	public static final String TABLE_SYSTEM = "core_system";
	public static final String SYSTEM_ID = "id";
	public static final String SYSTEM_SERVER_ID = "server_id";
	public static final String SYSTEM_DIRTY = "dirty";
	public static final String SYSTEM_LAST_UPDATE = "last_update";
	public static final String SYSTEM_NAME = "name";
	public static final String SYSTEM_ENABLED = "enabled";
	public static final String SYSTEM_FK_CURRENCY = "fk_currency";
	public static final String SYSTEM_FANTASY_NAME = "fantasy_name";
	public static final String SYSTEM_STORES_ADDRESS = "stores_address";
	public static final String SYSTEM_SRORES_ADDRESS_NUMBER = "srores_address_number";
	public static final String SYSTEM_STORES_CITY = "stores_city";
	public static final String SYSTEM_STORES_NEIGHBORHOOD = "stores_neighborhood";
	public static final String SYSTEM_STORES_ZIP_CODE = "stores_zip_code";
	public static final String SYSTEM_STORES_STATE = "stores_state";
	public static final String SYSTEM_STORES_EMAIL = "stores_email";
	public static final String SYSTEM_STORES_PHONENUMBER = "stores_phonenumber";
	public static final String SYSTEM_FK_RESELLER = "fk_reseller";

	private static final String CREATE_TABLE_SYSTEM = "CREATE TABLE " + 
	TABLE_SYSTEM + " ("+ 
	SYSTEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	SYSTEM_SERVER_ID + " INTEGER NULL, " +
	SYSTEM_DIRTY + " BOOLEAN NOT NULL, " +
	SYSTEM_LAST_UPDATE + " INTEGER NULL, " + 
	SYSTEM_NAME + " CHAR(30) NOT NULL, " + 
	SYSTEM_ENABLED + " BOOLEAN NOT NULL, " + 
	SYSTEM_FK_CURRENCY + " INTEGER NULL, " + 
	SYSTEM_FANTASY_NAME + " VARCHAR(45) NOT NULL, " + 
	SYSTEM_STORES_ADDRESS + " VARCHAR(45) NOT NULL, " + 
	SYSTEM_SRORES_ADDRESS_NUMBER + " CHAR(30) NOT NULL, " + 
	SYSTEM_STORES_CITY + " VARCHAR(45) NOT NULL, " + 
	SYSTEM_STORES_NEIGHBORHOOD + " VARCHAR(45) NOT NULL, " + 
	SYSTEM_STORES_ZIP_CODE + " CHAR(15) NOT NULL, " + 
	SYSTEM_STORES_STATE + " CHAR(30) NOT NULL, " + 
	SYSTEM_STORES_EMAIL + " VARCHAR(45) NOT NULL, " + 
	SYSTEM_STORES_PHONENUMBER + " CHAR(30) NOT NULL, " + 
	SYSTEM_FK_RESELLER + " INTEGER NULL " + 
	");";

	private static final String CREATE_INDEX_SYSTEM_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_SYSTEM + "_serverid_idx" +
	" ON " + TABLE_SYSTEM + " ("+ SYSTEM_SERVER_ID +")"; 


	public static final String TABLE_ROLE = "core_role";
	public static final String ROLE_ID = "id";
	public static final String ROLE_SERVER_ID = "server_id";
	public static final String ROLE_DIRTY = "dirty";
	public static final String ROLE_LAST_UPDATE = "last_update";
	public static final String ROLE_NAME = "name";

	private static final String CREATE_TABLE_ROLE = "CREATE TABLE " + 
	TABLE_ROLE + " ("+ 
	ROLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	ROLE_SERVER_ID + " INTEGER NULL, " +
	ROLE_DIRTY + " BOOLEAN NOT NULL, " +
	ROLE_LAST_UPDATE + " INTEGER NULL, " + 
	ROLE_NAME + " CHAR(30) NOT NULL " + 
	");";

	private static final String CREATE_INDEX_ROLE_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_ROLE + "_serverid_idx" +
	" ON " + TABLE_ROLE + " ("+ ROLE_SERVER_ID +")"; 

// discount

	public static final String TABLE_DISCOUNT = "discount_discount";
	public static final String DISCOUNT_ID = "id";
	public static final String DISCOUNT_SERVER_ID = "server_id";
	public static final String DISCOUNT_DIRTY = "dirty";
	public static final String DISCOUNT_LAST_UPDATE = "last_update";
	public static final String DISCOUNT_VALUE = "value";
	public static final String DISCOUNT_PERCENTAGE = "percentage";
	public static final String DISCOUNT_FK_PRODUCT = "fk_product";
	public static final String DISCOUNT_FK_CATEGORY = "fk_category";
	public static final String DISCOUNT_FK_BRAND = "fk_brand";
	public static final String DISCOUNT_FK_CLIENT_FROM_SYSTEM = "fk_client_from_system";
	public static final String DISCOUNT_FK_GENDER = "fk_gender";

	private static final String CREATE_TABLE_DISCOUNT = "CREATE TABLE " + 
	TABLE_DISCOUNT + " ("+ 
	DISCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	DISCOUNT_SERVER_ID + " INTEGER NULL, " +
	DISCOUNT_DIRTY + " BOOLEAN NOT NULL, " +
	DISCOUNT_LAST_UPDATE + " INTEGER NULL, " + 
	DISCOUNT_VALUE + " REAL(10,2) NOT NULL, " + 
	DISCOUNT_PERCENTAGE + " REAL(10,2) NOT NULL, " + 
	DISCOUNT_FK_PRODUCT + " INTEGER NULL, " + 
	DISCOUNT_FK_CATEGORY + " INTEGER NULL, " + 
	DISCOUNT_FK_BRAND + " INTEGER NULL, " + 
	DISCOUNT_FK_CLIENT_FROM_SYSTEM + " INTEGER NULL, " + 
	DISCOUNT_FK_GENDER + " INTEGER NULL " + 
	");";

	private static final String CREATE_INDEX_DISCOUNT_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_DISCOUNT + "_serverid_idx" +
	" ON " + TABLE_DISCOUNT + " ("+ DISCOUNT_SERVER_ID +")"; 

// client

	public static final String TABLE_COUNTRY = "client_country";
	public static final String COUNTRY_ID = "id";
	public static final String COUNTRY_SERVER_ID = "server_id";
	public static final String COUNTRY_DIRTY = "dirty";
	public static final String COUNTRY_LAST_UPDATE = "last_update";
	public static final String COUNTRY_NAME = "name";

	private static final String CREATE_TABLE_COUNTRY = "CREATE TABLE " + 
	TABLE_COUNTRY + " ("+ 
	COUNTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	COUNTRY_SERVER_ID + " INTEGER NULL, " +
	COUNTRY_DIRTY + " BOOLEAN NOT NULL, " +
	COUNTRY_LAST_UPDATE + " INTEGER NULL, " + 
	COUNTRY_NAME + " CHAR(30) NOT NULL " + 
	");";

	private static final String CREATE_INDEX_COUNTRY_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_COUNTRY + "_serverid_idx" +
	" ON " + TABLE_COUNTRY + " ("+ COUNTRY_SERVER_ID +")"; 


	public static final String TABLE_BRAZILIAN = "client_brazilian";
	public static final String BRAZILIAN_ID = "id";
	public static final String BRAZILIAN_SERVER_ID = "server_id";
	public static final String BRAZILIAN_DIRTY = "dirty";
	public static final String BRAZILIAN_LAST_UPDATE = "last_update";
	public static final String BRAZILIAN_CPF = "cpf";
	public static final String BRAZILIAN_RG = "rg";
	public static final String BRAZILIAN_FK_BASIC_CLIENT = "fk_basic_client";

	private static final String CREATE_TABLE_BRAZILIAN = "CREATE TABLE " + 
	TABLE_BRAZILIAN + " ("+ 
	BRAZILIAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	BRAZILIAN_SERVER_ID + " INTEGER NULL, " +
	BRAZILIAN_DIRTY + " BOOLEAN NOT NULL, " +
	BRAZILIAN_LAST_UPDATE + " INTEGER NULL, " + 
	BRAZILIAN_CPF + " CHAR(30) NOT NULL, " + 
	BRAZILIAN_RG + " CHAR(30) NOT NULL, " + 
	BRAZILIAN_FK_BASIC_CLIENT + " INTEGER NULL " + 
	");";

	private static final String CREATE_INDEX_BRAZILIAN_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_BRAZILIAN + "_serverid_idx" +
	" ON " + TABLE_BRAZILIAN + " ("+ BRAZILIAN_SERVER_ID +")"; 

	private static final String CREATE_INDEX_BRAZILIAN_CPF = "CREATE UNIQUE INDEX " + TABLE_BRAZILIAN + "_cpf_idx" +
	" ON " + TABLE_BRAZILIAN + " ("+ BRAZILIAN_CPF +")";

	public static final String TABLE_CLIENT_FROM_SYSTEM = "client_client_from_system";
	public static final String CLIENT_FROM_SYSTEM_ID = "id";
	public static final String CLIENT_FROM_SYSTEM_SERVER_ID = "server_id";
	public static final String CLIENT_FROM_SYSTEM_DIRTY = "dirty";
	public static final String CLIENT_FROM_SYSTEM_LAST_UPDATE = "last_update";
	public static final String CLIENT_FROM_SYSTEM_FK_SYSTEM = "fk_system";
	public static final String CLIENT_FROM_SYSTEM_FK_BASIC_CLIENT = "fk_basic_client";
	public static final String CLIENT_FROM_SYSTEM_FK_SHARED_CLIENT = "fk_shared_client";
	public static final String CLIENT_FROM_SYSTEM_FK_USER = "fk_user";

	private static final String CREATE_TABLE_CLIENT_FROM_SYSTEM = "CREATE TABLE " + 
	TABLE_CLIENT_FROM_SYSTEM + " ("+ 
	CLIENT_FROM_SYSTEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	CLIENT_FROM_SYSTEM_SERVER_ID + " INTEGER NULL, " +
	CLIENT_FROM_SYSTEM_DIRTY + " BOOLEAN NOT NULL, " +
	CLIENT_FROM_SYSTEM_LAST_UPDATE + " INTEGER NULL, " + 
	CLIENT_FROM_SYSTEM_FK_SYSTEM + " INTEGER NULL, " + 
	CLIENT_FROM_SYSTEM_FK_BASIC_CLIENT + " INTEGER NULL, " + 
	CLIENT_FROM_SYSTEM_FK_SHARED_CLIENT + " INTEGER NULL, " + 
	CLIENT_FROM_SYSTEM_FK_USER + " INTEGER NULL " + 
	");";

	private static final String CREATE_INDEX_CLIENT_FROM_SYSTEM_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_CLIENT_FROM_SYSTEM + "_serverid_idx" +
	" ON " + TABLE_CLIENT_FROM_SYSTEM + " ("+ CLIENT_FROM_SYSTEM_SERVER_ID +")"; 


	public static final String TABLE_BASIC_CLIENT = "client_basic_client";
	public static final String BASIC_CLIENT_ID = "id";
	public static final String BASIC_CLIENT_SERVER_ID = "server_id";
	public static final String BASIC_CLIENT_DIRTY = "dirty";
	public static final String BASIC_CLIENT_LAST_UPDATE = "last_update";
	public static final String BASIC_CLIENT_NAME = "name";
	public static final String BASIC_CLIENT_BIRTH_DATE = "birth_date";
	public static final String BASIC_CLIENT_BIRTH_CITY = "birth_city";
	public static final String BASIC_CLIENT_BIRTH_STATE = "birth_state";
	public static final String BASIC_CLIENT_MOTHERS_NAME = "mothers_name";
	public static final String BASIC_CLIENT_FATHERS_NAME = "fathers_name";
	public static final String BASIC_CLIENT_PROFESSION = "profession";
	public static final String BASIC_CLIENT_ZIP_CODE = "zip_code";
	public static final String BASIC_CLIENT_ADDRESS = "address";
	public static final String BASIC_CLIENT_NEIGHBORHOOD = "neighborhood";
	public static final String BASIC_CLIENT_CITY = "city";
	public static final String BASIC_CLIENT_STATE = "state";
	public static final String BASIC_CLIENT_COMPLEMENT = "complement";
	public static final String BASIC_CLIENT_FK_COUNTRY = "fk_country";

	private static final String CREATE_TABLE_BASIC_CLIENT = "CREATE TABLE " + 
	TABLE_BASIC_CLIENT + " ("+ 
	BASIC_CLIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	BASIC_CLIENT_SERVER_ID + " INTEGER NULL, " +
	BASIC_CLIENT_DIRTY + " BOOLEAN NOT NULL, " +
	BASIC_CLIENT_LAST_UPDATE + " INTEGER NULL, " + 
	BASIC_CLIENT_NAME + " CHAR(30) NOT NULL, " + 
	BASIC_CLIENT_BIRTH_DATE + " INTEGER NOT NULL, " + 
	BASIC_CLIENT_BIRTH_CITY + " CHAR(30) NOT NULL, " + 
	BASIC_CLIENT_BIRTH_STATE + " CHAR(30) NOT NULL, " + 
	BASIC_CLIENT_MOTHERS_NAME + " CHAR(30) NOT NULL, " + 
	BASIC_CLIENT_FATHERS_NAME + " CHAR(30) NOT NULL, " + 
	BASIC_CLIENT_PROFESSION + " CHAR(30) NOT NULL, " + 
	BASIC_CLIENT_ZIP_CODE + " CHAR(15) NOT NULL, " + 
	BASIC_CLIENT_ADDRESS + " CHAR(30) NOT NULL, " + 
	BASIC_CLIENT_NEIGHBORHOOD + " CHAR(30) NOT NULL, " + 
	BASIC_CLIENT_CITY + " CHAR(30) NOT NULL, " + 
	BASIC_CLIENT_STATE + " CHAR(2) NOT NULL, " + 
	BASIC_CLIENT_COMPLEMENT + " VARCHAR(45) NOT NULL, " + 
	BASIC_CLIENT_FK_COUNTRY + " INTEGER NULL " + 
	");";

	private static final String CREATE_INDEX_BASIC_CLIENT_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_BASIC_CLIENT + "_serverid_idx" +
	" ON " + TABLE_BASIC_CLIENT + " ("+ BASIC_CLIENT_SERVER_ID +")"; 

// receivement

	public static final String TABLE_BANK = "receivement_bank";
	public static final String BANK_ID = "id";
	public static final String BANK_SERVER_ID = "server_id";
	public static final String BANK_DIRTY = "dirty";
	public static final String BANK_LAST_UPDATE = "last_update";
	public static final String BANK_CODE = "code";
	public static final String BANK_NAME = "name";

	private static final String CREATE_TABLE_BANK = "CREATE TABLE " + 
	TABLE_BANK + " ("+ 
	BANK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	BANK_SERVER_ID + " INTEGER NULL, " +
	BANK_DIRTY + " BOOLEAN NOT NULL, " +
	BANK_LAST_UPDATE + " INTEGER NULL, " + 
	BANK_CODE + " CHAR(30) NOT NULL, " + 
	BANK_NAME + " CHAR(30) NOT NULL " + 
	");";

	private static final String CREATE_INDEX_BANK_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_BANK + "_serverid_idx" +
	" ON " + TABLE_BANK + " ("+ BANK_SERVER_ID +")"; 


	public static final String TABLE_INVOICE = "receivement_invoice";
	public static final String INVOICE_ID = "id";
	public static final String INVOICE_SERVER_ID = "server_id";
	public static final String INVOICE_DIRTY = "dirty";
	public static final String INVOICE_LAST_UPDATE = "last_update";
	public static final String INVOICE_FK_SYSTEM = "fk_system";
	public static final String INVOICE_FK_SALE = "fk_sale";
	public static final String INVOICE_FK_CLIENT_FROM_SYSTEM = "fk_client_from_system";
	public static final String INVOICE_FK_INSTALLMENT_TYPE = "fk_installment_type";
	public static final String INVOICE_FK_INTEREST_RATE_TYPE = "fk_interest_rate_type";
	public static final String INVOICE_FK_BANK = "fk_bank";

	private static final String CREATE_TABLE_INVOICE = "CREATE TABLE " + 
	TABLE_INVOICE + " ("+ 
	INVOICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	INVOICE_SERVER_ID + " INTEGER NULL, " +
	INVOICE_DIRTY + " BOOLEAN NOT NULL, " +
	INVOICE_LAST_UPDATE + " INTEGER NULL, " + 
	INVOICE_FK_SYSTEM + " INTEGER NULL, " + 
	INVOICE_FK_SALE + " INTEGER NULL, " + 
	INVOICE_FK_CLIENT_FROM_SYSTEM + " INTEGER NULL, " + 
	INVOICE_FK_INSTALLMENT_TYPE + " INTEGER NULL, " + 
	INVOICE_FK_INTEREST_RATE_TYPE + " INTEGER NULL, " + 
	INVOICE_FK_BANK + " INTEGER NULL " + 
	");";

	private static final String CREATE_INDEX_INVOICE_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_INVOICE + "_serverid_idx" +
	" ON " + TABLE_INVOICE + " ("+ INVOICE_SERVER_ID +")"; 


	public static final String TABLE_INSTALLMENT_TYPE = "receivement_installment_type";
	public static final String INSTALLMENT_TYPE_ID = "id";
	public static final String INSTALLMENT_TYPE_SERVER_ID = "server_id";
	public static final String INSTALLMENT_TYPE_DIRTY = "dirty";
	public static final String INSTALLMENT_TYPE_LAST_UPDATE = "last_update";
	public static final String INSTALLMENT_TYPE_NAME = "name";

	private static final String CREATE_TABLE_INSTALLMENT_TYPE = "CREATE TABLE " + 
	TABLE_INSTALLMENT_TYPE + " ("+ 
	INSTALLMENT_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	INSTALLMENT_TYPE_SERVER_ID + " INTEGER NULL, " +
	INSTALLMENT_TYPE_DIRTY + " BOOLEAN NOT NULL, " +
	INSTALLMENT_TYPE_LAST_UPDATE + " INTEGER NULL, " + 
	INSTALLMENT_TYPE_NAME + " CHAR(30) NOT NULL " + 
	");";

	private static final String CREATE_INDEX_INSTALLMENT_TYPE_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_INSTALLMENT_TYPE + "_serverid_idx" +
	" ON " + TABLE_INSTALLMENT_TYPE + " ("+ INSTALLMENT_TYPE_SERVER_ID +")"; 


	public static final String TABLE_BOLETO_SICOOB = "receivement_boleto_sicoob";
	public static final String BOLETO_SICOOB_ID = "id";
	public static final String BOLETO_SICOOB_SERVER_ID = "server_id";
	public static final String BOLETO_SICOOB_DIRTY = "dirty";
	public static final String BOLETO_SICOOB_LAST_UPDATE = "last_update";
	public static final String BOLETO_SICOOB_CPF = "cpf";
	public static final String BOLETO_SICOOB_NUMERO = "numero";
	public static final String BOLETO_SICOOB_DATA = "data";
	public static final String BOLETO_SICOOB_VENCIMENTO = "vencimento";
	public static final String BOLETO_SICOOB_VALOR = "valor";
	public static final String BOLETO_SICOOB_NOSSO_NUMERO = "nosso_numero";
	public static final String BOLETO_SICOOB_QUANTIDADE = "quantidade";
	public static final String BOLETO_SICOOB_PARCELA = "parcela";
	public static final String BOLETO_SICOOB_FOI_PAGO = "foi_pago";
	public static final String BOLETO_SICOOB_DATA_DE_PAGAMENTO = "data_de_pagamento";
	public static final String BOLETO_SICOOB_VALOR_RECEBIDO = "valor_recebido";
	public static final String BOLETO_SICOOB_FK_INVOICE = "fk_invoice";

	private static final String CREATE_TABLE_BOLETO_SICOOB = "CREATE TABLE " + 
	TABLE_BOLETO_SICOOB + " ("+ 
	BOLETO_SICOOB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	BOLETO_SICOOB_SERVER_ID + " INTEGER NULL, " +
	BOLETO_SICOOB_DIRTY + " BOOLEAN NOT NULL, " +
	BOLETO_SICOOB_LAST_UPDATE + " INTEGER NULL, " + 
	BOLETO_SICOOB_CPF + " CHAR(30) NOT NULL, " + 
	BOLETO_SICOOB_NUMERO + " CHAR(30) NOT NULL, " + 
	BOLETO_SICOOB_DATA + " INTEGER NOT NULL, " + 
	BOLETO_SICOOB_VENCIMENTO + " INTEGER NOT NULL, " + 
	BOLETO_SICOOB_VALOR + " REAL(10,2) NOT NULL, " + 
	BOLETO_SICOOB_NOSSO_NUMERO + " CHAR(30) NOT NULL, " + 
	BOLETO_SICOOB_QUANTIDADE + " INTEGER NOT NULL, " + 
	BOLETO_SICOOB_PARCELA + " INTEGER NOT NULL, " + 
	BOLETO_SICOOB_FOI_PAGO + " BOOLEAN NOT NULL, " + 
	BOLETO_SICOOB_DATA_DE_PAGAMENTO + " INTEGER NOT NULL, " + 
	BOLETO_SICOOB_VALOR_RECEBIDO + " REAL(10,2) NOT NULL, " + 
	BOLETO_SICOOB_FK_INVOICE + " INTEGER NULL " + 
	");";

	private static final String CREATE_INDEX_BOLETO_SICOOB_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_BOLETO_SICOOB + "_serverid_idx" +
	" ON " + TABLE_BOLETO_SICOOB + " ("+ BOLETO_SICOOB_SERVER_ID +")"; 

	private static final String CREATE_INDEX_BOLETO_SICOOB_CPF = "CREATE UNIQUE INDEX " + TABLE_BOLETO_SICOOB + "_cpf_idx" +
	" ON " + TABLE_BOLETO_SICOOB + " ("+ BOLETO_SICOOB_CPF +")";

	public static final String TABLE_INTEREST_RATE_TYPE = "receivement_interest_rate_type";
	public static final String INTEREST_RATE_TYPE_ID = "id";
	public static final String INTEREST_RATE_TYPE_SERVER_ID = "server_id";
	public static final String INTEREST_RATE_TYPE_DIRTY = "dirty";
	public static final String INTEREST_RATE_TYPE_LAST_UPDATE = "last_update";
	public static final String INTEREST_RATE_TYPE_NAME = "name";

	private static final String CREATE_TABLE_INTEREST_RATE_TYPE = "CREATE TABLE " + 
	TABLE_INTEREST_RATE_TYPE + " ("+ 
	INTEREST_RATE_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	INTEREST_RATE_TYPE_SERVER_ID + " INTEGER NULL, " +
	INTEREST_RATE_TYPE_DIRTY + " BOOLEAN NOT NULL, " +
	INTEREST_RATE_TYPE_LAST_UPDATE + " INTEGER NULL, " + 
	INTEREST_RATE_TYPE_NAME + " CHAR(30) NOT NULL " + 
	");";

	private static final String CREATE_INDEX_INTEREST_RATE_TYPE_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_INTEREST_RATE_TYPE + "_serverid_idx" +
	" ON " + TABLE_INTEREST_RATE_TYPE + " ("+ INTEREST_RATE_TYPE_SERVER_ID +")"; 

// referral

	public static final String TABLE_RESELLER = "referral_reseller";
	public static final String RESELLER_ID = "id";
	public static final String RESELLER_SERVER_ID = "server_id";
	public static final String RESELLER_DIRTY = "dirty";
	public static final String RESELLER_LAST_UPDATE = "last_update";
	public static final String RESELLER_SYSTEM_AMOUNT = "system_amount";
	public static final String RESELLER_NAME = "name";
	public static final String RESELLER_ADDRESS = "address";
	public static final String RESELLER_NEIGHBORHOOD = "neighborhood";
	public static final String RESELLER_CITY = "city";
	public static final String RESELLER_STATE = "state";
	public static final String RESELLER_ZIP_CODE = "zip_code";

	private static final String CREATE_TABLE_RESELLER = "CREATE TABLE " + 
	TABLE_RESELLER + " ("+ 
	RESELLER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	RESELLER_SERVER_ID + " INTEGER NULL, " +
	RESELLER_DIRTY + " BOOLEAN NOT NULL, " +
	RESELLER_LAST_UPDATE + " INTEGER NULL, " + 
	RESELLER_SYSTEM_AMOUNT + " INTEGER NOT NULL, " + 
	RESELLER_NAME + " VARCHAR(45) NOT NULL, " + 
	RESELLER_ADDRESS + " VARCHAR(45) NOT NULL, " + 
	RESELLER_NEIGHBORHOOD + " VARCHAR(45) NOT NULL, " + 
	RESELLER_CITY + " VARCHAR(45) NOT NULL, " + 
	RESELLER_STATE + " CHAR(30) NOT NULL, " + 
	RESELLER_ZIP_CODE + " CHAR(15) NOT NULL " + 
	");";

	private static final String CREATE_INDEX_RESELLER_SERVER_ID = "CREATE UNIQUE INDEX " + TABLE_RESELLER + "_serverid_idx" +
	" ON " + TABLE_RESELLER + " ("+ RESELLER_SERVER_ID +")"; 


	private void fill(SQLiteDatabase db){

// cash
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('cash_register')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('cash_launch')", null);
// supply
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('stock_review')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('unit')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('category')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('brand')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('gender')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('product')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('distributor_contact')", null);
// credit_protection
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('Issue')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('shared_client')", null);
// sales
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('product_on_sale')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('sale_type')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('sale')", null);
// core
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('db_log')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('currency')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('token_type')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('user')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('token')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('system')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('role')", null);
// discount
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('discount')", null);
// client
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('country')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('brazilian')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('client_from_system')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('basic_client')", null);
// receivement
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('bank')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('invoice')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('installment_type')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('boleto_sicoob')", null);
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('interest_rate_type')", null);
// referral
		db.rawQuery("INSERT INTO update_history (table_name) VALUES ('reseller')", null);

	}

	private DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	public static DbHelper getInstance(Context ctx) {
		if (db_instance == null) {
			db_instance = new DbHelper(ctx.getApplicationContext());
		}
		return db_instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

// Start of user code user specification for onCreate
		db.execSQL(CREATE_TABLE_COOKIE);
		db.execSQL(CREATE_TABLE_UPDATE_HISTORY);
		db.execSQL(CREATE_INDEX_UPDATE_HISTORY_TNAME);
//End of user code

// cash
		db.execSQL(CREATE_TABLE_CASH_REGISTER);
		db.execSQL(CREATE_INDEX_CASH_REGISTER_SERVER_ID);
		db.execSQL(CREATE_TABLE_CASH_LAUNCH);
		db.execSQL(CREATE_INDEX_CASH_LAUNCH_SERVER_ID);
// supply
		db.execSQL(CREATE_TABLE_STOCK_REVIEW);
		db.execSQL(CREATE_INDEX_STOCK_REVIEW_SERVER_ID);
		db.execSQL(CREATE_TABLE_UNIT);
		db.execSQL(CREATE_INDEX_UNIT_SERVER_ID);
		db.execSQL(CREATE_TABLE_CATEGORY);
		db.execSQL(CREATE_INDEX_CATEGORY_SERVER_ID);
		db.execSQL(CREATE_TABLE_BRAND);
		db.execSQL(CREATE_INDEX_BRAND_SERVER_ID);
		db.execSQL(CREATE_TABLE_GENDER);
		db.execSQL(CREATE_INDEX_GENDER_SERVER_ID);
		db.execSQL(CREATE_TABLE_PRODUCT);
		db.execSQL(CREATE_INDEX_PRODUCT_SERVER_ID);
		db.execSQL(CREATE_TABLE_DISTRIBUTOR_CONTACT);
		db.execSQL(CREATE_INDEX_DISTRIBUTOR_CONTACT_SERVER_ID);
// credit_protection
		db.execSQL(CREATE_TABLE_ISSUE);
		db.execSQL(CREATE_INDEX_ISSUE_SERVER_ID);
		db.execSQL(CREATE_TABLE_SHARED_CLIENT);
		db.execSQL(CREATE_INDEX_SHARED_CLIENT_SERVER_ID);
// sales
		db.execSQL(CREATE_TABLE_PRODUCT_ON_SALE);
		db.execSQL(CREATE_INDEX_PRODUCT_ON_SALE_SERVER_ID);
		db.execSQL(CREATE_TABLE_SALE_TYPE);
		db.execSQL(CREATE_INDEX_SALE_TYPE_SERVER_ID);
		db.execSQL(CREATE_TABLE_SALE);
		db.execSQL(CREATE_INDEX_SALE_SERVER_ID);
// core
		db.execSQL(CREATE_TABLE_DB_LOG);
		db.execSQL(CREATE_INDEX_DB_LOG_SERVER_ID);
		db.execSQL(CREATE_TABLE_CURRENCY);
		db.execSQL(CREATE_INDEX_CURRENCY_SERVER_ID);
		db.execSQL(CREATE_TABLE_TOKEN_TYPE);
		db.execSQL(CREATE_INDEX_TOKEN_TYPE_SERVER_ID);
		db.execSQL(CREATE_TABLE_USER);
		db.execSQL(CREATE_INDEX_USER_SERVER_ID);
		db.execSQL(CREATE_TABLE_TOKEN);
		db.execSQL(CREATE_INDEX_TOKEN_SERVER_ID);
		db.execSQL(CREATE_TABLE_SYSTEM);
		db.execSQL(CREATE_INDEX_SYSTEM_SERVER_ID);
		db.execSQL(CREATE_TABLE_ROLE);
		db.execSQL(CREATE_INDEX_ROLE_SERVER_ID);
// discount
		db.execSQL(CREATE_TABLE_DISCOUNT);
		db.execSQL(CREATE_INDEX_DISCOUNT_SERVER_ID);
// client
		db.execSQL(CREATE_TABLE_COUNTRY);
		db.execSQL(CREATE_INDEX_COUNTRY_SERVER_ID);
		db.execSQL(CREATE_TABLE_BRAZILIAN);
		db.execSQL(CREATE_INDEX_BRAZILIAN_SERVER_ID);
		db.execSQL(CREATE_INDEX_BRAZILIAN_CPF);
		db.execSQL(CREATE_TABLE_CLIENT_FROM_SYSTEM);
		db.execSQL(CREATE_INDEX_CLIENT_FROM_SYSTEM_SERVER_ID);
		db.execSQL(CREATE_TABLE_BASIC_CLIENT);
		db.execSQL(CREATE_INDEX_BASIC_CLIENT_SERVER_ID);
// receivement
		db.execSQL(CREATE_TABLE_BANK);
		db.execSQL(CREATE_INDEX_BANK_SERVER_ID);
		db.execSQL(CREATE_TABLE_INVOICE);
		db.execSQL(CREATE_INDEX_INVOICE_SERVER_ID);
		db.execSQL(CREATE_TABLE_INSTALLMENT_TYPE);
		db.execSQL(CREATE_INDEX_INSTALLMENT_TYPE_SERVER_ID);
		db.execSQL(CREATE_TABLE_BOLETO_SICOOB);
		db.execSQL(CREATE_INDEX_BOLETO_SICOOB_SERVER_ID);
		db.execSQL(CREATE_INDEX_BOLETO_SICOOB_CPF);
		db.execSQL(CREATE_TABLE_INTEREST_RATE_TYPE);
		db.execSQL(CREATE_INDEX_INTEREST_RATE_TYPE_SERVER_ID);
// referral
		db.execSQL(CREATE_TABLE_RESELLER);
		db.execSQL(CREATE_INDEX_RESELLER_SERVER_ID);

		fill(db);
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//db.execSQL("PRAGMA foreign_keys = OFF;");

// cash
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CASH_REGISTER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CASH_LAUNCH);
// supply
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCK_REVIEW);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNIT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BRAND);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GENDER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISTRIBUTOR_CONTACT);
// credit_protection
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ISSUE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHARED_CLIENT);
// sales
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_ON_SALE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALE_TYPE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALE);
// core
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DB_LOG);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENCY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOKEN_TYPE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOKEN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYSTEM);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLE);
// discount
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISCOUNT);
// client
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BRAZILIAN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENT_FROM_SYSTEM);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BASIC_CLIENT);
// receivement
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BANK);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVOICE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTALLMENT_TYPE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOLETO_SICOOB);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTEREST_RATE_TYPE);
// referral
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESELLER);

		//db.execSQL("PRAGMA foreign_keys = ON;");
		onCreate(db);
	}

}
