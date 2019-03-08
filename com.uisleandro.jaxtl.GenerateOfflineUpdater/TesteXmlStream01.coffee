CharStream = require "./CharStream"
XmlBufferedStream = require "./XmlBufferedStream"
LogStream = require "./LogStream"

charStream = new CharStream("teste01.xml")
xmlBufferedStream = new XmlBufferedStream()
logStream = new LogStream()

xmlTokenStream.observe charStream
logStream.observe xmlBufferedStream

charStream.start()
