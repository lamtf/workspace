Observable = require "../../transformation/streams/Observable"
xmiQuery = require "../../transformation/json/XmiQuery"
Util = require "../../transformation/json/Util"
fs = require "fs"
path = require "path"

content = """
package %%package%%;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

%%import%%

public class %%className%% extends ContentProvider {

  public static final String AUTHORITY = %%authority%%
  public static final String %%TABLE%%_PATH = %%table%%
  public static final Uri %%TABLE%%_URI = Uri.parse(
    ContentResolver.SCHEME_CONTENT +
    "://" + AUTHORITY + "/" +
    %%TABLE%%_PATH);

  private DbHelper mDbHelper;

  @Override
	public boolean onCreate() {
		mDbHelper = new DbHelper(getContext());
		return true;
	}

  @Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
    %%query.body%%
  }

  @Override
	public String getType(Uri uri) {
    %%getType.body%%
  }

	@Override
	public Uri insert(Uri uri, ContentValues values) {
    return null;
  }

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
    return 0;
  }

  @Override
	public int update(Uri uri, ContentValues values, String selection,
	  String[] selectionArgs) {
    return 0;
  }

  %%body%%

}
"""

getType=(attr)->
  type = xmiQuery.getChildrenByType(attr, 'uml:PrimitiveType')[0]
  if !type?
    type = xmiQuery.getChildrenByType(attr, 'uml:Class')[0]
    attr.isFk = true;
    if !type?
      type = attr
  #console.log """#{attr.getAttr("name")}->#{type.getXmiObject().getAttr("name")}"""
  return type.getXmiObject()

# It will provide basic info such as icon and name
class ContentProviderForModule
  constructor:(@location)->
    $this = @
    Observable.extends @
  observe:(source)->
    source.addObserver @
