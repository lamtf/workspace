CharStream = require "./CharStream"
XmlKeywordStream = require "./XmlKeywordStream"
LogStream = require "./LogStream"
XmlTokenStream = require "./XmlTokenStream"
pipe = require "./Pipe"
Observable = require "./Observable"

DATA = 0|0
EOF = 0xFFFFFFFF|0

NOTHING = 0

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

#xmlKeywordStream = new XmlKeywordStream()
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
      s = @data[@i].charCodeAt 0
      @update s
      @i = @i + 1
      x = x + 1

memoryCharStream = new MemoryCharStream("""
<?xml version = "1.0" encoding="UTF-8"?>
<root>
  <!-- this is a comment -->
  <contact name="name" address="address"/>
  <![CDATA[<sexo>Feminino</sexo>]]>
  <hastext>text <div>another</div> and another</hastext>
</root>
""")

str = (s)->
  if(s) and typeof(s) isnt "string" and s.length > 0
    val = s.map((x)->String.fromCharCode x).join('')
    return val
  else
    return s

class ExpectedResultStream
  constructor:(stream)->
    new Observable @
    @stream = stream
    @queue = []
  testQueue:()->
    if @expected is NOTHING
      return
    if @queue.length is 0
      throw "Error: No data"
    else if @queue[0][0] isnt @expected
      throw "Error: Expecting #{@expected} but got #{@queue[0][0]} #{str @queue[0][1]}"
    else
      console.log "Passed #{@queue[0][0]} #{str @queue[0][1]}"
    @queue = @queue.slice 1, @queue.length
  expect:(n, @expected)->
    if n is 0
      @testQueue()
      return
    @stream.start n
    if @expected isnt NOTHING and !@changed
      throw "Error: Nothing happend"
    if @expected is NOTHING
      if @changed
       throw "Error: Expecting NOTHING"
      console.log "Passed, NO RESULTS"
    @changed = false
  update:(s)->
    @queue.push s
    if !@changed
      @changed = true
      @testQueue()


  expectChange:()->


expected = new ExpectedResultStream memoryCharStream

pipe expected, xmlTokenStream, memoryCharStream

expected.expect 5, OPENING_XML_PAYLOAD_TAG
expected.expect 9, ATTR_NAME
expected.expect 7, DOUBLE_QUOTED_ATTR_VALUE
expected.expect 10, ATTR_NAME
expected.expect 7, DOUBLE_QUOTED_ATTR_VALUE
expected.expect 2, CLOSING_XML_PAYLOAD_TAG
expected.expect 7, OPENING_XML_TAG
expected.expect 0, CLOSING_XML_TAG
expected.expect 32, NOTHING
expected.expect 9, OPENING_XML_TAG
expected.expect 5, ATTR_NAME
###


###
