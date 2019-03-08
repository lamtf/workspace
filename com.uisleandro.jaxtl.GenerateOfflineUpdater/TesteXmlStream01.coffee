CharStream = require "./CharStream"
XmlTokenStream = require "./XmlTokenStream"
LogStream = require "./LogStream"

charStream = new CharStream("teste01.xml")
xmlTokenStream = new XmlTokenStream()
logStream = new LogStream()

xmlTokenStream.observe charStream
logStream.observe xmlTokenStream

charStream.start()
