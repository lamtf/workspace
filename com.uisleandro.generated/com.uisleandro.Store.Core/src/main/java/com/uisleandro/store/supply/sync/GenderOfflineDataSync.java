package com.uisleandro.store.supply.sync;

import android.content.Context;
import android.util.Log;

import com.uisleandro.util.DBPaginator;
import com.uisleandro.store.supply.model.GenderOfflineHelper;
import com.uisleandro.store.supply.view.GenderDataView;
import com.uisleandro.util.web.TLSWebClient2;
import com.uisleandro.util.web.TlsCallback;
import com.uisleandro.util.web.sync.SyncUpdater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public class GenderOfflineDataSync implements SyncUpdater {

	final DBPaginator paginator;
	TLSWebClient2 client;
	GenderOfflineHelper genderOfflineHelper;

	public GenderSync(TLSWebClient2 client, Context context){
		this.client = client;
		paginator = new DBPaginator();
		genderOfflineHelper = new GenderOfflineHelper(context);
	}

	public void insert_on_client(){
		long the_last_server_id_on_client = genderOfflineHelper.getLastServerId(); //step 1,1
		//paginator.setPageSize(100);
		paginator.setPageNumber(0);
		paginator.setFinished(false);
		paginator.setError(false);
		Log.v("rest-api", "atempt to insert on the client .. ");
		//i think now im repeating the process
		while (false == paginator.isFinished()) {
			String json = "{ \"server_id\": " + String.valueOf(the_last_server_id_on_client) + "," +
						" \"page_number\" : " + String.valueOf(paginator.getPageNumber()) + "," +
						" \"page_size\": " + String.valueOf(paginator.getPageSize()) + " }"; 
			paginator.nextPage();
			Log.v("rest-api", "get_for_client_insert :\n" + json);
			//step 1,2
			client.run(
				"https://192.168.0.250/naiara/sync/gender/get_for_client_insert", json,
				new TlsCallback() {
					@Override
					public void CallBack(String result) {
						Log.v("rest-api", "get_for_client_insert :\n" + result);
						try {
							JSONArray array = new JSONArray(result);
							if (0 == array.length()) {
								paginator.setFinished(true);
							}
							for (int i = 0; i < array.length(); i++) {
								//Log.v("rest-api", array.getJSONObject(i).toString());
								Gender gender = Gender.FromJsonObj(array.getJSONObject(i));
								genderOfflineHelper.insert(gender); //step 1,3
							}
						} catch (JSONException ex) {
							paginator.setFinished(true);
							ex.printStackTrace();
						}
					}
				}
			);
		}
	
	}

	//todo error se eu quero usar assim eu tenho que ter esta informação, no cliente, por tabela
	public void update_on_client(){
		//not exactly yet implemented
		//todo test..
		//paginator.setPageSize(100);
		paginator.setPageNumber(0);
		paginator.setFinished(false);
		paginator.setError(false);
			Log.v("rest-api", "atempt to update on client .. ");
			genderOfflineHelper.before_client_updating();
			while (false == paginator.isFinished()) {
				long lastUpdateTime = genderOfflineHelper.getLastUpdateTime();
				String json = "{ " +
				"\"last_update_time\" : " + String.valueOf(lastUpdateTime) +
				", \"page_number\" : " + paginator.getPageNumber() +
				", \"page_size\" : " + paginator.getPageSize() +
				" }";
				paginator.nextPage();
				Log.v("rest-api", "get_for_client_update :\n" + json);
				client.run("https://192.168.0.250/naiara/sync/gender/get_for_client_update", json, new TlsCallback() {
						@Override
						public void CallBack(String result) {
						Log.v("rest-api", "get_for_client_update :\n" + result);
							try {
								JSONArray array = new JSONArray(result);
								//i can't finish it if this don't have any value
								if(0 < array.length()){
									for (int i = 0; i < array.length(); i++) {
										Gender obj = Gender.FromJsonObj(
												array.getJSONObject(i)
										);
										genderOfflineHelper.update(obj);
									}
								} else {
									paginator.setFinished(true);
								}
							} catch (JSONException e) {
								e.printStackTrace();
								paginator.setFinished(true);
								paginator.setError(true);
							}
						}
					}
				);
			}
	
			if(!paginator.isError()) {
				genderOfflineHelper.after_client_updating();
			}
	
	}

	public void fix_foreign_keys_on_client(){
	}

	public void insert_on_server(){
		//paginator.setPageSize(100);
		paginator.setPageNumber(0);
		paginator.setFinished(false);
		paginator.setError(false);
		Log.v("rest-api", "atempt to insert on the server .. ");
		while (false == paginator.isFinished()) {
			List<Gender> gender_for_insertion_on_server =
					genderOfflineHelper.listForInsertOnServer(
							paginator.getPageNumber(),
							paginator.getPageSize()); //step 2,1
			paginator.nextPage();
			Iterator<Gender> it = gender_for_insertion_on_server.iterator();
			String json = "[";
			if(it.hasNext()){
				Log.v("rest-api", "building the message .. ");
				json += it.next().toJsonString();
				while(it.hasNext()){
					json += ", " + it.next().toJsonString();
				}
			}else{
				paginator.setFinished(true);
				Log.v("rest-api", "abort insertions..");
				continue;
			}
			json += "]";
			Log.v("rest-api", "bash_insert:\n"+ json);
			//step 2,2
			client.run(
				"https://192.168.0.250/naiara/sync/gender/bash_insert",
				json,
				new TlsCallback() {
					@Override
					public void CallBack(String result) {
					Log.v("rest-api", "bash_insert :\n" + result);
					try {
						JSONArray array = new JSONArray(result);
						for (int i = 0; i < array.length(); i++) {
							JSONObject row = array.getJSONObject(i);
							//step 2,3
							genderOfflineHelper.fixAfterServerInsertAndUpdate(
									row.getLong("client_id"),
									row.getLong("server_id"),
									row.getLong("last_update_time")
							);
							Log.v("rest-api", "updating the client");
						}
					} catch (JSONException e) {
						e.printStackTrace();
						paginator.setFinished(true);
					}
				}
			});
		}
	
	}

	public void update_on_server(){
		Log.v("rest-api", "attempt to update on the server .. ");
		//paginator.setPageSize(100);
		paginator.setPageNumber(0);
		paginator.setFinished(false);
		paginator.setError(false);
		while (false == paginator.isFinished()) {
			//4,2
			List<Gender> update_remote = genderOfflineHelper.listForUpdateOnServer(paginator.getPageNumber(), paginator.getPageSize());
			paginator.nextPage();
			Iterator<Gender> it = update_remote.iterator();
			String json = "[";
			if (it.hasNext()) {
				Log.v("rest-api", "updating the server..\n");
				json += it.next().toJsonString();
				while (it.hasNext()) {
					json += "," + it.next().toJsonString();
				}
			}else{
				paginator.setFinished(true);
				Log.v("rest-api", "abort server updates..");
				continue;
			}
			json += "]";
			Log.v("rest-api", "bash_update :\n" + json);
			//todo the date is not correct
			//4,3
			client.run(
				"https://192.168.0.250/naiara/sync/gender/bash_update", json, new TlsCallback() {
				@Override
				public void CallBack(String result) {
					Log.v("rest-api", "bash_update :\n" + result);
					try {
						JSONArray array = new JSONArray(result);
						for (int i = 0; i < array.length(); i++) {
							JSONObject row = array.getJSONObject(i);
							//step 4,4
							genderOfflineHelper.fixAfterServerInsertAndUpdate(
									row.getLong("client_id"),
									row.getLong("server_id"),
									row.getLong("last_update_time")
							);
						}
					} catch (JSONException e) {
						e.printStackTrace();
						paginator.setFinished(true);
					}
				}
			});
		}
	}

}
