CharStream = require "./CharStream"
XmlCharacterStream = require './XmlCharacterStream'
XmlStackStream = require './XmlStackStream'
XmlTokenStream = require './XmlTokenStream'

XmiParser = require './XmiParser'

XmiFileWatcher = require './XmiFileWatcher'
Semaphore = require './Semaphore'

LogStream = require "./LogStream"

#
AttributeStream = require "./AttributeStream"

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



class EofStream
  END_OF_FILE = 4294967295
  constructor:()->
  observe:(source)->
    source.addObserver @
  update:(obj)->
    if obj.what is END_OF_FILE
      console.log "END OF FILE"
      #console.log JSON.stringify obj, null, 2
  error:(obj)->
    console.error obj

eof = new EofStream()

###
TODO: please verify that href appears 1656 times
cat ./xmi/behavior_model_v4.uml | grep href | awk 'BEGIN{X=0} {X=X+1} END{print "x=",X}'
seems not all attributes are showwn

TODO: looking for "appliedProfile" elements

TODO: need to get everything at the end

TODO: this code do not pass through xmiparser

###

xmiParser = new XmiParser()

xmiFileWatcher = new XmiFileWatcher("./xmi", ($this, fileName)->#
  #charStream = new CharStream("./xmi/behavior_model_v4.uml")
  console.log "File=#{$this.baseFolder}/#{fileName}"
  charStream = new CharStream("#{$this.baseFolder}/#{fileName}")
  xmlCharacterStream = new XmlCharacterStream()
  xmlTokenStream = new XmlTokenStream()
  xmlStackStream = new XmlStackStream()


  #pipe xmiParser, xmlStackStream, xmlTokenStream, xmlCharacterStream, charStream
  #pipe $this, xmlStackStream
  #pipe eof, xmlStackStream


  #attr1 = new AttributeStream()

  #pipe eof, xmlStackStream, xmlTokenStream, xmlCharacterStream, charStream

  #pipe $this, attr1, xmlStackStream, xmlTokenStream, xmlCharacterStream, charStream
  #pipe $this, xmlStackStream

  pipe $this, xmlStackStream, xmlTokenStream, xmlCharacterStream, charStream
  charStream.start()

)

pipe eof, xmiFileWatcher


xmiFileWatcher.start("behavior_model_v4.uml");
#xmiFileWatcher.start("basic_structure_v5_modular.uml");

#pipe logStream, semaphore.start(($this)->)
