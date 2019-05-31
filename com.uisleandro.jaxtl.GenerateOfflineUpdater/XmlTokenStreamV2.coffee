{ DATA,EOF,LT,GT,EQ,SL,SP,TA,CR,LF,SQ,DQ,QM,CHa,CHz,CHA,CHZ,CH0,CH9,U_,DOTS,EXC,
  CHx, CHm, CHl, CHX, CHM, CHL, MINUS, OPEN_BRACKET, CLOSE_BRACKET } = require "./StateMachine"

Observable = require "./Observable"

###

  CH0, CH9, UNL, DT

  [ ['<','?'],['?','>'],['<','/'],['/','>'] ]
  new: ['<','!','-','-'],['-','-','>']
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
XML_DOCUMENT = (OPENING_XML_PAYLOAD_TAG){0,1} OPENING_XML_OR_PAYLOAD_OR_COMMENT{1,*}

OPENING_XML_PAYLOAD_TAG -> '<?xml' (ATTR_NAME)+ | '?>'

OPENING_XML_OR_PAYLOAD_OR_COMMENT -> '<' TAG_NAME | '?>'

TAG_NAME -> (ATTR_NAME){1,*}
ATTR_NAME -> ATTR_VALUE

###


EMPTY_ARRAY = []

EMPTY_STATUS = 0

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

IGNORE_SPACES = 14

ATTR_VALUE = 15
OPENING_XML_OR_PAYLOAD_OR_COMMENT = 16

COMMENT_OR_CDATA = 17
BEGIN_XML_COMMENT_EXPECT_MINUS = 18

END_XML_COMMENT_EXPECT_MINUS = 19
END_XML_COMMENT_EXPECT_GT = 20

READY_FOR_ATTRIBUTE = 21


IGNORE_SPACE = (s)->
  s isnt SINGLE_QUOTED_ATTR_VALUE and s isnt DOUBLE_QUOTED_ATTR_VALUE

IS_SPACE = (s)->
  return s is SP or s is TA or s is CR or s is LF

IS_CHARACTER = (s)->
  return (s >= CHA and s <= CHZ) or (s >= CHa and s <= CHz)

IS_NUMERIC = (s)->
  return (s >= CH0 and s <= CH9)

IS_ALPHA_NUM_DOTS_U = (s)->
  return IS_NUMERIC(s) or IS_CHARACTER(s) or (s is U_) or (s is DOTS)

IS_PARSEABLE = (s)->
  return (s is LT) or (s is SL)

str = (s)->
  if s isnt null and typeof(s) isnt "string" and s.length > 0
    val = s.map((x)->String.fromCharCode x).join('')
    return val
  else
    return s

class XmlTokenStreamV2

  constructor:()->
    @data = EMPTY_ARRAY.slice 0
    @ob = []
    @status = EMPTY_STATUS
    @type = "XmlTokenStreamV2"
    new Observable @

  update:(args)->
    console.log args


module.exports = XmlTokenStreamV2
