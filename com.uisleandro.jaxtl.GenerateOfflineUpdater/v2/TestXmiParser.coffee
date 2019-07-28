CharStream = require "./CharStream"
XmlCharacterStream = require './XmlCharacterStream'
XmlStackStream = require './XmlStackStream'
XmlTokenStream = require './XmlTokenStream'

XmiParser = require './XmiParser'

XmiFileWatcher = require './XmiFileWatcher'

LogStream = require "./LogStream"

pipe = require "./Pipe"

charStream = new CharStream("./xmi/behavior_model_v4.uml")
xmlCharacterStream = new XmlCharacterStream()
xmlTokenStream = new XmlTokenStream()
xmlStackStream = new XmlStackStream()
xmiFileWatcher = new XmiFileWatcher("xmi")

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
  else
    console.log "END_OF_FILE"
    console.log event.elementById["_9d2ibqoJEee7s7jN2Y2F9A"]
  )

logStream = new LogStream()
xmiParser = new XmiParser()

pipe andThen, xmiParser, xmlStackStream, xmlTokenStream, xmlCharacterStream, charStream
pipe andThen, xmiFileWatcher, xmlStackStream

charStream.start()
