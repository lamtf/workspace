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
    attr.isFK = true;
    if !type?
      type = attr

  return type.getXmiObject().getAttr("name")

###
  else
    return "fk_"+attr.getXmiObject().getAttr("name")
###

class DbModel
  constructor:()->
    $this = @
    Observable.extends @
  observe:(source)->
    source.addObserver @
  private_var:(attr)->
    console.log getType(attr)
    return ""
  getset:(attr)->
    return ""
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
        dbModel = dbModel.replace "%%1%%", "lamtf.model.#{modelName.toCamelCase()}"
        dbModel = dbModel.replace "%%2%%", "#{modelName}"
        contents = ""

        i = 0
        mClass.children
        .filter((x)->x.tagName is "ownedAttribute")
        .forEach((x)-> $this.private_var x)

        #writeFile("tar/model/#{modelName.toCamelCase()}/DbHelper.java", dbHelper)

      return

module.exports = DbModel
