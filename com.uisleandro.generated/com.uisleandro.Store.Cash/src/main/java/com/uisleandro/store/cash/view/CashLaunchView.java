
package com.uisleandro.store.cash.view; 

import org.json.JSONException;
import org.json.JSONObject;
import android.database.Cursor;

public class CashLaunchView {

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private long fk_cash_register;
	private String justification;
	private float amount_spent;
	private long fk_currency;

	public CashLaunchView () {
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.fk_cash_register = 0L;
		this.justification = "";
		this.amount_spent = 0F;
		this.fk_currency = 0L;
	}

	public long getId () {
		return id;
	}

	public void setId (long id) {
		this.id = id;
	}

	public long getServerId () {
		return server_id;
	}

	public void setServerId (long server_id) {
		this.server_id = server_id;
	}

	public boolean isDirty () {
		return dirty;
	}

	public void setDirty (boolean dirty) {
		this.dirty = dirty;
	}

	public long getLastUpdate () {
		return last_update;
	}

	public void setLastUpdate (long last_update) {
		this.last_update = last_update;
	}

	public long getFkCashRegister () {
		return fk_cash_register;
	}

	public void setFkCashRegister (long fk_cash_register) {
		this.fk_cash_register = fk_cash_register;
	}

	public String getJustification () {
		return justification;
	}

	public void setJustification (String justification) {
		this.justification = justification;
	}

	public float getAmountSpent () {
		return amount_spent;
	}

	public void setAmountSpent (float amount_spent) {
		this.amount_spent = amount_spent;
	}

	public long getFkCurrency () {
		return fk_currency;
	}

	public void setFkCurrency (long fk_currency) {
		this.fk_currency = fk_currency;
	}

	public String toJsonString () {
		String result = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"fk_cash_register\":\"" + this.fk_cash_register+ "\"," + 
			"\"justification\":\"" + this.justification+ "\"," + 
			"\"amount_spent\":\"" + this.amount_spent+ "\"," + 
			"\"fk_currency\":\"" + this.fk_currency+ "\"" + 
		"}";
		return result;
	}

	public String toString () {
		return this.justification;

	}

	public static CashLaunchView FromJson(String json){
		if(json != null) {
			try {
				JSONObject obj = new JSONObject(json);
				return CashLaunchView.FromJsonObj(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static CashLaunchView FromJsonObj (JSONObject obj) {
		if(null != obj) {
			try {
				CashLaunchView result = new CashLaunchView();
				if(obj.has("client_id") && !obj.isNull("client_id")){
					result.setId(obj.getLong("client_id"));
				}
				if(obj.has("server_id") && !obj.isNull("server_id")){
					result.setServerId(obj.getLong("server_id"));
				}
				/* if(obj.has("dirty") && !obj.isNull("dirty")){
					result.setDirty(obj.getInt("dirty") > 0);
				} */
				result.setLastUpdate(obj.getLong("last_update"));
				if(obj.has("fk_cash_register") && !obj.isNull("fk_cash_register")){
					result.setFkCashRegister(obj.getLong("fk_cash_register"));
				}
				result.setJustification(obj.getString("justification"));
				result.setAmountSpent(Float.valueOf(obj.getString("amount_spent")));
				if(obj.has("fk_currency") && !obj.isNull("fk_currency")){
					result.setFkCurrency(obj.getLong("fk_currency"));
				}
				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static CashLaunchView FromCursor (Cursor cursor) {
		if(null != cursor){
			CashLaunchView result = new CashLaunchView();
			result.setId(cursor.getLong(0));
			result.setServerId(cursor.getLong(1));
			result.setDirty(cursor.getInt(2) > 0);
			result.setLastUpdate(cursor.getLong(3));
			result.setFkCashRegister(cursor.getLong(4));
			result.setJustification(cursor.getString(5));
			result.setAmountSpent(cursor.getFloat(6));
			result.setFkCurrency(cursor.getLong(7));
			return result;		
		}
		return null;
	}
}
