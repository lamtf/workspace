Observable = require "./Observable"
xmiQuery = require "../json/XmiQuery"

class EofClassStream
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
          "package": modelName
          "class": mClass.name
          "attr": []
        }

        properties = xmiQuery.getChildrenByType mClass, 'uml:Property'
        properties.filter( (x)-> x.name isnt null ).forEach (mProp)->
          #console.log "\t\t#{mProp.name}", mProp
          #console.log "Attr::#{mProp.name}"
          if not mProp.children
            #console.log "Type::#{mProp.getXmiObject().name}"
            attr = {}
            attr[mProp.name] = mProp.getXmiObject().name
            classEvent.attr.push attr
          else
            mType = xmiQuery.getChildrenByTagName mProp, 'type'
            #console.log "Type::#{(mType[0].getXmiObject()).name}"
            attr = {}
            attr[mProp.name] = (mType[0].getXmiObject()).name
            classEvent.attr.push attr
        $this.tell classEvent
    return

    #console.log (dataModels.filter (d)-> d.getParent().name)

    #console.log JSON.stringify obj
    #console.log 'eof'
    #console.log obj.elementById["_OoF08ApVEee_sO_72Fl5KA"]
  error:(obj)->
    console.error obj


module.exports = EofClassStream
