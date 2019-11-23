CharStream = require "./transformation/streams/CharStream"
XmlCharacterStream = require "./transformation/streams/XmlCharacterStream"
XmlStackStream = require "./transformation/streams/XmlStackStream"
XmlTokenStream = require "./transformation/streams/XmlTokenStream"

XmiParser = require "./transformation/streams/XmiParser"

XmiFileWatcher = require "./transformation/streams/XmiFileWatcher"

#Semaphore = require "./transformation/streams/Semaphore"

LogStream = require "./transformation/streams/LogStream"

#AttributeStream = require "./transformation/streams/AttributeStream"

pipe = require "./transformation/streams/Pipe"


DbHelper = require "./layer/java_android_sqlite/DbHelper.coffee"
DbModel = require "./layer/java_android_sqlite/DbModel.coffee"

log = new LogStream()
#semaphore = new Semaphore()

dbHelper = new DbHelper("out/android")
dbModel = new DbModel("out/android")

xmiParser = new XmiParser()

xmiFileWatcher = new XmiFileWatcher("./src/model", ($this, fileName)->

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
