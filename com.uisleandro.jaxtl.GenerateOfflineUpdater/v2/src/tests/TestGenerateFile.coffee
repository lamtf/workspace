CharStream = require "../transformation/streams/CharStream"
XmlCharacterStream = require "../transformation/streams/XmlCharacterStream"
XmlStackStream = require "../transformation/streams/XmlStackStream"
XmlTokenStream = require "../transformation/streams/XmlTokenStream"

XmiParser = require "../transformation/streams/XmiParser"

XmiFileWatcher = require "../transformation/streams/XmiFileWatcher"

#Semaphore = require "../transformation/streams/Semaphore"

LogStream = require "../transformation/streams/LogStream"

#AttributeStream = require "../transformation/streams/AttributeStream"

pipe = require "../transformation/streams/Pipe"

xmiQuery = require "../json/XmiQuery"

logStream = new LogStream()
#semaphore = new Semaphore()


class EofStream
  constructor:()->
  observe:(source)->
    source.addObserver @
  update:(obj)->
    #
    #console.log mvc_package
    root = obj.data.children[0].children[0];
    #root.children.forEach (x)-> console.log x.name
    mvcPackage = (xmiQuery.getPackageByName root,'mvc')[0]
    dataModelsPackage = (xmiQuery.getPackageByName mvcPackage, 'dataModels')
    dataModelsPackage.forEach (p)->
      modelName = p.getParent().name
      console.log modelName
      (xmiQuery.getAllNamedClasses p)
      .forEach (mClass)->
        console.log "Table::#{mClass.name}"
        properties = xmiQuery.getChildrenByType mClass, 'uml:Property'
        properties.filter( (x)-> x.name isnt null ).forEach (mProp)->
          #console.log "\t\t#{mProp.name}", mProp
          console.log "Attr::#{mProp.name}"
          if not mProp.children
            console.log "=>#{mProp.getXmiObject().name}"
          else
            mType = xmiQuery.getChildrenByTagName mProp, 'type'
            console.log "Type::#{(mType[0].getXmiObject()).name}"

    #console.log (dataModels.filter (d)-> d.getParent().name)

    #console.log JSON.stringify obj
    #console.log 'eof'
    #console.log obj.elementById["_OoF08ApVEee_sO_72Fl5KA"]
  error:(obj)->
    console.error obj

eof = new EofStream()

xmiParser = new XmiParser()

xmiFileWatcher = new XmiFileWatcher("./src/model", ($this, fileName)->

  charStream = new CharStream("#{$this.baseFolder}/#{fileName}")
  xmlCharacterStream = new XmlCharacterStream()
  xmlTokenStream = new XmlTokenStream()
  xmlStackStream = new XmlStackStream()

  pipe xmiParser, xmlStackStream, xmlTokenStream, xmlCharacterStream, charStream
  charStream.start()

)

pipe eof, xmiFileWatcher, xmiParser

xmiFileWatcher.start("behavior_model_v4.uml");
