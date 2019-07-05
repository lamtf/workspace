CharStream = require "./CharStream"
XmlKeywordStream = require "./XmlKeywordStream"
LogStream = require "./LogStream"
XmlTokenStreamV2 = require "./XmlTokenStreamV2"
pipe = require "./Pipe"
Observable = require "./Observable"

{CHAR_CODE_0,CHAR_CODE_9,CHAR_CODE_C,CHAR_CODE_D,CHAR_CODE_A,
CHAR_CODE_T,CHAR_CODE_X,CHAR_CODE_M,CHAR_CODE_L,CHAR_CODE_Z,
CHAR_CODE_c,CHAR_CODE_d,CHAR_CODE_a,CHAR_CODE_t,CHAR_CODE_x,
CHAR_CODE_m,CHAR_CODE_l,CHAR_CODE_z,CHAR_CODE_SLASH,
CHAR_CODE_LOWER_THAN,CHAR_CODE_GREATHER_THAN,
CHAR_CODE_OPEN_SQUARE_BRACES,CHAR_CODE_CLOSE_SQUARE_BRACES,
CHAR_CODE_COLON,CHAR_CODE_PERIOD,CHAR_CODE_QUESTION_MARK,
CHAR_CODE_EXCLAMATION_POINT,CHAR_CODE_UNDERSCORE,CHAR_CODE_MUNIS,
CHAR_CODE_EQUAL,CHAR_CODE_TAB,CHAR_CODE_CARRIAGE_RETURN,
CHAR_CODE_LINE_FEED,CHAR_CODE_SPACE,CHAR_CODE_SINGLE_QUOTE,
CHAR_CODE_DOUBLE_QUOTE,SEND_DATA,SEND_END_OF_FILE} = require './constants'

{EMPTY_STATUS,OPENING_PAYLOAD,CLOSING_PAYLOAD,CLOSED_TAG,
OPENING_TAG,CLOSING_TAG,OPENING_COMMENT,CLOSING_COMMENT,
OPENING_CDATA,CLOSING_CDATA,TAG_NAME,ATTRIBUTE_NAME,
ATTRIBUTE_VALUE,SINGLE_QUOTED_ATTRIBUTE_VALUE,
DOUBLE_QUOTED_ATTRIBUTE_VALUE,TAG_CONTENTS} = require './states'

NOTHING = -1

xmlKeywordStream = new XmlKeywordStream()
xmlTokenStream = new XmlTokenStreamV2()
logStream = new LogStream()

class MemoryCharStream
  constructor:(@data)->
    @i = 0
    @type = "MemoryCharStream"
    new Observable @
  update:(s)->
    @tell [SEND_DATA ,s]
  start:(n)->
    if n > @data.length
      n = @data.length
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
  <br/>
  <li supported></li><!-- this one did not pass -->
  <li supported ></li>
  <li supported enabled="1"></li>
  <contact name="Contact \\" Name" address='Contact Address'/>
  <![CDATA[<sexo>Feminino</sexo>]]>
  <hastext>text \\"hahaha<div>another</div> and another</hastext>
</root>
""")

# error In Contact \" Name
# I must send two characters everytime i found \
# then threat it

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



# expected = new ExpectedResultStream memoryCharStream

logs = new LogStream()

pipe logs, xmlTokenStream, xmlKeywordStream, memoryCharStream


memoryCharStream.start(500)

# expected.expect 5, OPENING_XML_PAYLOAD_TAG

###
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
