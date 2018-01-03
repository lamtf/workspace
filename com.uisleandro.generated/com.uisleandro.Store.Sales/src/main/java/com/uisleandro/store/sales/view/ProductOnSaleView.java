
package com.uisleandro.store.sales.view; 

import org.json.JSONException;
import org.json.JSONObject;

public class ProductOnSaleView{

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private long fk_sale;
	private long fk_product;

	public ProductOnSaleView(){
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.fk_sale = 0L;
		this.fk_product = 0L;

	}

	public long getId(){
		return id;
	}

	public void setId(long id){
		this.id = id;
	}

	public long getServerId(){
		return server_id;
	}

	public void setServerId(long server_id){
		this.server_id = server_id;
	}

	public boolean isDirty(){
		return dirty;
	}

	public void setDirty(boolean dirty){
		this.dirty = dirty;
	}

	public long getLastUpdate(){
		return last_update;
	}

	public void setLastUpdate(long last_update){
		this.last_update = last_update;
	}

	public long getFkSale(){
		return fk_sale;
	}

	public void setFkSale(long fk_sale){
		this.fk_sale = fk_sale;
	}

	public long getFkProduct(){
		return fk_product;
	}

	public void setFkProduct(long fk_product){
		this.fk_product = fk_product;
	}


	public String toJsonString(){

		String that = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"fk_sale\":\"" + this.fk_sale+ "\"," + 
			"\"fk_product\":\"" + this.fk_product+ "\"" + 
		"}";

		return that;

	}

	public String toString(){

		return "ProductOnSaleView";

	}

	public static ProductOnSaleView FromJson(String json){

		if(json != null) {
		try {

		JSONObject obj = new JSONObject(json);
				ProductOnSaleView result = new ProductOnSaleView();

				if(obj.has("client_id") && !obj.isNull("client_id")){
					result.setId(obj.getLong("client_id"));
				}
				if(obj.has("server_id") && !obj.isNull("server_id")){
					result.setServerId(obj.getLong("server_id"));
				}
				result.setLastUpdate(obj.getLong("last_update"));
				if(obj.has("server_id") && !obj.isNull("fk_sale")){
					result.setFkSale(obj.getLong("fk_sale"));
				}
				if(obj.has("server_id") && !obj.isNull("fk_product")){
					result.setFkProduct(obj.getLong("fk_product"));
				}

				return result;

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;

	}


	public static ProductOnSaleView FromJsonObj(JSONObject obj){

		if(null != obj) {
			try {
				ProductOnSaleView result = new ProductOnSaleView();

				if(obj.has("client_id") && !obj.isNull("client_id")){
					result.setId(obj.getLong("client_id"));
				}
				if(obj.has("server_id") && !obj.isNull("server_id")){
					result.setServerId(obj.getLong("server_id"));
				}
				result.setLastUpdate(obj.getLong("last_update"));
				if(obj.has("fk_sale") && !obj.isNull("fk_sale")){
					result.setFkSale(obj.getLong("fk_sale"));
				}
				if(obj.has("fk_product") && !obj.isNull("fk_product")){
					result.setFkProduct(obj.getLong("fk_product"));
				}

				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;

	}


}
