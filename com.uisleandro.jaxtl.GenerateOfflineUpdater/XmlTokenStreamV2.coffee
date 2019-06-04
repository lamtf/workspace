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

###
{EMPTY_STATUS,OPENING_PAYLOAD,XML_HEAD,CLOSING_PAYLOAD,CLOSED_TAG,
OPENING_TAG,OPENING_TAG,CLOSING_TAG,OPENING_COMMENT,
CLOSING_COMMENT,OPENING_CDATA,CLOSING_CDATA,TAG_NAME,
ATTRIBUTE_NAME,ATTRIBUTE_VALUE,SINGLE_QUOTED_ATTRIBUTE_VALUE,
DOUBLE_QUOTED_ATTRIBUTE_VALUE,TAG_HEAD,TAG_CONTENTS,END_TAG} = require './states'
###

{EMPTY_STATUS,OPENING_PAYLOAD,XML_HEAD,CLOSING_PAYLOAD,CLOSED_TAG,
OPENING_TAG,OPENING_TAG,CLOSING_TAG,OPENING_COMMENT,CLOSING_COMMENT,
OPENING_CDATA,CLOSING_CDATA,TAG_NAME,ATTRIBUTE_NAME,ATTRIBUTE_VALUE,
SINGLE_QUOTED_ATTRIBUTE_VALUE,DOUBLE_QUOTED_ATTRIBUTE_VALUE,TAG_HEAD,
TAG_CONTENTS,END_TAG,MASK} = require './states'

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

IGNORE_SPACE = (s)->
  s isnt SINGLE_QUOTED_ATTR_VALUE and s isnt DOUBLE_QUOTED_ATTR_VALUE

IS_SPACE = (s)->
  return (s is CHAR_CODE_SPACE or
  s is CHAR_CODE_TAB or
  s is CHAR_CODE_CARRIAGE_RETURN or
  s is CHAR_CODE_LINE_FEED)

IS_TAGNAME_PREFIX = (s)->
  return (s >= CHAR_CODE_A and s <= CHAR_CODE_Z) or (s >= CHAR_CODE_a and s <= CHAR_CODE_z)

IS_NUMERIC = (s)->
  return (s >= CHAR_CODE_9 and s <= CHAR_CODE_9)

IS_TAGNAME_SUFIX = (s)->
  return IS_NUMERIC(s) or IS_TAGNAME_PREFIX(s) or (s is CHAR_CODE_UNDERSCORE) or (s is CHAR_CODE_COLON) or (s is CHAR_CODE_PERIOD)

str = (s)->
  if s isnt null and typeof(s) isnt "string" and s.length > 0
    val = s.map((x)->String.fromCharCode x).join('')
    return val
  else
    return s

class XmlTokenStreamV2

  constructor:()->
    @data = EMPTY.slice 0
    @ob = []
    @status = @status | EMPTY_STATUS
    @type = "XmlTokenStreamV2"
    new Observable @

  update:(args)->
    $this = @
    v = args[1]
    if (@status & XML_HEAD_ATTRIBUTE_VALUE) > 0
      if IS_SPACE v[0]
        return
      else if v[0] is CHAR_CODE_DOUBLE_QUOTE
        @status = @status | XML_HEAD_DOUBLE_QUOTED_ATTRIBUTE_VALUE
      else if v[0] is CHAR_CODE_SINGLE_QUOTE
        @status = @status | XML_HEAD_SINGLE_QUOTED_ATTRIBUTE_VALUE
      else if v[0] is CHAR_CODE_QUESTION_MARK
        @status = @status | CLOSING_PAYLOAD
    else if (@status & XML_HEAD_ATTRIBUTE_NAME) > 0
      if IS_TAGNAME_SUFIX v[0]
        @data.push v[0]
      else if v[0] is CHAR_CODE_EQUAL
        console.log "XML_HEAD_ATTRIBUTE_VALUE"
        @status = @status | XML_HEAD_ATTRIBUTE_VALUE
      else
        @status = @status | XML_HEAD_ATTRIBUTE
        console.log "XML_HEAD_ATTRIBUTE", str @data
        @data = EMPTY.slice 0
        @status = @status | XML_HEAD_ATTRIBUTE_VALUE
    else if (@status & XML_HEAD) > 0
      if v.length is 2 and v[0] is CHAR_CODE_QUESTION_MARK
        @status = @status | CLOSING_PAYLOAD
        console.log "CLOSING_PAYLOAD", CLOSING_PAYLOAD, str v
      else if IS_TAGNAME_SUFIX v[0]
        @data.push v[0]
        @status = @status | XML_HEAD_ATTRIBUTE_NAME
        console.log "XML_HEAD_ATTRIBUTE_NAME", XML_HEAD_ATTRIBUTE_NAME, str v
    else if (@status & TAG_HEAD) > 0
      if v.length is 1 and v[0] is CHAR_CODE_GREATHER_THAN
        @status = @status | END_TAG
        console.log "END_TAG", str v
      if v[0] is CHAR_CODE_SLASH
        @status = @status | CLOSED_TAG  #<<<< />
        console.log "CLOSED_TAG", CLOSED_TAG, str v
      else
        console.log "TAG_HEAD", str v
    else if (@status & OPENING_TAG) > 0
      if @data.length is 0
        if IS_TAGNAME_PREFIX v[0]
          @data.push v[0]
        else
          console.log "ERROR???", '"',str(v),'"'
      else
        if IS_TAGNAME_SUFIX v[0]
          @data.push v[0]
        else if v[0] is CHAR_CODE_GREATHER_THAN
          console.log "OPENING_TAG", str @data
          @data = EMPTY.slice 0
          @status = @status | END_TAG
        else
          console.log "OPENING_TAG", str @data
          @data = EMPTY.slice 0
          @status = @status | TAG_HEAD
    else if (@status & CLOSING_TAG) > 0
      if @data.length is 0
        if IS_TAGNAME_PREFIX v[0]
          @data.push v[0]
        else
          console.log "ERROR???", '"',str(v),'"'
      else
        if IS_TAGNAME_SUFIX v[0]
          @data.push v[0]
        else if v[0] is CHAR_CODE_GREATHER_THAN
          console.log "CLOSING_TAG", str @data
          @data = EMPTY.slice 0
          @status = @status | END_TAG
    else if (@status & OPENING_COMMENT) > 0
      # console.log "only treats the CLOSING_COMMENT, and return"
      if v.length is 3 and v[2] is CHAR_CODE_GREATHER_THAN
        @status = @status | CLOSING_COMMENT
        console.log "CLOSING_COMMENT", CLOSING_COMMENT, str v
      return
    else if (@status & OPENING_CDATA) > 0
      #console.log "only treats CLOSING_CDATA, and return"
      if v.length is 3 and v[0] is CHAR_CODE_CLOSE_SQUARE_BRACES
        @status = @status | CLOSING_CDATA
        console.log str(@data)
        console.log "CLOSING_CDATA", CLOSING_CDATA, str v
        @data = EMPTY.slice 0
      else
        v.forEach (x)-> $this.data.push x
      return
    else if v.length is 9 # and v[8] is CHAR_CODE_OPEN_SQUARE_BRACES
      @status = @status | OPENING_CDATA
      console.log "OPENING_CDATA", OPENING_CDATA, str v
    else if v.length is 5 # and v[4] is CHAR_CODE_l
      console.log "OPENING_PAYLOAD", OPENING_PAYLOAD, str v
      @status = @status | XML_HEAD
    else if v.length is 4 # and v[3] is CHAR_CODE_MUNIS
      @status = @status | OPENING_COMMENT
      console.log "OPENING_COMMENT", OPENING_COMMENT, str v
    ###
    else if v.length is 3
      if v[0] is CHAR_CODE_CLOSE_SQUARE_BRACES
        @status = @status | CLOSING_CDATA
        console.log "CLOSING_CDATA", CLOSING_CDATA, str v
      else if v[2] is CHAR_CODE_GREATHER_THAN
        @status = @status | CLOSING_COMMENT
        console.log "CLOSING_COMMENT", CLOSING_COMMENT, str v
      # else console.log "XmlTokenStreamV2::errorParsing", str v
    ###
    else if v.length is 2
      if v[0] is CHAR_CODE_SLASH
        @status = @status | CLOSED_TAG  #<<<< />
        console.log "CLOSED_TAG v2", CLOSED_TAG, str v
      ###
      else if v[0] is CHAR_CODE_QUESTION_MARK
        @status = @status | CLOSING_PAYLOAD
        console.log "CLOSING_PAYLOAD", CLOSING_PAYLOAD, str v
      ###
      else if v[1] is CHAR_CODE_SLASH
        @status = @status | CLOSING_TAG
        console.log "CLOSING_TAG", CLOSING_TAG, str v
    else if v.length is 1
      if v[0] is CHAR_CODE_LOWER_THAN
        @status = @status | OPENING_TAG
        console.log "OPENING_TAG", OPENING_TAG, str v
      else if v[0] is CHAR_CODE_GREATHER_THAN
        if @status is OPENING_TAG or @status is CLOSED_TAG
          console.log "TAG_NAME", str @data
          @data = EMPTY.slice 0
        @status = @status | END_TAG # <<< I need different names, like end_opening_tag
        console.log "END_TAG", END_TAG, str v
        # tag contents??
      else if v[0] is CHAR_CODE_EQUAL
        @status = @status | ATTRIBUTE_VALUE
        console.log "ATTRIBUTE_VALUE", ATTRIBUTE_VALUE, str v
      else
        if @status is OPENING_TAG
          console.log "TAG", str V
        else if IS_SPACE v[0]
          console.log "SPACE"
        else
          console.log "TAG_CONTENTS", TAG_CONTENTS, v, str v
          # I should treat here the tag contents
    # else console.log "ERROR???", str v



module.exports = XmlTokenStreamV2
