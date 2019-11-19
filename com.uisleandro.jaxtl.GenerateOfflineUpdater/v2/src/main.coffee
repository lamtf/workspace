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


DbHelper = require "./genereting/java_android_sqlite/DbHelper.coffee"
DbModel = require "./genereting/java_android_sqlite/DbModel.coffee"

log = new LogStream()
#semaphore = new Semaphore()

dbHelper = new DbHelper()
dbModel = new DbModel()

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
pipe dbHelper, xmiFileWatcher, xmiParser
pipe dbModel, xmiFileWatcher

xmiFileWatcher.start("behavior_model_v4.uml");
