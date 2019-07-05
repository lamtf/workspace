CharStream = require "./CharStream"
XmlKeywordStream = require "./XmlKeywordStream"
LogStream = require "./LogStream"
XmlTokenStream = require "./XmlTokenStream"
pipe = require "./Pipe"

charStream = new CharStream("teste02.xml")
xmlKeywordStream = new XmlKeywordStream()
xmlTokenStream = new XmlTokenStream()
logStream = new LogStream()

pipe logStream,
xmlTokenStream,
charStream

charStream.start()
