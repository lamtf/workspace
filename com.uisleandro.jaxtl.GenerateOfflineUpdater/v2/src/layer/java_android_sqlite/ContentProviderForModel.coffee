Observable = require "../../transformation/streams/Observable"
xmiQuery = require "../../transformation/json/XmiQuery"
Util = require "../../transformation/json/Util"
fs = require "fs"
path = require "path"

global_content = """
package %%package%%;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import %%DbHelper%%;

public class %%ClassName%%Provider extends ContentProvider {

  public static final String AUTHORITY = %%authority%%

  public static final String %%TABLE%%_GET_PATH = %%GetTable%%
  public static final Uri %%TABLE%%_GET_URI = Uri.parse(
  ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY + "/" + %%TABLE%%_GET_PATH
  );
  public static final String %%TABLE%%_GET_MIME_TYPE =
  ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + %%TABLE%%_GET_PATH;

  public static final String %%TABLE%%_LIST_PATH = %%ListTables%%
  public static final Uri %%TABLE%%_LIST_URI = Uri.parse(
  ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY + "/" + %%TABLE%%_LIST_PATH
  );
  public static final String %%TABLE%%_LIST_MIME_TYPE =
  ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + %%TABLE%%_LIST_PATH;

  public static final String %%TABLE%%_COUNT_PATH = %%CountTables%%
  public static final Uri %%TABLE%%_COUNT_URI = Uri.parse(
  ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY + "/" +
  %%TABLE%%_COUNT_PATH
  );
  public static final String %%TABLE%%_COUNT_MIME_TYPE =
  ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + %%TABLE%%_COUNT_PATH;

  /*%%more_actions%%*/

  private final static int GET = 0;
  private final static int LIST = 1;
  private final static int COUNT = 2;
  /*%%more_static_int%%*/

  private final static UriMatcher MATCHER = new UriMatcher(
      UriMatcher.NO_MATCH);

  static {
    MATCHER.addURI(AUTHORITY, %%TABLE%%_GET_PATH, GET);
    MATCHER.addURI(AUTHORITY, %%TABLE%%_LIST_PATH, LIST);
    MATCHER.addURI(AUTHORITY, %%TABLE%%_COUNT_PATH, COUNT);
    /*%%more_uri%%*/
  }

  private DbHelper mDbHelper;

  @Override
  public boolean onCreate() {
    mDbHelper = new DbHelper(getContext());
    return true;
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection,
    String[] selectionArgs, String sortOrder) {
    Cursor cursor;
    switch(MATCHER.match(uri)){
      case LIST:%%listQuery%%
        break;
      case GET:%%oneQuery%%
        break;
      case COUNT:%%countQuery%%
        break;
      /*%%more_queries%%*/
    }
    return cursor;
  }

  @Override
  public String getType(Uri uri) {
    String result = "";
    switch(MATCHER.match(uri)){
      case GET:
        result = %%TABLE%%_GET_MIME_TYPE;
        break;
      case LIST:
        result = %%TABLE%%_LIST_MIME_TYPE;
        break;
      case COUNT:
        result = %%TABLE%%_COUNT_MIME_TYPE;
        break;
      /*%%more_types%%*/
    }
    return result;
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    %%insert.body%%
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    %%delete.body%%
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection,
    String[] selectionArgs) {
    %%update.body%%
  }

  %%body%%

}
"""


listQuery=(tableName, attrs)->
  """
\n
        int pageSize = Integer.valueOf(selectionArgs[0]);
        int pageNumber = Integer.valueOf(selectionArgs[1]);

        query = "SELECT id, server_id, dirty, " +
#{(attrs.map (attr)-> "          \"#{attr.getAttr "name"}").join(", \" + \n")} " +
          "FROM " + DBHelper.#{tableName.toUpperCase()};

        if(pageSize > 0){
          query += " LIMIT " + String.valueOf(pageSize) + " OFFSET " + String.valueOf(pageSize * pageNumber);
        }

        query += ";";
        cursor = database.rawQuery(query, null);

  """

countQuery=(tableName)->
  """
\n
        query = "SELECT count(*) as count FROM " +
        DBHelper.#{tableName.toUpperCase()} + ";";
        cursor = database.rawQuery(query, null);

  """

oneQuery=(tableName, attrs)->
  """
\n
        query = "SELECT id, server_id, dirty, " +
#{(attrs.map (attr)-> "          \"#{attr.getAttr "name"}").join(", \" + \n")} " +
          "FROM " + DBHelper.#{tableName.toUpperCase()} +
          " WHERE " + id = " + String.valueOf(Integer.valueOf(selectionArgs[0])) + ";";
        cursor = database.rawQuery(query, null);

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

class ContentProviderForModel
  constructor:(@location)->
    $this = @
    Observable.extends @
  observe:(source)->
    source.addObserver @
  update:(obj)->
  update:(obj)->
    $this = @
    moduleContent = global_content
    #console.log mvc_package
    root = obj.data.children[0].children[0];
    #root.children.forEach (x)-> console.log x.name
    mvcPackage = (xmiQuery.getPackageByName root,'mvc')[0]
    dataModelsPackage = (xmiQuery.getPackageByName mvcPackage, 'dataModels')
    dataModelsPackage.forEach (p)->
      modelName = p.getParent().name
      #console.log "Package::#{modelName}"

      moduleContent = moduleContent.replace "%%package%%", "#{modelName.toLowerCase()}project.repository"
      moduleContent = moduleContent.replace "%%DbHelper%%", "#{modelName.toLowerCase()}project.repository.DbHelper"

      (xmiQuery.getAllNamedClasses p)
      .forEach (mClass)->
        classContent = moduleContent;
        className = mClass.name
        classContent = classContent.replace "%%ClassName%%", className.ToCamelCase()
        classContent = classContent.replace "%%GetTable%%", "Get#{className.ToCamelCase()}"
        classContent = classContent.replace "%%ListTable%%", "List#{className.ToCamelCase()}"
        classContent = classContent.replace "%%CountTable%%", "Count#{className.ToCamelCase()}"
        #console.log classContent
        attrs = mClass.children.filter((x)->x.tagName is "ownedAttribute")
        strListQuery = listQuery className, attrs
        classContent = classContent.replace "%%listQuery%%", strListQuery
        strOneQuery = oneQuery className, attrs
        classContent = classContent.replace "%%oneQuery%%", strOneQuery
        strCountQuery = countQuery className
        classContent = classContent.replace "%%countQuery%%", strCountQuery
        console.log classContent


module.exports = ContentProviderForModel
