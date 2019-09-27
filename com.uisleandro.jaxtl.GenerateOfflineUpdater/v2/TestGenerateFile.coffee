CharStream = require "./CharStream"
XmlCharacterStream = require './XmlCharacterStream'
XmlStackStream = require './XmlStackStream'
XmlTokenStream = require './XmlTokenStream'

XmiParser = require './XmiParser'

XmiFileWatcher = require './XmiFileWatcher'
Semaphore = require './Semaphore'

LogStream = require "./LogStream"

AttributeStream = require "./AttributeStream"

pipe = require "./Pipe"


xmiQuery = require './XmiJsQuery'

logStream = new LogStream()
semaphore = new Semaphore()


class EofStream
  constructor:()->
  observe:(source)->
    source.addObserver @
  update:(obj)->
    #
    #console.log mvc_package
    root = obj.data.children[0].children[0];
    mvc_package = (xmiQuery.getPackageByName root,'mvc')[0]
    console.log mvc_package
    #console.log JSON.stringify obj
    #console.log 'eof'
    #console.log obj.elementById["_OoF08ApVEee_sO_72Fl5KA"]
  error:(obj)->
    console.error obj

eof = new EofStream()


xmiParser = new XmiParser()

xmiFileWatcher = new XmiFileWatcher("./xmi", ($this, fileName)->

  charStream = new CharStream("#{$this.baseFolder}/#{fileName}")
  xmlCharacterStream = new XmlCharacterStream()
  xmlTokenStream = new XmlTokenStream()
  xmlStackStream = new XmlStackStream()

  pipe xmiParser, xmlStackStream, xmlTokenStream, xmlCharacterStream, charStream
  charStream.start()

)

pipe eof, xmiFileWatcher, xmiParser

xmiFileWatcher.start("behavior_model_v4.uml");
