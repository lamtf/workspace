CharStream = require "../streams/CharStream"
XmlCharacterStream = require "../streams/XmlCharacterStream"
LogStream = require "../streams/LogStream"
XmlTokenStream = require "../streams/XmlTokenStream"
pipe = require "../streams/Pipe"
Observable = require "../streams/Observable"
XmlStackStream = require "../streams/XmlStackStream"

{CHAR_CODE_0,CHAR_CODE_9,CHAR_CODE_C,CHAR_CODE_D,CHAR_CODE_A,
CHAR_CODE_T,CHAR_CODE_X,CHAR_CODE_M,CHAR_CODE_L,CHAR_CODE_Z,
CHAR_CODE_c,CHAR_CODE_d,CHAR_CODE_a,CHAR_CODE_t,CHAR_CODE_x,
CHAR_CODE_m,CHAR_CODE_l,CHAR_CODE_z,CHAR_CODE_SLASH,
CHAR_CODE_LOWER_THAN,CHAR_CODE_GREATHER_THAN,
CHAR_CODE_OPEN_SQUARE_BRACES,CHA/R_CODE_CLOSE_SQUARE_BRACES,
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

xmlCharacterStream = new XmlCharacterStream()
xmlTokenStream = new XmlTokenStream()
xmlStackStream = new XmlStackStream()
logStream = new LogStream()

class MemoryCharStream
  constructor:(@data)->
    @i = 0/
    @type = "MemoryCharStream"
    Observable.extends @
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
  <br bla />
  <br bla="bla" />
  <li supported></li><!-- this one did not pass -->
  <li supported ></li>
  <li supported enabled="1"></li>
  <contact name="Contact \\" Name" address='Contact Address'/>
  <![CDATA[<sexo>Feminino</sexo>]]>
  <hastext>text \\"slash something<div>another</div> and another</hastext>
</root>
""")


logs = new LogStream()

pipe logs, xmlStackStream, xmlTokenStream, xmlCharacterStream, memoryCharStream

memoryCharStream.start(500)

console.log JSON.stringify xmlStackStream.stack[0], null, 2

#.children[0].children[8].children
