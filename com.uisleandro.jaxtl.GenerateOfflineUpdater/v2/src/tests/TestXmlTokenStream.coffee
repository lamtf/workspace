CharStream = require "../streams/CharStream"
XmlCharacterStream = require "../streams/XmlCharacterStream"
LogStream = require "../streams/LogStream"
XmlTokenStream = require "../streams/XmlTokenStream"
pipe = require "../streams/Pipe"
Observable = require "../streams/Observable"

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

{NULL,BEGIN_PAYLOAD,TAG_HEAD,ENDING_TAG,COMMENT,BEGIN_CDATA,
LOW_PRIORITY_CONTENT,BEGIN_TAG,END_TAG,END_COMMENT,END_CDATA,
END_PAYLOAD,ENDING_TAG,BEGINNING_TAG,READY_FOR_ATTR,BEGIN_ATTRIBUTE,
SPACE1,SPACE2,DOUBLE_QUOTTED,SINGLE_QUOTTED,END_ATTRIBUTE,
MASK} = require './states'

{TOKEN_BEGIN_XML, TOKEN_EMPTY_ATTR, TOKEN_ATTR_NAME, TOKEN_ATTR_VALUE,
TOKEN_TAG_HEAD, TOKEN_END_TAG, TOKEN_DATA} = require './TokenType'

NOTHING = -1

xmlCharacterStream = new XmlCharacterStream()
xmlTokenStream = new XmlTokenStream()
logStream = new LogStream()

class MemoryCharStream
  constructor:(@data)->
    @i = 0
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

# error In Contact \" Name
# I must send two characters everytime i found \
# then threat it

str = (s)->
  if(s) and typeof(s) isnt "string" and s.length > 0
    val = s.map((x)->String.fromCharCode x).join('')
    return val
  else
    return s

ps = (s)->
  ss = []
  if s & BEGIN_PAYLOAD
    ss.push "BEGIN_PAYLOAD"
  if s & TAG_HEAD
    ss.push "TAG_HEAD"
  if s & ENDING_TAG
    ss.push "ENDING_TAG"
  if s & COMMENT
    ss.push "COMMENT"
  if s & BEGIN_CDATA
    ss.push "BEGIN_CDATA"
  if s & LOW_PRIORITY_CONTENT
    ss.push "LOW_PRIORITY_CONTENT"
  if s & BEGIN_TAG
    ss.push "BEGIN_TAG"
  if s & END_TAG
    ss.push "END_TAG"
  if s & END_COMMENT
    ss.push "END_COMMENT"
  if s & END_CDATA
    ss.push "END_CDATA"
  if s & END_PAYLOAD
    ss.push "END_PAYLOAD"
  if s & ENDING_TAG
    ss.push "END_TAG"
  if s & BEGINNING_TAG
    ss.push "BEGIN_PAYLOAD"
  if s & READY_FOR_ATTR
    ss.push "READY_FOR_ATTR"
  if s & BEGIN_ATTRIBUTE
    ss.push "BEGIN_ATTRIBUTE"
  if s & SPACE1
    ss.push "SPACE1"
  if s & SPACE2
    ss.push "SPACE2"
  if s & DOUBLE_QUOTTED
    ss.push "DOUBLE_QUOTTED"
  if s & SINGLE_QUOTTED
    ss.push "SINGLE_QUOTTED"
  if s & END_ATTRIBUTE
    ss.push "END_ATTRIBUTE"
  console.log "{ \"states\":[#{ss.join()}] }"
  return

tokenName =(t)->
  if t is TOKEN_BEGIN_XML
    return ":TOKEN_BEGIN_XML:"
  if t is TOKEN_EMPTY_ATTR
    return ":TOKEN_EMPTY_ATTR:"
  if t is TOKEN_ATTR_NAME
    return ":TOKEN_ATTR_NAME:"
  if t is TOKEN_ATTR_VALUE
    return ":TOKEN_ATTR_VALUE:"
  if t is TOKEN_TAG_HEAD
    return ":TOKEN_TAG_HEAD:"
  if t is TOKEN_END_TAG
    return ":TOKEN_END_TAG:"
  if t is TOKEN_DATA
    return ":TOKEN_DATA:"

# TODO: the queue test is wrong
class ExpectedResultStream
  constructor:(stream)->
    Observable.extends @
    @stream = stream
    @queue = []
  psOf:(@obj)->
  ps:()->
    ps @obj.status
    console.log @queue

  testQueue:()->
    if @expected is NOTHING
      return
    if @queue.length is 0
      @ps()
      throw "\x1b[31m\x1b[1mError: No data\x1b[0m"
    else if not (@queue[0][0] is @expected)
      @ps()
      throw "\x1b[31m\x1b[1mError: Expecting #{tokenName(@expected)} but got #{tokenName(@queue[0][0])} #{str @queue[0][1]}\x1b[0m"
    else
      console.log '\x1b[32m\x1b[1mPassed: \x1b[0m', "#{@queue[0][0]} #{str @queue[0][1]}"
      @queue = @queue.slice 1, @queue.length
  expect:(n, @expected)->
    if n is 0
      @testQueue()
      return
    @stream.start n
    if @expected isnt NOTHING and !@changed
      @ps()
      throw "\x1b[31m\x1b[1mError: Nothing happend\x1b[0m"
    if @expected is NOTHING
      if @changed
        @ps()
        throw "\x1b[31m\x1b[1mError: Expecting NOTHING, got #{tokenName @queue[0][0]} #{str @queue[0][1]}\x1b[0m"
      console.log "\x1b[32m\x1b[1mPassed, \x1b[36mNO RESULTS\x1b[0m"
    @changed = false
  update:(s)->
    @queue.push s
    if !@changed
      @changed = true
      @testQueue()
  expectChange:()->



expected = new ExpectedResultStream memoryCharStream
expected.psOf xmlTokenStream

logs = new LogStream()

pipe expected, xmlTokenStream, xmlCharacterStream, memoryCharStream

console.log '\x1b[40m\x1b[33mBEGIN TEST CASES \x1b[0m'

expected.expect 5, TOKEN_BEGIN_XML
expected.expect 10, TOKEN_ATTR_NAME
expected.expect 6, TOKEN_ATTR_VALUE
expected.expect 10, TOKEN_ATTR_NAME
expected.expect 7, TOKEN_ATTR_VALUE
expected.expect 3, NOTHING
expected.expect 6, TOKEN_TAG_HEAD
expected.expect 32, NOTHING
expected.expect 5, TOKEN_TAG_HEAD
expected.expect 0, TOKEN_END_TAG
expected.expect 7, TOKEN_TAG_HEAD
expected.expect 6, TOKEN_EMPTY_ATTR
expected.expect 0, TOKEN_END_TAG
expected.expect 7, TOKEN_TAG_HEAD
expected.expect 4, TOKEN_ATTR_NAME
expected.expect 5, TOKEN_ATTR_VALUE
expected.expect 3, TOKEN_END_TAG
expected.expect 7, TOKEN_TAG_HEAD
expected.expect 10, TOKEN_EMPTY_ATTR
expected.expect 5, TOKEN_END_TAG
expected.expect 33, NOTHING
expected.expect 4, TOKEN_TAG_HEAD
expected.expect 11, TOKEN_EMPTY_ATTR
expected.expect 5, TOKEN_END_TAG
expected.expect 7, TOKEN_TAG_HEAD
expected.expect 11, TOKEN_EMPTY_ATTR
expected.expect 7, TOKEN_ATTR_NAME
expected.expect 3, TOKEN_ATTR_VALUE
expected.expect 6, TOKEN_END_TAG
expected.expect 3, NOTHING
expected.expect 9, TOKEN_TAG_HEAD
expected.expect 5, TOKEN_ATTR_NAME
expected.expect 17, TOKEN_ATTR_VALUE
expected.expect 9, TOKEN_ATTR_NAME
expected.expect 17, TOKEN_ATTR_VALUE
expected.expect 3, TOKEN_END_TAG
expected.expect 35, TOKEN_DATA
expected.expect 12, TOKEN_TAG_HEAD
expected.expect 24, TOKEN_DATA
expected.expect 3, TOKEN_TAG_HEAD
expected.expect 9, TOKEN_DATA
expected.expect 5, TOKEN_END_TAG
expected.expect 13, TOKEN_DATA
expected.expect 8, TOKEN_END_TAG
expected.expect 8, TOKEN_END_TAG
#expected.ps()

#expected.expect 5, NOTHING
#expected.expect 5, NOTHING
#expected.expect 5, NOTHING

console.log '\x1b[40m\x1b[33mEND TEST CASES \x1b[0m'
