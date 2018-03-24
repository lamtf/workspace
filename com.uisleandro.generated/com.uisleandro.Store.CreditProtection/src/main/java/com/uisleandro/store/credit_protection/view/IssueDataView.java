package com.uisleandro.store.credit_protection.view; 

import org.json.JSONException;
import org.json.JSONObject;
import android.content.ContentValues;
import android.database.Cursor;

public class IssueDataView {

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private long fk_shared_client;
	private long fk_system;
	private String description;
	private boolean active;
	private boolean isAnswer;
	private long fk_issue;

	public IssueDataView () {
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.fk_shared_client = 0L;
		this.fk_system = 0L;
		this.description = "";
		this.active = false;
		this.isAnswer = false;
		this.fk_issue = 0L;
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

	public long getFkSharedClient () {
		return fk_shared_client;
	}

	public void setFkSharedClient (long fk_shared_client) {
		this.fk_shared_client = fk_shared_client;
	}

	public long getFkSystem () {
		return fk_system;
	}

	public void setFkSystem (long fk_system) {
		this.fk_system = fk_system;
	}

	public String getDescription () {
		return description;
	}

	public void setDescription (String description) {
		this.description = description;
	}

	public boolean getActive () {
		return active;
	}

	public void setActive (boolean active) {
		this.active = active;
	}

	public boolean getIsAnswer () {
		return isAnswer;
	}

	public void setIsAnswer (boolean isAnswer) {
		this.isAnswer = isAnswer;
	}

	public long getFkIssue () {
		return fk_issue;
	}

	public void setFkIssue (long fk_issue) {
		this.fk_issue = fk_issue;
	}

	public String toJsonString () {
		String result = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"fk_shared_client\":\"" + this.fk_shared_client+ "\"," + 
			"\"fk_system\":\"" + this.fk_system+ "\"," + 
			"\"description\":\"" + this.description+ "\"," + 
			"\"active\":\"" + this.active+ "\"," + 
			"\"isAnswer\":\"" + this.isAnswer+ "\"," + 
			"\"fk_issue\":\"" + this.fk_issue+ "\"" + 
		"}";
		return result;
	}

	public String toString () {
		return this.description;

	}

	public static IssueDataView FromJson(String json){
		if(json != null) {
			try {
				JSONObject obj = new JSONObject(json);
				return IssueDataView.FromJsonObj(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static IssueDataView FromJsonObj (JSONObject obj) {
		if(null != obj) {
			try {
				IssueDataView result = new IssueDataView();
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
				if(obj.has("fk_shared_client") && !obj.isNull("fk_shared_client")){
					result.setFkSharedClient(obj.getLong("fk_shared_client"));
				}
				if(obj.has("fk_system") && !obj.isNull("fk_system")){
					result.setFkSystem(obj.getLong("fk_system"));
				}
				result.setDescription(obj.getString("description"));
				result.setActive(obj.getInt("active") > 0);
				result.setIsAnswer(obj.getInt("isAnswer") > 0);
				if(obj.has("fk_issue") && !obj.isNull("fk_issue")){
					result.setFkIssue(obj.getLong("fk_issue"));
				}
				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static IssueDataView FromCursor (Cursor cursor) {
		if(null != cursor){
			IssueDataView result = new IssueDataView();
			result.setId(cursor.getLong(0));
			result.setServerId(cursor.getLong(1));
			result.setDirty(cursor.getInt(2) > 0);
			result.setLastUpdate(cursor.getLong(3));
			result.setFkSharedClient(cursor.getLong(4));
			result.setFkSystem(cursor.getLong(5));
			result.setDescription(cursor.getString(6));
			result.setActive((cursor.getInt(7) > 0));
			result.setIsAnswer((cursor.getInt(8) > 0));
			result.setFkIssue(cursor.getLong(9));
			return result;		
		}
		return null;
	}

	public ContentValues toInsertValues () {

		ContentValues contentValues = new ContentValues();
		contentValues.put("last_update",last_update);
		contentValues.put("fk_shared_client",fk_shared_client);
		contentValues.put("fk_system",fk_system);
		contentValues.put("description",description);
		contentValues.put("active",active);
		contentValues.put("isAnswer",isAnswer);
		contentValues.put("fk_issue",fk_issue);
		return contentValues;

	}

	public ContentValues toUpdateValues () {

		ContentValues contentValues = new ContentValues();
		contentValues.put("last_update",last_update);
		contentValues.put("fk_shared_client",fk_shared_client);
		contentValues.put("fk_system",fk_system);
		contentValues.put("description",description);
		contentValues.put("active",active);
		contentValues.put("isAnswer",isAnswer);
		contentValues.put("fk_issue",fk_issue);
		return contentValues;

	}

}
