Observable = require "../../streams/Observable"
xmiQuery = require "../../json/XmiQuery"
Util = require "../../json/Util"
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

global_model = """
package %%1%%;
public class %%2%% {
    %%3%%
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

class DbModel
  constructor:()->
    $this = @
    Observable.extends @
  observe:(source)->
    source.addObserver @
  private_var:(attr)->
    type = getType attr
    if attr.isFk
      return """\n    private Long #{attr.getAttr("name").toCamelCase()};"""
    return """\n    private #{java_type(type.getAttr("name"), attr.getAttr("name"))} #{attr.getAttr("name").toCamelCase()};"""
  getSet:(attr)->
    type = "Long"
    Name = attr.getAttr("name").ToCamelCase()
    name = attr.getAttr("name").toCamelCase()#{modelName.ToCamelCase()}Project
    if not attr.isFk
      type = java_type (getType(attr)).getAttr("name"), attr.getAttr("name")
    return """
    \n
        public Long get#{Name}(){
          return #{name};
        }

        public void set#{Name}(Long #{name}){
          this.#{name} = #{name};
        }
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

      (xmiQuery.getAllNamedClasses p)
      .forEach (mClass)->
        #console.log "Table::#{mClass.name}"
        #        console.log "#{modelName}.#{mClass.name}"
        #console.log mClass.children
        className = mClass.name

        dbModel = global_model
        dbModel = dbModel.replace "%%1%%", "#{modelName.ToCamelCase()}Project.model"
        dbModel = dbModel.replace "%%2%%", "#{className.ToCamelCase()}"
        contents = ""

        mClass.children
        .filter((x)->x.tagName is "ownedAttribute")
        .forEach((x)-> contents += $this.private_var x)

        mClass.children
        .filter((x)->x.tagName is "ownedAttribute")
        .forEach((x)-> contents += $this.getSet x)

        dbModel = dbModel.replace "%%3%%", contents

        writeFile("generated/#{modelName.ToCamelCase()}Project/model/#{className.ToCamelCase()}.java", dbModel)

      return

# Its not a module its an entire project

module.exports = DbModel
