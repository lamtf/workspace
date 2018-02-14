package com.uisleandro.store.cash.view;
import android.database.Cursor;

public class CheckHistoryOut {

	public static CheckHistoryOut FromCursor(Cursor cursor){
		CheckHistoryOut instance = new CheckHistoryOut();
		instance.setCashRegisterLastUpdate(cursor.getLong(1));
		instance.setCashRegisterOpeningValue(cursor.getFloat(2));
		instance.setCashRegisterReceivedValue(cursor.getFloat(3));
		instance.setCashRegisterClosingValue(cursor.getFloat(4));
		instance.setUserLastUpdate(cursor.getLong(5));
		instance.setUserUsername(cursor.getString(6));
		instance.setUserPassword(cursor.getString(7));
		instance.setUserName(cursor.getString(8));
		instance.setUserEmail(cursor.getString(9));
		instance.setUserLastUseTime(cursor.getLong(10));
		instance.setUserLastErrorTime(cursor.getLong(11));
		instance.setUserErrorCount(cursor.getInt(12));
		instance.setUserActive((cursor.getInt(13) > 0));
		instance.setRoleLastUpdate(cursor.getLong(14));
		instance.setRoleName(cursor.getString(15));
		instance.setSystemLastUpdate(cursor.getLong(16));
		instance.setSystemName(cursor.getString(17));
		instance.setSystemEnabled((cursor.getInt(18) > 0));
		instance.setCurrencyLastUpdate(cursor.getLong(19));
		instance.setCurrencyAbbreviature(cursor.getString(20));
		instance.setCurrencyDescription(cursor.getString(21));
		return instance;
	}

	private Long cash_register_last_update;
	private Float cash_register_opening_value;
	private Float cash_register_received_value;
	private Float cash_register_closing_value;
	private Long user_last_update;
	private String user_username;
	private String user_password;
	private String user_name;
	private String user_email;
	private Long user_last_use_time;
	private Long user_last_error_time;
	private Integer user_error_count;
	private Boolean user_active;
	private Long role_last_update;
	private String role_name;
	private Long system_last_update;
	private String system_name;
	private Boolean system_enabled;
	private Long currency_last_update;
	private String currency_abbreviature;
	private String currency_description;

	public Long getCashRegisterLastUpdate () {
		return cash_register_last_update;
	}
	
	public void setCashRegisterLastUpdate (Long cash_register_last_update) {
		this.cash_register_last_update = cash_register_last_update;
	}

	public Float getCashRegisterOpeningValue () {
		return cash_register_opening_value;
	}
	
	public void setCashRegisterOpeningValue (Float cash_register_opening_value) {
		this.cash_register_opening_value = cash_register_opening_value;
	}

	public Float getCashRegisterReceivedValue () {
		return cash_register_received_value;
	}
	
	public void setCashRegisterReceivedValue (Float cash_register_received_value) {
		this.cash_register_received_value = cash_register_received_value;
	}

	public Float getCashRegisterClosingValue () {
		return cash_register_closing_value;
	}
	
	public void setCashRegisterClosingValue (Float cash_register_closing_value) {
		this.cash_register_closing_value = cash_register_closing_value;
	}

	public Long getUserLastUpdate () {
		return user_last_update;
	}
	
	public void setUserLastUpdate (Long user_last_update) {
		this.user_last_update = user_last_update;
	}

	public String getUserUsername () {
		return user_username;
	}
	
	public void setUserUsername (String user_username) {
		this.user_username = user_username;
	}

	public String getUserPassword () {
		return user_password;
	}
	
	public void setUserPassword (String user_password) {
		this.user_password = user_password;
	}

	public String getUserName () {
		return user_name;
	}
	
	public void setUserName (String user_name) {
		this.user_name = user_name;
	}

	public String getUserEmail () {
		return user_email;
	}
	
	public void setUserEmail (String user_email) {
		this.user_email = user_email;
	}

	public Long getUserLastUseTime () {
		return user_last_use_time;
	}
	
	public void setUserLastUseTime (Long user_last_use_time) {
		this.user_last_use_time = user_last_use_time;
	}

	public Long getUserLastErrorTime () {
		return user_last_error_time;
	}
	
	public void setUserLastErrorTime (Long user_last_error_time) {
		this.user_last_error_time = user_last_error_time;
	}

	public Integer getUserErrorCount () {
		return user_error_count;
	}
	
	public void setUserErrorCount (Integer user_error_count) {
		this.user_error_count = user_error_count;
	}

	public Boolean getUserActive () {
		return user_active;
	}
	
	public void setUserActive (Boolean user_active) {
		this.user_active = user_active;
	}

	public Long getRoleLastUpdate () {
		return role_last_update;
	}
	
	public void setRoleLastUpdate (Long role_last_update) {
		this.role_last_update = role_last_update;
	}

	public String getRoleName () {
		return role_name;
	}
	
	public void setRoleName (String role_name) {
		this.role_name = role_name;
	}

	public Long getSystemLastUpdate () {
		return system_last_update;
	}
	
	public void setSystemLastUpdate (Long system_last_update) {
		this.system_last_update = system_last_update;
	}

	public String getSystemName () {
		return system_name;
	}
	
	public void setSystemName (String system_name) {
		this.system_name = system_name;
	}

	public Boolean getSystemEnabled () {
		return system_enabled;
	}
	
	public void setSystemEnabled (Boolean system_enabled) {
		this.system_enabled = system_enabled;
	}

	public Long getCurrencyLastUpdate () {
		return currency_last_update;
	}
	
	public void setCurrencyLastUpdate (Long currency_last_update) {
		this.currency_last_update = currency_last_update;
	}

	public String getCurrencyAbbreviature () {
		return currency_abbreviature;
	}
	
	public void setCurrencyAbbreviature (String currency_abbreviature) {
		this.currency_abbreviature = currency_abbreviature;
	}

	public String getCurrencyDescription () {
		return currency_description;
	}
	
	public void setCurrencyDescription (String currency_description) {
		this.currency_description = currency_description;
	}

}
