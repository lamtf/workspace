package com.uisleandro.store.core.view;
import android.database.Cursor;

public class LoginOut {

	public static LoginOut FromCursor(Cursor cursor){
		LoginOut instance = new LoginOut();
		instance.setUserLastUpdate(cursor.getLong(1));
		instance.setUserUsername(cursor.getString(2));
		instance.setUserPassword(cursor.getString(3));
		instance.setUserName(cursor.getString(4));
		instance.setUserEmail(cursor.getString(5));
		instance.setUserLastUseTime(cursor.getLong(6));
		instance.setUserLastErrorTime(cursor.getLong(7));
		instance.setUserErrorCount(cursor.getInt(8));
		instance.setUserActive((cursor.getInt(9) > 0));
		instance.setRoleLastUpdate(cursor.getLong(10));
		instance.setRoleName(cursor.getString(11));
		instance.setSystemLastUpdate(cursor.getLong(12));
		instance.setSystemName(cursor.getString(13));
		instance.setSystemEnabled((cursor.getInt(14) > 0));
		instance.setCurrencyLastUpdate(cursor.getLong(15));
		instance.setCurrencyAbbreviature(cursor.getString(16));
		instance.setCurrencyDescription(cursor.getString(17));
		return instance;
	}

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
