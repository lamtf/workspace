package com.uisleandro.store.supply.view; 

import org.json.JSONException;
import org.json.JSONObject;
import android.database.Cursor;

public class ProductDataView {

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private long fk_system;
	private String barcode;
	private String description;
	private int amount;
	private long fk_gender;
	private float purchase_price;
	private float sale_price;
	private long fk_category;
	private String size;
	private long fk_unit;
	private long expiration_date;
	private long fk_brand;

	public ProductDataView () {
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.fk_system = 0L;
		this.barcode = "";
		this.description = "";
		this.amount = 0;
		this.fk_gender = 0L;
		this.purchase_price = 0F;
		this.sale_price = 0F;
		this.fk_category = 0L;
		this.size = "";
		this.fk_unit = 0L;
		this.expiration_date = 0L;
		this.fk_brand = 0L;
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

	public String getBarcode () {
		return barcode;
	}

	public void setBarcode (String barcode) {
		this.barcode = barcode;
	}

	public String getDescription () {
		return description;
	}

	public void setDescription (String description) {
		this.description = description;
	}

	public int getAmount () {
		return amount;
	}

	public void setAmount (int amount) {
		this.amount = amount;
	}

	public long getFkGender () {
		return fk_gender;
	}

	public void setFkGender (long fk_gender) {
		this.fk_gender = fk_gender;
	}

	public float getPurchasePrice () {
		return purchase_price;
	}

	public void setPurchasePrice (float purchase_price) {
		this.purchase_price = purchase_price;
	}

	public float getSalePrice () {
		return sale_price;
	}

	public void setSalePrice (float sale_price) {
		this.sale_price = sale_price;
	}

	public long getFkCategory () {
		return fk_category;
	}

	public void setFkCategory (long fk_category) {
		this.fk_category = fk_category;
	}

	public String getSize () {
		return size;
	}

	public void setSize (String size) {
		this.size = size;
	}

	public long getFkUnit () {
		return fk_unit;
	}

	public void setFkUnit (long fk_unit) {
		this.fk_unit = fk_unit;
	}

	public long getExpirationDate () {
		return expiration_date;
	}

	public void setExpirationDate (long expiration_date) {
		this.expiration_date = expiration_date;
	}

	public long getFkBrand () {
		return fk_brand;
	}

	public void setFkBrand (long fk_brand) {
		this.fk_brand = fk_brand;
	}

	public String toJsonString () {
		String result = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"fk_system\":\"" + this.fk_system+ "\"," + 
			"\"barcode\":\"" + this.barcode+ "\"," + 
			"\"description\":\"" + this.description+ "\"," + 
			"\"amount\":\"" + this.amount+ "\"," + 
			"\"fk_gender\":\"" + this.fk_gender+ "\"," + 
			"\"purchase_price\":\"" + this.purchase_price+ "\"," + 
			"\"sale_price\":\"" + this.sale_price+ "\"," + 
			"\"fk_category\":\"" + this.fk_category+ "\"," + 
			"\"size\":\"" + this.size+ "\"," + 
			"\"fk_unit\":\"" + this.fk_unit+ "\"," + 
			"\"expiration_date\":\"" + this.expiration_date+ "\"," + 
			"\"fk_brand\":\"" + this.fk_brand+ "\"" + 
		"}";
		return result;
	}

	public String toString () {
		return this.barcode;

	}

	public static ProductDataView FromJson(String json){
		if(json != null) {
			try {
				JSONObject obj = new JSONObject(json);
				return ProductDataView.FromJsonObj(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static ProductDataView FromJsonObj (JSONObject obj) {
		if(null != obj) {
			try {
				ProductDataView result = new ProductDataView();
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
				result.setBarcode(obj.getString("barcode"));
				result.setDescription(obj.getString("description"));
				result.setAmount(obj.getInt("amount"));
				if(obj.has("fk_gender") && !obj.isNull("fk_gender")){
					result.setFkGender(obj.getLong("fk_gender"));
				}
				result.setPurchasePrice(Float.valueOf(obj.getString("purchase_price")));
				result.setSalePrice(Float.valueOf(obj.getString("sale_price")));
				if(obj.has("fk_category") && !obj.isNull("fk_category")){
					result.setFkCategory(obj.getLong("fk_category"));
				}
				result.setSize(obj.getString("size"));
				if(obj.has("fk_unit") && !obj.isNull("fk_unit")){
					result.setFkUnit(obj.getLong("fk_unit"));
				}
				result.setExpirationDate(obj.getLong("expiration_date"));
				if(obj.has("fk_brand") && !obj.isNull("fk_brand")){
					result.setFkBrand(obj.getLong("fk_brand"));
				}
				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static ProductDataView FromCursor (Cursor cursor) {
		if(null != cursor){
			ProductDataView result = new ProductDataView();
			result.setId(cursor.getLong(0));
			result.setServerId(cursor.getLong(1));
			result.setDirty(cursor.getInt(2) > 0);
			result.setLastUpdate(cursor.getLong(3));
			result.setFkSystem(cursor.getLong(4));
			result.setBarcode(cursor.getString(5));
			result.setDescription(cursor.getString(6));
			result.setAmount(cursor.getInt(7));
			result.setFkGender(cursor.getLong(8));
			result.setPurchasePrice(cursor.getFloat(9));
			result.setSalePrice(cursor.getFloat(10));
			result.setFkCategory(cursor.getLong(11));
			result.setSize(cursor.getString(12));
			result.setFkUnit(cursor.getLong(13));
			result.setExpirationDate(cursor.getLong(14));
			result.setFkBrand(cursor.getLong(15));
			return result;		
		}
		return null;
	}

	public String[] toInsertArray () {
		return new String[]{String.valueOf(last_update), String.valueOf(system), barcode, description, String.valueOf(amount), String.valueOf(gender), String.valueOf(purchase_price), String.valueOf(sale_price), String.valueOf(category), size, String.valueOf(unit), String.valueOf(expiration_date), String.valueOf(brand)};
	}

	public String[] toUpdateArray () {
		return new String[]{String.valueOf(id), String.valueOf(server_id), String.valueOf(dirty), String.valueOf(last_update), String.valueOf(system), barcode, description, String.valueOf(amount), String.valueOf(gender), String.valueOf(purchase_price), String.valueOf(sale_price), String.valueOf(category), size, String.valueOf(unit), String.valueOf(expiration_date), String.valueOf(brand)};
	}

}
