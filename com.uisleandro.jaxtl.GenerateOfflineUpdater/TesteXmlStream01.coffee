CharStream = require "./CharStream"
XmlKeywordStream = require "./XmlKeywordStream"
LogStream = require "./LogStream"
pipe = require "./Pipe"

charStream = new CharStream("teste01.xml")
xmlKeywordStream = new XmlKeywordStream()
logStream = new LogStream()

pipe logStream,
xmlKeywordStream,
charStream

charStream.start()
