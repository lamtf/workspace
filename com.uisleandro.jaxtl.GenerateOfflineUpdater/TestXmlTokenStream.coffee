CharStream = require "./CharStream"
XmlBufferedStream = require "./XmlBufferedStream"
LogStream = require "./LogStream"
XmlTokenStream = require "./XmlTokenStream"
pipe = require "./Pipe"
Observable = require "./Observable"

DATA = 0|0
EOF = 0xFFFFFFFF|0

OPENING_XML_PAYLOAD_TAG = 1 #
CLOSING_XML_PAYLOAD_TAG = 2 #

OPENING_XML_TAG = 3 #
CLOSING_XML_TAG = 4

CLOSED_XML_TAG = 5 #

TAG_NAME = 6
TAG_SPACE = 7
ATTR_NAME = 8

SINGLE_QUOTED_ATTR_VALUE = 9
DOUBLE_QUOTED_ATTR_VALUE = 10

CHILD_TEXT = 11

BEGIN_XML_COMMENT = 12 # ????????????? <!--
END_XML_COMMENT = 13 # ?????????????? -->

#xmlBufferedStream = new XmlBufferedStream()
xmlTokenStream = new XmlTokenStream()
logStream = new LogStream()

class MemoryCharStream
  constructor:(@data)->
    new Observable @
    @i = 0
  update:(s)->
    @tell [DATA ,s]
  start:(n)->
    x = 0
    while x < n
      @update @data[@i].charCodeAt 0
      @i = @i + 1
      x = x + 1

# what about <secao><![CDATA[<sexo>Feminino</sexo>]]><secao> ???

memoryCharStream = new MemoryCharStream("""
<?xml version="1.0" encoding="UTF-8"?>
<root>
  <!-- this is a comment -->
  <contact name="name" address="address"/>
  <hastext>text <div>another</div> and another</hastext>
</root>
""")

class ExpectedResultStream
  constructor:(stream)->
    new Observable @
    @stream = stream
    @i = 0
  expect:(n, @expected)->
    @stream.start n
    if !@changed
      throw "Error: Nothing happend"
    @changed = false
  update:(s)->
    @changed = true
    @i = @i - 1
    if s isnt @expected
      throw "Error: Expecting #{@expected} but got #{s}"
    else
      console.log "Passed"
  expectChange:()->


expected = new ExpectedResultStream memoryCharStream

pipe expected, xmlTokenStream, memoryCharStream

expected.expect 2, OPENING_XML_PAYLOAD_TAG
expected.expect 3, TAG_NAME
