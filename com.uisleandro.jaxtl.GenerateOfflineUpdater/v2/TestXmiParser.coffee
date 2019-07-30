CharStream = require "./CharStream"
XmlCharacterStream = require './XmlCharacterStream'
XmlStackStream = require './XmlStackStream'
XmlTokenStream = require './XmlTokenStream'

XmiParser = require './XmiParser'

XmiFileWatcher = require './XmiFileWatcher'
Semaphore = require './Semaphore'

LogStream = require "./LogStream"

pipe = require "./Pipe"



ADD_WORK = 1
REMOVE_WORK = 2

###
class AndThen
  constructor:(@exec)->
  observe:(source)->
    source.addObserver @
  update:(obj)->
    if obj
      @exec obj
  error:(obj)->
    console.error obj

andThen = new AndThen( (event)=>
  if event.what is 0
    console.log "ADD_PROPERTY"
  else if event.what is 1
    console.log "NEW_FILE", event.fileName
  else if event.what is 4294967295
    console.log "END_OF_FILE"
    console.log event.elementById["_9d2ibqoJEee7s7jN2Y2F9A"]
  )
###

logStream = new LogStream()
semaphore = new Semaphore()

xmiFileWatcher = new XmiFileWatcher("./xmi", ($this, fileName)->
  #charStream = new CharStream("./xmi/behavior_model_v4.uml")
  charStream = new CharStream("#{$this.baseFolder}/#{fileName}")
  xmlCharacterStream = new XmlCharacterStream()
  xmlTokenStream = new XmlTokenStream()
  xmlStackStream = new XmlStackStream()
  xmiParser = new XmiParser()

  pipe xmiParser, xmlStackStream, xmlTokenStream, xmlCharacterStream, charStream
  pipe $this, xmlStackStream
  charStream.start()

)

xmiFileWatcher.start("behavior_model_v4.uml");
#xmiFileWatcher.start("basic_structure_v5_modular.uml");

#pipe logStream, semaphore.start(($this)->)
