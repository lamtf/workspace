// Start of user code reserved-for:AndroidSqliteDatabase001
package com.uisleandro.store.core.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.core.view.UserView

public class UserDataSource {

	public static final String AUTHORITY = "com.uisleandro.user";
	public static final String SCHEME = "content://";

	public static final String USER_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String USER_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String USER_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String USER_ALL = SCHEME + AUTHORITY + "/all";
	public static final String USER_SOME = SCHEME + AUTHORITY + "/some";
	public static final String USER_BYID = SCHEME + AUTHORITY + "/byid";
	public static final String USER_LASTID = SCHEME + AUTHORITY + "/lastid";

	Context context;
	public UserDataSource(Context context){
		this.context = context;
	}

	public List<UserView> listAll(){
		List<UserView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(USER_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      UserView that = UserView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public UserView getById(long id){
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(USER_BYID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = UserView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<UserView listSome(long page_count, long page_size){
		List<UserView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(USER_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      UserView that = UserView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId(){
		long result = 0;
		Cursor cursor = context.getContentResolver().query(USER_LASTID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert(UserView that) {
		context.getContentResolver().insert(USER_INSERT, that.toInsertArray());
		return 0;
	}

	public int update(UserView that) {
		return context.getContentResolver().update(USER_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete(UserView that) {
		return context.getContentResolver().delete(USER_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById(long id) {
		return context.getContentResolver().delete(USER_DELETE, null, new String[]{ String.valueOf(id) });
	}

}

