#!./node_modules/.bin/coffee

lamtf = require "./transformation/injector"
pipe = require "./transformation/streams/Pipe"

sequence = require "./transformation/streams/Sequence"
group = require "./transformation/streams/Group"

DatabaseHelperForModule = require "./layer/java_android_sqlite/DatabaseHelperForModule.coffee"
DatabaseModel = require "./layer/java_android_sqlite/DatabaseModel.coffee"

ContentProviderForModel = require "./layer/java_android_sqlite/ContentProviderForModel"

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

  sequence xmiParser, xmlStackStream, xmlTokenStream, xmlCharacterStream, charStream

  charStream.start()
)

cp = new ContentProviderForModel(config.android.folder)

###
pipe xmiFileWatcher, xmiParser

pipe cp, xmiFileWatcher

pipe dbHelper, xmiFileWatcher, xmiParser
pipe dbModel, xmiFileWatcher
###


sequence(group(cp, dbHelper, dbModel), xmiFileWatcher, xmiParser)

#sequence group(cp), xmiFileWatcher, xmiParser

xmiFileWatcher.start(config.model.file);
