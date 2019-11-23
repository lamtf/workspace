CharStream = require "./streams/CharStream"
XmlCharacterStream = require "./streams/XmlCharacterStream"
XmlStackStream = require "./streams/XmlStackStream"
XmlTokenStream = require "./streams/XmlTokenStream"
XmiParser = require "./streams/XmiParser"
XmiFileWatcher = require "./streams/XmiFileWatcher"
LogStream = require "./streams/LogStream"

module.exports = {
  getCharStream:(fileName)->
    new CharStream(fileName)
  getXmlCharacterStream:()->
    new XmlCharacterStream()
  getXmlStackStream:()->
    new XmlStackStream()
  getXmlTokenStream:()->
    new XmlTokenStream()
  getXmiParser:()->
    new XmiParser()
  getXmiFileWatcher:(models, callback)->
    new XmiFileWatcher(models, callback)
  getLogStream:()->
    new LogStream()
}
