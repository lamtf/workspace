CharStream = require "./CharStream"
XmlCharacterStream = require './XmlCharacterStream'
XmlStackStream = require './XmlStackStream'
XmlTokenStream = require './XmlTokenStream'

XmiParser = require './XmiParser'

LogStream = require "./LogStream"

pipe = require "./Pipe"

charStream = new CharStream("./behavior_model_v4.uml")
xmlCharacterStream = new XmlCharacterStream()
xmlTokenStream = new XmlTokenStream()
xmlStackStream = new XmlStackStream()
logStream = new LogStream()

# TODO: Now i need to check if the tokens are being parsed correctly
# and if no, what is wrong, in which character

pipe logStream, xmlStackStream, xmlTokenStream, xmlCharacterStream, charStream

charStream.start()
