package com.uisleandro.store.receivement.view; 

import org.json.JSONException;
import org.json.JSONObject;
import android.content.ContentValues;
import android.database.Cursor;

public class InvoiceDataView {

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private long fk_system;
	private long fk_sale;
	private long fk_client_from_system;
	private long fk_installment_type;
	private long fk_interest_rate_type;
	private long fk_bank;

	public InvoiceDataView () {
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.fk_system = 0L;
		this.fk_sale = 0L;
		this.fk_client_from_system = 0L;
		this.fk_installment_type = 0L;
		this.fk_interest_rate_type = 0L;
		this.fk_bank = 0L;
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

	public long getFkSystem () {
		return fk_system;
	}

	public void setFkSystem (long fk_system) {
		this.fk_system = fk_system;
	}

	public long getFkSale () {
		return fk_sale;
	}

	public void setFkSale (long fk_sale) {
		this.fk_sale = fk_sale;
	}

	public long getFkClientFromSystem () {
		return fk_client_from_system;
	}

	public void setFkClientFromSystem (long fk_client_from_system) {
		this.fk_client_from_system = fk_client_from_system;
	}

	public long getFkInstallmentType () {
		return fk_installment_type;
	}

	public void setFkInstallmentType (long fk_installment_type) {
		this.fk_installment_type = fk_installment_type;
	}

	public long getFkInterestRateType () {
		return fk_interest_rate_type;
	}

	public void setFkInterestRateType (long fk_interest_rate_type) {
		this.fk_interest_rate_type = fk_interest_rate_type;
	}

	public long getFkBank () {
		return fk_bank;
	}

	public void setFkBank (long fk_bank) {
		this.fk_bank = fk_bank;
	}

	public String toJsonString () {
		String result = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"fk_system\":\"" + this.fk_system+ "\"," + 
			"\"fk_sale\":\"" + this.fk_sale+ "\"," + 
			"\"fk_client_from_system\":\"" + this.fk_client_from_system+ "\"," + 
			"\"fk_installment_type\":\"" + this.fk_installment_type+ "\"," + 
			"\"fk_interest_rate_type\":\"" + this.fk_interest_rate_type+ "\"," + 
			"\"fk_bank\":\"" + this.fk_bank+ "\"" + 
		"}";
		return result;
	}

	public String toString () {
		return "InvoiceDataView";

	}

	public static InvoiceDataView FromJson(String json){
		if(json != null) {
			try {
				JSONObject obj = new JSONObject(json);
				return InvoiceDataView.FromJsonObj(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static InvoiceDataView FromJsonObj (JSONObject obj) {
		if(null != obj) {
			try {
				InvoiceDataView result = new InvoiceDataView();
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
				if(obj.has("fk_system") && !obj.isNull("fk_system")){
					result.setFkSystem(obj.getLong("fk_system"));
				}
				if(obj.has("fk_sale") && !obj.isNull("fk_sale")){
					result.setFkSale(obj.getLong("fk_sale"));
				}
				if(obj.has("fk_client_from_system") && !obj.isNull("fk_client_from_system")){
					result.setFkClientFromSystem(obj.getLong("fk_client_from_system"));
				}
				if(obj.has("fk_installment_type") && !obj.isNull("fk_installment_type")){
					result.setFkInstallmentType(obj.getLong("fk_installment_type"));
				}
				if(obj.has("fk_interest_rate_type") && !obj.isNull("fk_interest_rate_type")){
					result.setFkInterestRateType(obj.getLong("fk_interest_rate_type"));
				}
				if(obj.has("fk_bank") && !obj.isNull("fk_bank")){
					result.setFkBank(obj.getLong("fk_bank"));
				}
				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static InvoiceDataView FromCursor (Cursor cursor) {
		if(null != cursor){
			InvoiceDataView result = new InvoiceDataView();
			result.setId(cursor.getLong(0));
			result.setServerId(cursor.getLong(1));
			result.setDirty(cursor.getInt(2) > 0);
			result.setLastUpdate(cursor.getLong(3));
			result.setFkSystem(cursor.getLong(4));
			result.setFkSale(cursor.getLong(5));
			result.setFkClientFromSystem(cursor.getLong(6));
			result.setFkInstallmentType(cursor.getLong(7));
			result.setFkInterestRateType(cursor.getLong(8));
			result.setFkBank(cursor.getLong(9));
			return result;		
		}
		return null;
	}

	public ContentValues toInsertValues () {

		ContentValues contentValues = new ContentValues();
		contentValues.put("last_update",last_update);
		contentValues.put("fk_system",fk_system);
		contentValues.put("fk_sale",fk_sale);
		contentValues.put("fk_client_from_system",fk_client_from_system);
		contentValues.put("fk_installment_type",fk_installment_type);
		contentValues.put("fk_interest_rate_type",fk_interest_rate_type);
		contentValues.put("fk_bank",fk_bank);
		return contentValues;

	}

	public ContentValues toUpdateValues () {

		ContentValues contentValues = new ContentValues();
		contentValues.put("last_update",last_update);
		contentValues.put("fk_system",fk_system);
		contentValues.put("fk_sale",fk_sale);
		contentValues.put("fk_client_from_system",fk_client_from_system);
		contentValues.put("fk_installment_type",fk_installment_type);
		contentValues.put("fk_interest_rate_type",fk_interest_rate_type);
		contentValues.put("fk_bank",fk_bank);
		return contentValues;

	}

}
