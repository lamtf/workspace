CharStream = require "./streams/CharStream"
XmlCharacterStream = require "./streams/XmlCharacterStream"
XmlStackStream = require "./streams/XmlStackStream"
XmlTokenStream = require "./streams/XmlTokenStream"

XmiParser = require "./streams/XmiParser"

XmiFileWatcher = require "./streams/XmiFileWatcher"

#Semaphore = require "./streams/Semaphore"

LogStream = require "./streams/LogStream"

#AttributeStream = require "./streams/AttributeStream"

pipe = require "./streams/Pipe"


{PackageStream} = require "./genereting/java_android_sqlite/database.coffee"

log = new LogStream()
#semaphore = new Semaphore()

packageStream = new PackageStream()

xmiParser = new XmiParser()

xmiFileWatcher = new XmiFileWatcher("./data", ($this, fileName)->

  charStream = new CharStream("#{$this.baseFolder}/#{fileName}")
  xmlCharacterStream = new XmlCharacterStream()
  xmlTokenStream = new XmlTokenStream()
  xmlStackStream = new XmlStackStream()

  pipe xmiParser, xmlStackStream, xmlTokenStream, xmlCharacterStream, charStream
  charStream.start()

)

#pipe log, classStream, xmiFileWatcher, xmiParser

# nao vai ser androidTable
pipe packageStream, xmiFileWatcher, xmiParser

xmiFileWatcher.start("behavior_model_v4.uml");
