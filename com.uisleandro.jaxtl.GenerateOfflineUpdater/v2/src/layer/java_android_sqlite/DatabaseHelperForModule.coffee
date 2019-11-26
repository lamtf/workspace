Observable = require "../../transformation/streams/Observable"
xmiQuery = require "../../transformation/json/XmiQuery"
Util = require "../../transformation/json/Util"
fs = require "fs"
path = require "path"

writeFile = (fileName, data)->
  fs.mkdir path.dirname(fileName),(recursive: true), (e)->
    fs.writeFile fileName, data, (e)->
      if e?
        throw e

java_type = (type, name)->
  if ( null is type or type.toLowerCase() is name.toLowerCase() or type.toLowerCase() is 'date' or type.toLowerCase() is 'datetime' or type.toLowerCase() is 'time' or type.toLowerCase() is 'identifier' )
    return 'Long'
  else if type.toLowerCase() is 'number' or type.toLowerCase() is 'picturetype'
    return 'Integer'
  else if type.toLowerCase() is 'money'
    return 'Float'
  else if type.toLowerCase() is 'precisenumber' or type.toLowerCase() is 'veryprecisenumber2'
    return 'Double'
  else if type.toLowerCase() is 'yesnoquestion'
    return 'Boolean'
  else
    return 'String'

sqlite_type = (type, name)->
  if null is type
    return 'INTEGER'
  else if type.toLowerCase() is name
    return 'INTEGER'
  else if type.toLowerCase() is 'date'
    return 'INTEGER'
  else if type.toLowerCase() is 'datetime'
    return 'INTEGER'
  else if type.toLowerCase() is 'time'
    return 'INTEGER'
  else if type.toLowerCase() is 'number'
    return 'INTEGER'
  else if type.toLowerCase() is 'money'
    return 'REAL(10,2)'
  else if type.toLowerCase() is 'precisenumber'
    return 'DOUBLE'
  else if type.toLowerCase() is 'veryprecisenumber2'
    return 'DOUBLE PRECISION'
  else if type.toLowerCase() is 'yesnoquestion'
    return 'BOOLEAN'
  else if type.toLowerCase() is 'identifier'
    return 'INTEGER'
  else if type.toLowerCase() is 'guidtype'
    return 'CHAR36'
  else if type.toLowerCase() is 'abbreviature'
    if (name.indexOf('uf') > -1 or name.indexOf('state') > -1)
      return 'CHAR(2)'
    else
      return 'CHAR(8)'
  else if type.toLowerCase() is 'bigtext'
    return 'VARCHAR(256)'
  else if type.toLowerCase() is 'file'
    return 'VARCHAR(128)'
  else if type.toLowerCase() is 'smalltext'
    if (name.indexOf('zip') > -1 or name.indexOf('cep') > -1)
      return 'CHAR(15)'
    else
      return 'CHAR(30)'
  else if type.toLowerCase() is 'mediumtext'
    return 'VARCHAR(45)'
  else if type.toLowerCase() is 'barcodetype'
    return 'CHAR(64)'
  else if type.toLowerCase() is 'picturetype'
    return 'INTEGER'
  else
    return 'VARCHAR(45)'

global_placeholder = /\[\$\]/

global_dbhelper = """
package %%1%%;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static string DATABASE_NAME = "%%2%%";%%3%%

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      %%4%%

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
      %%5%%
      this.onCreate(db);

    }

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


class DatabaseHelperForModule
  constructor:(@location)->
    $this = @
    Observable.extends @
  observe:(source)->
    source.addObserver @


  declare_java_class:(packageName, contents)->
    """
    public class #{packageName.ToCamelCase()} {
      \n
      #{contents}
    }
    """
  create_table:(className)->
    """\n      db.execSQL(CREATE_TABLE_#{className.toUpperCase()});"""
  drop_table:(className)->
    """\n      db.execSQL("DROP TABLE IF EXISTS " + TABLE_#{className.toUpperCase()});"""

  declare_java_static_string:(className,propertyName)->
    """    public static final String #{className.toUpperCase()}_#{propertyName.toUpperCase()} = "#{propertyName}";\n"""

  declare_sqlite_field:(className,propertyName,type)->
    "    #{className.toUpperCase()}_#{propertyName.toUpperCase()} + \" #{sqlite_type type, propertyName} NULL"

  declare_sqlite_field_java:(className, attr, last)->
    $this = @
    result = ""
    type = getType attr

    if attr.isFk
      result = $this.declare_sqlite_field className, "FK_#{attr.name.toUpperCase()}", null
    else
      result = $this.declare_sqlite_field className, attr.name, type.name

    if last
      result += ";\";\n"
    else
      result += ", \" +\n"
    return result

  declare_static_java_variable:(className, attr, last)->
    $this = @
    result = ""
    type = getType attr

    if attr.isFk
      result += $this.declare_java_static_string className, "fk_#{attr.name}"
    else
      result += $this.declare_java_static_string className, attr.name

    return result

  update:(obj)->
    $this = @
    #
    #console.log mvc_package
    root = obj.data.children[0].children[0];
    #root.children.forEach (x)-> console.log x.name
    mvcPackage = (xmiQuery.getPackageByName root,'mvc')[0]
    dataModelsPackage = (xmiQuery.getPackageByName mvcPackage, 'dataModels')
    dataModelsPackage.forEach (p)->
      modelName = p.getParent().name
      #console.log "Package::#{modelName}"
      contents = ""

      str_create_table = "";
      str_drop_table = "";

      dbHelper = global_dbhelper
      dbHelper = dbHelper.replace "%%1%%", "#{modelName.ToCamelCase()}Project.model"
      dbHelper = dbHelper.replace "%%2%%", "#{modelName}"

      (xmiQuery.getAllNamedClasses p)
      .forEach (mClass)->
        #console.log "Table::#{mClass.name}"
        #        console.log "#{modelName}.#{mClass.name}"
        #console.log mClass.children
        className = mClass.name

        str_create_table += $this.create_table className
        str_drop_table += $this.drop_table className

        decl_vars = """
        \n
            public static final String TABLE_#{className.toUpperCase()} = "#{className}";
            public static final String #{className.toUpperCase()}_ID = "id";
            public static final String #{className.toUpperCase()}_UUID = "uuid";
            public static final String #{className.toUpperCase()}_DIRTY = "dirty";\n
        """

        decl_class = """
        \n
            private static final String CREATE_TABLE_#{className.toUpperCase()} = "CREATE TABLE " + TABLE_#{className.toUpperCase()} +
            " ("+ #{className.toUpperCase()}_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            #{className.toUpperCase()}_UUID + " STRING NULL, " +
            #{className.toUpperCase()}_DIRTY + " BOOLEAN NOT NULL, " +\n
        """

        i = 0
        attrs = mClass.children.filter((x)->x.tagName is "ownedAttribute")
        while i < attrs.length
          attr = attrs[i]
          i++
          decl_vars += $this.declare_static_java_variable className, attr, 0
          decl_class += $this.declare_sqlite_field_java className, attr, (i == mClass.children.length)

        contents += decl_vars
        contents += decl_class

      dbHelper = dbHelper.replace "%%3%%", contents
      dbHelper = dbHelper.replace "%%4%%", str_create_table
      dbHelper = dbHelper.replace "%%5%%", str_drop_table

      writeFile("#{$this.location}/#{modelName.ToCamelCase()}Project/model/DbHelper.java", dbHelper)
      return

module.exports = DatabaseHelperForModule
