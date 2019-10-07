Observable = require "../../streams/Observable"
xmiQuery = require "../../json/XmiQuery"
Util = require "../../json/Util"

class PackageStream
  constructor:()->
    Observable.extends @
  observe:(source)->
    source.addObserver @

  declareClass:(packageName, contents)->
    """
    public class #{packageName.ToCamelCase()} {
      #{contents}
    }
    """

  declareTable:(className)->
    """

      public static final String TABLE_#{className.toUpperCase()} = "#{className}";
      public static final String #{className.toUpperCase()}_ID = "id";
      public static final String #{className.toUpperCase()}_UUID = "uuid";
      public static final String #{className.toUpperCase()}_DIRTY = "dirty";

    """

  declareField:(className,propertyName)->
    """
    public static final String #{className.toUpperCase()}_#{propertyName.toUpperCase()} = "#{propertyName}";

    """

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

      (xmiQuery.getAllNamedClasses p)
      .forEach (mClass)->
        #console.log "Table::#{mClass.name}"
        #        console.log "#{modelName}.#{mClass.name}"
        #console.log mClass.children
        clsssName = mClass.name
        contents = $this.declareTable clsssName

        mClass.children.forEach (attr)->
          if attr.name
            #console.log "  #{attr.name}"
            type = xmiQuery.getChildrenByType(attr, 'uml:PrimitiveType')[0]
            if type
              #console.log "\t#{attr.name} #{type.getXmiObject().name}"
              contents += $this.declareField clsssName, attr.name
            else
              if attr.getXmiObject()
                #console.log "\tfk_#{attr.name} #{attr.getXmiObject().name}"
                contents += $this.declareField clsssName, "fk_#{attr.name}"
              else
                type = xmiQuery.getChildrenByType(attr, 'uml:Class')[0]
                #console.log "\tfk_#{attr.name} #{type.getXmiObjeclass ClassStream
                contents += $this.declareField clsssName, "fk_#{attr.name}"
      console.log $this.declareClass modelName, contents

module.exports = {
  PackageStream: PackageStream
}
