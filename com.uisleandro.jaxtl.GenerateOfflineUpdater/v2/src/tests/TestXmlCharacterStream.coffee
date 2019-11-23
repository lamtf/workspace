CharStream = require "../transformation/streams/CharStream"
XmlCharacterStream = require "../transformation/streams/XmlCharacterStream"
LogStream = require "../transformation/streams/LogStream"
pipe = require "../transformation/streams/Pipe"

charStream = new CharStream("teste02.xml")
xmlCharacterStream = new XmlCharacterStream()
logStream = new LogStream()

pipe logStream, xmlCharacterStream, charStream

charStream.start()
