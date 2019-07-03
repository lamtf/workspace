{CHAR_CODE_0,CHAR_CODE_9,CHAR_CODE_C,CHAR_CODE_D,CHAR_CODE_A,
CHAR_CODE_T,CHAR_CODE_X,CHAR_CODE_M,CHAR_CODE_L,CHAR_CODE_Z,
CHAR_CODE_c,CHAR_CODE_d,CHAR_CODE_a,CHAR_CODE_t,CHAR_CODE_x,
CHAR_CODE_m,CHAR_CODE_l,CHAR_CODE_z,CHAR_CODE_SLASH,
CHAR_CODE_LOWER_THAN,CHAR_CODE_GREATHER_THAN,
CHAR_CODE_OPEN_SQUARE_BRACES,CHAR_CODE_CLOSE_SQUARE_BRACES,
CHAR_CODE_COLON,CHAR_CODE_PERIOD,CHAR_CODE_QUESTION_MARK,
CHAR_CODE_EXCLAMATION_POINT,CHAR_CODE_UNDERSCORE,CHAR_CODE_MINUS,
CHAR_CODE_EQUAL,CHAR_CODE_TAB,CHAR_CODE_CARRIAGE_RETURN,
CHAR_CODE_LINE_FEED,CHAR_CODE_SPACE,CHAR_CODE_SINGLE_QUOTE,
CHAR_CODE_DOUBLE_QUOTE,SEND_DATA,SEND_END_OF_FILE} = require './constants'

{NULL,BEGIN_PAYLOAD,TAG_HEAD,ENDING_TAG,COMMENT,BEGIN_CDATA,
LOW_PRIORITY_CONTENT,BEGIN_TAG,END_TAG,END_COMMENT,END_CDATA,
END_PAYLOAD,ENDING_TAG,BEGINNING_TAG,READY_FOR_ATTR,BEGIN_ATTRIBUTE,
SPACE1,SPACE2,DOUBLE_QUOTTED,SINGLE_QUOTTED,END_ATTRIBUTE,
MASK} = require './states'

Observable = require "./Observable"

EMPTY = []

###
  <?xml version="1.0" encoding="utf-8" ?>
  <html>
    <head>
      <title>Teste</title>
    </head>
    <!-- COMMENT -->
  <body>
    <br />
    <div class="teste">Hello <i>Brazil</i></div>
  </body>
  </html>

###

###
REMOVE_STATE = (s, t)->
  return s & (t^MASK)
###

isSpace = (s)->
  return (s is CHAR_CODE_SPACE or
  s is CHAR_CODE_TAB or
  s is CHAR_CODE_CARRIAGE_RETURN or
  s is CHAR_CODE_LINE_FEED)

isPrefix = (s)->
  return (s >= CHAR_CODE_A and s <= CHAR_CODE_Z) or (s >= CHAR_CODE_a and s <= CHAR_CODE_z)

isNumber = (s)->
  return (s >= CHAR_CODE_9 and s <= CHAR_CODE_9)

isSufix = (s)->
  return isNumber(s) or isPrefix(s) or (s is CHAR_CODE_UNDERSCORE) or (s is CHAR_CODE_COLON) or (s is CHAR_CODE_PERIOD)

str = (s)->
  if s isnt null and typeof(s) isnt "string" and s.length > 0
    val = s.map((x)->String.fromCharCode x).join('')
    return val
  else
    return s

b = (s)->
  return s.toString('2')

class XmlTokenStreamV2

  constructor:()->
    @data = EMPTY.slice 0
    @ob = []
    @status = 0|0
    @type = "XmlTokenStreamV2"
    new Observable @

  flushData:()->
    console.log str @data
    @data = EMPTY.slice 0

  # I must not change the state if its closing
  update:(args)->
    $this = @
    x = 0
    v = args[1]
    if @status & DOUBLE_QUOTTED
      if v[0] is CHAR_CODE_DOUBLE_QUOTE
        if @data[@data.length-1] is CHAR_CODE_SLASH
          @data[@data.length-1] = v[0]
          return
        else
          @flushData()
          @status = @status & (DOUBLE_QUOTTED^MASK)
          return
      else
        @data.push v[0]
        return
    else if @status & SINGLE_QUOTTED
      if v[0] is CHAR_CODE_SINGLE_QUOTE
        if @data[@data.length-1] is CHAR_CODE_SLASH
          @data[@data.length-1] = v[0]
          return
        else
          @flushData()
          @status = @status & (SINGLE_QUOTTED^MASK)
          return
      else
        @data.push v[0]
        return
    else if @status & SPACE2
      if isSpace v[0]
        return
      else if v[0] is CHAR_CODE_DOUBLE_QUOTE
        @status = (@status & (SPACE2^MASK)) | DOUBLE_QUOTTED
        return
      else if v[0] is CHAR_CODE_SINGLE_QUOTE
        @status = (@status & (SPACE2^MASK)) | SINGLE_QUOTTED
        return
    else if @status & SPACE1
      if isSpace v[0]
        return
      else if v[0] is CHAR_CODE_EQUAL
        @status = (@status & (SPACE1^MASK)) | SPACE2
        return
      else if isPrefix v[0]
        @flushData()
        @data.push v[0]
        @status = (@status & (SPACE1^MASK)) | BEGIN_ATTRIBUTE
        return
      else
        @status = @status & (SPACE1^MASK)
    else if @status & BEGIN_ATTRIBUTE
      if isSufix v[0]
        @data.push v[0]
        return
      else if isSpace v[0]
        @flushData()
        @status = (@status & (BEGIN_ATTRIBUTE^MASK)) | SPACE1
        return
      else if v[0] is CHAR_CODE_EQUAL
        @flushData()
        @status = (@status & (BEGIN_ATTRIBUTE^MASK)) | SPACE2
        return
    else if @status & READY_FOR_ATTR
      if isPrefix v[0]
        @data.push v[0]
        # @status - READY_FOR_ATTR + BEGIN_ATTRIBUTE
        @status = (@status & (READY_FOR_ATTR^MASK)) | BEGIN_ATTRIBUTE
        return
      else
        # @status - READY_FOR_ATTR
        @status = @status & (READY_FOR_ATTR^MASK)
    ###
    SEGUNDA PARTE
    ###
    if @status & BEGIN_CDATA
      if v.length is 3 and v[0] is CHAR_CODE_CLOSE_SQUARE_BRACES
        @flushData()
        console.log "]]>"
        @status = @status & (BEGIN_CDATA^MASK)
      else
        @data.push v[0]
    else if @status & COMMENT
      if v.length is 3 and v[0] is CHAR_CODE_MINUS
        console.log "-->"
        @status = @status & (COMMENT^MASK)
    else if @status & BEGIN_PAYLOAD
      if v.length is 2 and v[0] is CHAR_CODE_QUESTION_MARK
        console.log "?>"
        @status = @status & (BEGIN_PAYLOAD^MASK)
      else
        if isSpace v[0]
          @status = @status | READY_FOR_ATTR
    else if @status & TAG_HEAD
      if isPrefix v[0]
        @data.push v[0]
      else if isSufix v[0] and @data.length > 0
        @data.push v[0]
      else if v.length is 2 and v[0] is CHAR_CODE_SLASH
        console.log "/>"
        @status = @status & (TAG_HEAD^MASK)
        console.log "TAG_FOOTER"
        @flushData()
      else if v.length is 1 and v[0] is CHAR_CODE_GREATHER_THAN
        console.log ">"
        @status = @status & (TAG_HEAD^MASK)
        console.log "TAG_HEAD"
        @flushData()
      else
        console.log "?2", str v
    else
      if v.length is 9 and v[2] is CHAR_CODE_OPEN_SQUARE_BRACES
        console.log '<![CDATA['
        @status = @status | BEGIN_CDATA
      else if v.length is 5 and v[2] is CHAR_CODE_x
        console.log '<?xml'
        @status = @status | BEGIN_PAYLOAD
      else if v.length is 4 and v[2] is CHAR_CODE_MINUS
        console.log "<!--"
        @status = @status | COMMENT
      else if v.length is 2 and v[0] is CHAR_CODE_LOWER_THAN
        console.log "</"
        @status = @status | ENDING_TAG
      else if v.length is 1 and v[0] is CHAR_CODE_LOWER_THAN
        console.log "<"
        @status = @status | TAG_HEAD




module.exports = XmlTokenStreamV2
