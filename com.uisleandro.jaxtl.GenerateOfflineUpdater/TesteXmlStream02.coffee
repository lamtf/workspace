CharStream = require "./CharStream"
XmlBufferedStream = require "./XmlBufferedStream"
LogStream = require "./LogStream"
XmlTokenStream = require "./XmlTokenStream"
pipe = require "./Pipe"

charStream = new CharStream("teste01.xml")
xmlBufferedStream = new XmlBufferedStream()
xmlTokenStream = new XmlTokenStream()
logStream = new LogStream()

pipe logStream,
xmlTokenStream,
charStream

charStream.start()
