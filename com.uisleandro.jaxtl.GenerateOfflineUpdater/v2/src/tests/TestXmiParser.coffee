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

logStream = new LogStream()
#semaphore = new Semaphore()



class EofStream
  constructor:()->
  observe:(source)->
    source.addObserver @
  update:(obj)->
    #console.log JSON.stringify obj, null, " "
    console.log 'eof'
    #console.log obj.elementById["_OoF08ApVEee_sO_72Fl5KA"]



  error:(obj)->
    console.error obj

eof = new EofStream()

###
TODO: please verify that href appears 1656 times
cat ../xmi/behavior_model_v4.uml | grep href | awk 'BEGIN{X=0} {X=X+1} END{print "x=",X}'
seems not all attributes are showwn

TODO: looking for "appliedProfile" elements

TODO: need to get everything at the end

###
xmiParser = new XmiParser()


xmiFileWatcher = new XmiFileWatcher("./src/model", ($this, fileName)->#
  #charStream = new CharStream("../xmi/behavior_model_v4.uml")
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

  pipe $this, xmiParser, xmlStackStream, xmlTokenStream, xmlCharacterStream, charStream
  #pipe $this, xmlStackStream, xmlTokenStream, xmlCharacterStream, charStream
  charStream.start()

)

pipe eof, xmiFileWatcher


xmiFileWatcher.start("behavior_model_v4.uml");
#xmiFileWatcher.start("basic_structure_v5_modular.uml");

#pipe logStream, semaphore.start(($this)->)
