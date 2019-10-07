Observable = require "./Observable"
xmiQuery = require "../json/XmiQuery"

class PackageStream
  constructor:()->
    Observable.extends @
  observe:(source)->
    source.addObserver @
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
        classEvent = {
          "packageName": modelName
          "classObject": mClass
        }
        $this.tell classEvent
    return

    #console.log (dataModels.filter (d)-> d.getParent().name)

    #console.log JSON.stringify obj
    #console.log 'eof'
    #console.log obj.elementById["_OoF08ApVEee_sO_72Fl5KA"]
  error:(obj)->
    console.error obj


module.exports = PackageStream
