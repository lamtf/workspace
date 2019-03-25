CharStream = require "./CharStream"
XmlBufferedStream = require "./XmlBufferedStream"
LogStream = require "./LogStream"
pipe = require "./Pipe"

charStream = new CharStream("teste01.xml")
xmlBufferedStream = new XmlBufferedStream()
logStream = new LogStream()

pipe logStream,
xmlBufferedStream,
charStream

charStream.start()
