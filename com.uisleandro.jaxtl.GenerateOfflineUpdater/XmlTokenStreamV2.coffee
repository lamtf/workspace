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


IGNORE_SPACE = (s)->
  s isnt SINGLE_QUOTED_ATTR_VALUE and s isnt DOUBLE_QUOTED_ATTR_VALUE

IS_SPACE = (s)->
  return (s is CHAR_CODE_SPACE or
  s is CHAR_CODE_TAB or
  s is CHAR_CODE_CARRIAGE_RETURN or
  s is CHAR_CODE_LINE_FEED)

IS_CHARACTER = (s)->
  return (s >= CHAR_CODE_A and s <= CHAR_CODE_Z) or (s >= CHAR_CODE_a and s <= CHAR_CODE_z)

IS_NUMERIC = (s)->
  return (s >= CHAR_CODE_9 and s <= CHAR_CODE_9)

IS_ALPHA_NUM_DOTS_U = (s)->
  return IS_NUMERIC(s) or IS_CHARACTER(s) or (s is CHAR_CODE_UNDERSCORE) or (s is CHAR_CODE_COLON) or (s is CHAR_CODE_PERIOD)

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
    @status = EMPTY_STATUS
    @type = "XmlTokenStreamV2"
    new Observable @

  update:(args)->
    v = args[1]
    if v.length is 9
      console.log "OPENING_CDATA", OPENING_CDATA, str v
    else if v.length is 5
      console.log "OPENING_PAYLOAD", OPENING_PAYLOAD, str v
    else if v.length is 4
      console.log "OPENING_COMMENT", OPENING_COMMENT, str v
    else if v.length is 3
      if v[0] is CHAR_CODE_CLOSE_SQUARE_BRACES
        console.log "CLOSING_CDATA", CLOSING_CDATA, str v
      else
        console.log "CLOSING_COMMENT", CLOSING_COMMENT, str v
    else if v.length is 2
      if v[0] is CHAR_CODE_SLASH
        console.log "CLOSING_TAG", CLOSING_TAG, str v
      if v[1] is CHAR_CODE_SLASH
        console.log "CLOSED_TAG", CLOSED_TAG, str v
    else if v.length is 1
      if v[0] is CHAR_CODE_LOWER_THAN
        console.log "OPENING_TAG", OPENING_TAG, str v
      else if v[0] is CHAR_CODE_GREATHER_THAN
        console.log "CLOSING_TAG", CLOSING_TAG, str v
      else if v[0] is CHAR_CODE_EQUAL
        console.log "ATTRIBUTE_VALUE", ATTRIBUTE_VALUE, str v


module.exports = XmlTokenStreamV2
