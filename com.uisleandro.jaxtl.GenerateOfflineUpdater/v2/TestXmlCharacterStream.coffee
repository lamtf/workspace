CharStream = require "./CharStream"
XmlCharacterStream = require "./XmlCharacterStream"
LogStream = require "./LogStream"
pipe = require "./Pipe"

charStream = new CharStream("teste02.xml")
xmlCharacterStream = new XmlCharacterStream()
logStream = new LogStream()

pipe logStream,
xmlCharacterStream,
charStream

charStream.start()
