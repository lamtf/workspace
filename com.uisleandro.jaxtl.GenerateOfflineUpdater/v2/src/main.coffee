lamtf = require "./transformation/injector"
pipe = require "./transformation/streams/Pipe"
DbHelper = require "./layer/java_android_sqlite/DatabaseHelperForModule.coffee"
DatabaseModel = require "./layer/java_android_sqlite/DatabaseModel.coffee"
config = require("./config.json")

dbHelper = new DatabaseHelperForModule(config.android.folder)
dbModel = new DatabaseModel(config.android.folder)

log = lamtf.getLogStream()
xmiParser = lamtf.getXmiParser()
xmiFileWatcher = lamtf.getXmiFileWatcher(config.model.folder, ($this, fileName)->

  charStream = lamtf.getCharStream("#{$this.baseFolder}/#{fileName}")
  xmlCharacterStream = lamtf.getXmlCharacterStream()
  xmlTokenStream = lamtf.getXmlTokenStream()
  xmlStackStream = lamtf.getXmlStackStream()

  pipe xmiParser, xmlStackStream, xmlTokenStream, xmlCharacterStream, charStream

  charStream.start()
)

pipe dbHelper, xmiFileWatcher, xmiParser
pipe dbModel, xmiFileWatcher

xmiFileWatcher.start(config.model.file);
