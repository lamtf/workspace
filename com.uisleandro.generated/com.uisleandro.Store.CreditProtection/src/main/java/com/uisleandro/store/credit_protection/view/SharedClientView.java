
package com.uisleandro.store.credit_protection.view; 

import org.json.JSONException;
import org.json.JSONObject;

public class SharedClientView{

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private String name;
	private long birth_date;
	private String birth_city;
	private String birth_state;
	private String mothers_name;
	private String fathers_name;
	private String profession;
	private String zip_code;
	private String address;
	private String neighborhood;
	private String city;
	private String state;
	private String complement;
	private long fk_country;

	public SharedClientView(){
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.name = "";
		this.birth_date = 0L;
		this.birth_city = "";
		this.birth_state = "";
		this.mothers_name = "";
		this.fathers_name = "";
		this.profession = "";
		this.zip_code = "";
		this.address = "";
		this.neighborhood = "";
		this.city = "";
		this.state = "";
		this.complement = "";
		this.fk_country = 0L;

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

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public long getBirthDate(){
		return birth_date;
	}

	public void setBirthDate(long birth_date){
		this.birth_date = birth_date;
	}

	public String getBirthCity(){
		return birth_city;
	}

	public void setBirthCity(String birth_city){
		this.birth_city = birth_city;
	}

	public String getBirthState(){
		return birth_state;
	}

	public void setBirthState(String birth_state){
		this.birth_state = birth_state;
	}

	public String getMothersName(){
		return mothers_name;
	}

	public void setMothersName(String mothers_name){
		this.mothers_name = mothers_name;
	}

	public String getFathersName(){
		return fathers_name;
	}

	public void setFathersName(String fathers_name){
		this.fathers_name = fathers_name;
	}

	public String getProfession(){
		return profession;
	}

	public void setProfession(String profession){
		this.profession = profession;
	}

	public String getZipCode(){
		return zip_code;
	}

	public void setZipCode(String zip_code){
		this.zip_code = zip_code;
	}

	public String getAddress(){
		return address;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getNeighborhood(){
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood){
		this.neighborhood = neighborhood;
	}

	public String getCity(){
		return city;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getState(){
		return state;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getComplement(){
		return complement;
	}

	public void setComplement(String complement){
		this.complement = complement;
	}

	public long getFkCountry(){
		return fk_country;
	}

	public void setFkCountry(long fk_country){
		this.fk_country = fk_country;
	}


	public String toJsonString(){

		String that = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"name\":\"" + this.name+ "\"," + 
			"\"birth_date\":\"" + this.birth_date+ "\"," + 
			"\"birth_city\":\"" + this.birth_city+ "\"," + 
			"\"birth_state\":\"" + this.birth_state+ "\"," + 
			"\"mothers_name\":\"" + this.mothers_name+ "\"," + 
			"\"fathers_name\":\"" + this.fathers_name+ "\"," + 
			"\"profession\":\"" + this.profession+ "\"," + 
			"\"zip_code\":\"" + this.zip_code+ "\"," + 
			"\"address\":\"" + this.address+ "\"," + 
			"\"neighborhood\":\"" + this.neighborhood+ "\"," + 
			"\"city\":\"" + this.city+ "\"," + 
			"\"state\":\"" + this.state+ "\"," + 
			"\"complement\":\"" + this.complement+ "\"," + 
			"\"fk_country\":\"" + this.fk_country+ "\"" + 
		"}";

		return that;

	}

	public String toString(){

		return this.name;

	}

	public static SharedClientView FromJson(String json){

		if(json != null) {
		try {

		JSONObject obj = new JSONObject(json);
				SharedClientView result = new SharedClientView();

				if(obj.has("client_id") && !obj.isNull("client_id")){
					result.setId(obj.getLong("client_id"));
				}
				if(obj.has("server_id") && !obj.isNull("server_id")){
					result.setServerId(obj.getLong("server_id"));
				}
				result.setLastUpdate(obj.getLong("last_update"));
				result.setName(obj.getString("name"));
				result.setBirthDate(obj.getLong("birth_date"));
				result.setBirthCity(obj.getString("birth_city"));
				result.setBirthState(obj.getString("birth_state"));
				result.setMothersName(obj.getString("mothers_name"));
				result.setFathersName(obj.getString("fathers_name"));
				result.setProfession(obj.getString("profession"));
				result.setZipCode(obj.getString("zip_code"));
				result.setAddress(obj.getString("address"));
				result.setNeighborhood(obj.getString("neighborhood"));
				result.setCity(obj.getString("city"));
				result.setState(obj.getString("state"));
				result.setComplement(obj.getString("complement"));
				if(obj.has("server_id") && !obj.isNull("fk_country")){
					result.setFkCountry(obj.getLong("fk_country"));
				}

				return result;

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;

	}


	public static SharedClientView FromJsonObj(JSONObject obj){

		if(null != obj) {
			try {
				SharedClientView result = new SharedClientView();

				if(obj.has("client_id") && !obj.isNull("client_id")){
					result.setId(obj.getLong("client_id"));
				}
				if(obj.has("server_id") && !obj.isNull("server_id")){
					result.setServerId(obj.getLong("server_id"));
				}
				result.setLastUpdate(obj.getLong("last_update"));
				result.setName(obj.getString("name"));
				result.setBirthDate(obj.getLong("birth_date"));
				result.setBirthCity(obj.getString("birth_city"));
				result.setBirthState(obj.getString("birth_state"));
				result.setMothersName(obj.getString("mothers_name"));
				result.setFathersName(obj.getString("fathers_name"));
				result.setProfession(obj.getString("profession"));
				result.setZipCode(obj.getString("zip_code"));
				result.setAddress(obj.getString("address"));
				result.setNeighborhood(obj.getString("neighborhood"));
				result.setCity(obj.getString("city"));
				result.setState(obj.getString("state"));
				result.setComplement(obj.getString("complement"));
				if(obj.has("fk_country") && !obj.isNull("fk_country")){
					result.setFkCountry(obj.getLong("fk_country"));
				}

				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;

	}


}
