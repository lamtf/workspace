CharStream = require "./CharStream"
XmlTokenStream = require "./XmlTokenStream"
LogStream = require "./LogStream"

charStream = new CharStream("test01.xml")
xmlTokenStream = new XmlTokenStream()
logStream = new LogStream()

xmlTokenStream.observe charStream
logStream.observe xmlTokenStream
