Observable = require "../../streams/Observable"
xmiQuery = require "../../json/XmiQuery"
Util = require "../../json/Util"
fs = require "fs"

global_placeholder = /\[\$\]/

global_dbhelper = """
package [$];

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static string DATABASE_NAME = "[$]";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }
    [$]

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        [$]
    }
    [$]

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        [$]
    }
    [$]
}

"""

global_model = """
public class [$] {
    \n
    [$]
}
"""

class PackageStream
  constructor:()->
    $this = @
    Observable.extends @
  observe:(source)->
    source.addObserver @

  sqliteType:(prop)->
    console.log prop
    return ""

  declareClass:(packageName, contents)->
    """
    public class #{packageName.ToCamelCase()} {
      \n
      #{contents}
    }
    """

  declareVariable:(className,propertyName)->
    """    public static final String #{className.toUpperCase()}_#{propertyName.toUpperCase()} = "#{propertyName}";\n"""

  declareField:(className,propertyName, last)->
    "    #{className.toUpperCase()}_#{propertyName.toUpperCase()} + \" STRING NULL \""

  declare_field_str:(className, attr, last)->
    $this = @
    result = ""
    if attr.name
      type = xmiQuery.getChildrenByType(attr, 'uml:PrimitiveType')[0]
      if type
        #console.log "\t#{attr.name} #{type.getXmiObject().name}"
        result = $this.declareField className, attr.name
      else if attr.getXmiObject()
        #console.log "\tfk_#{attr.name} #{attr.getXmiObject().name}"
        result = $this.declareField className, "FK_#{attr.name.toUpperCase()}"
      else
        type = xmiQuery.getChildrenByType(attr, 'uml:Class')[0]
        #console.log "\tfk_#{attr.name} #{type.getXmiObjeclass ClassStream
        result = $this.declareField className, "FK_#{attr.name.toUpperCase()}"
      if last
        result += ";\n"
      else
        result += "+\n"
    return result

  declare_static_variable:(className, attr, last)->
    $this = @
    result = ""
    if attr.name
      #console.log "  #{attr.name}"
      type = xmiQuery.getChildrenByType(attr, 'uml:PrimitiveType')[0]
      if type
        #console.log "\t#{attr.name} #{type.getXmiObject().name}"
        result += $this.declareVariable className, attr.name
      else if attr.getXmiObject()
        #console.log "\tfk_#{attr.name} #{attr.getXmiObject().name}"
        result += this.declareVariable className, "fk_#{attr.name}"
      else
        type = xmiQuery.getChildrenByType(attr, 'uml:Class')[0]
        #console.log "\tfk_#{attr.name} #{type.getXmiObjeclass ClassStream
        result += $this.declareVariable className, "fk_#{attr.name}"
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

      dbHelper = global_dbhelper
      dbHelper = dbHelper.replace global_placeholder, "lamtf.model.#{modelName.ToCamelCase()}"
      dbHelper = dbHelper.replace global_placeholder, "#{modelName.ToCamelCase()}"

      (xmiQuery.getAllNamedClasses p)
      .forEach (mClass)->
        #console.log "Table::#{mClass.name}"
        #        console.log "#{modelName}.#{mClass.name}"
        #console.log mClass.children
        className = mClass.name
        contents = ""

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
            #{className.toUpperCase()}_DIRTY + " BOOLEAN NOT NULL" +\n
        """

        i = 0
        while i < mClass.children.length
          attr = mClass.children[i]
          i++
          decl_vars += $this.declare_static_variable className, attr, 0
          decl_class += $this.declare_field_str className, attr, (i == mClass.children.length)

        contents += decl_vars
        contents += decl_class

        dbHelper = dbHelper.replace global_placeholder, "#{contents}[$]"

      dbHelper = dbHelper.replace global_placeholder, ""
      console.log dbHelper
      return

module.exports = {
  PackageStream: PackageStream
}
