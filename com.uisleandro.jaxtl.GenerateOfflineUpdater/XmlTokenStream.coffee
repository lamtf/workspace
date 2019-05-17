{ DATA,EOF,LT,GT,EQ,SL,SP,TA,CR,LF,SQ,DQ,QM,CHa,CHz,CHA,CHZ,CH0,CH9,U_,DOTS,EXC } = require "./StateMachine"

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

EMPTY_ARRAY = []

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

IGNORE_SPACE = (s)->
  s isnt SINGLE_QUOTED_ATTR_VALUE and s isnt DOUBLE_QUOTED_ATTR_VALUE

IS_SPACE = (s)->
  return s is SP or s is TA or s is CR or s is LF

IS_CHARACTER = (s)->
  return (s >= CHA and s <= CHZ) or (s >= CHa and s <= CHz)

IS_NUMERIC = (s)->
  return (s >= CH0 and s <= CH9)

IS_ALPHA_NUM = (s)->
  return IS_NUMERIC(s) or IS_CHARACTER(s) or (s is U_) or (s is DOTS)

class XmlTokenStream

  constructor:()->
    @data = EMPTY_ARRAY.slice 0
    @ob = []
    @status = 0

  observe:(source)->
    source.addObserver @

  tell:(a)->
    @ob.forEach (x)-> x.update(a)
    return

  addObserver:(b)->
    @ob.push b
    return

  # TODO XML comments
  update:(args)->
    if args[0] is DATA
      if IGNORE_SPACE(@status) and IS_SPACE(args[1])
        return
      if @data.length is 0
        @data[0] = args[1]
        return
      else if @data.length is 1
        if IS_CHARACTER @data[0]
          # segundo caractere
          if IS_CHARACTER args[1]
            @data.push args[1]
            return
          else
            @tell TAG_NAME, @data.map((x)->)
            @data = EMPTY_ARRAY.slice 0
            @data[0] = args[1]
            return;
        else if @data[0] is LT
          # console.log "<"+ String.fromCharCode args[1]
          if args[1] is QM
            @status = OPENING_XML_PAYLOAD_TAG
            @tell(@status, null)
            # console.log "empty data 1"
            @data = EMPTY_ARRAY.slice 0
            return
          else if args[1] is SL
            @status = CLOSED_XML_TAG
            @tell(@status, null)
            # console.log "empty data 2"
            @data = EMPTY_ARRAY.slice 0
            return
          else
            # Do not consumes.. So some 'TELLs' consumes and another don't
            @status = OPENING_XML_TAG
            @tell(@status, null)
            @data = EMPTY_ARRAY.slice 0
            @data[0] = args[1]
            return

            # TODO: It seems here occours a break of string
            # console.log String.fromCharCode args[1]
            # console.log "empty data 3"
        else if @data[0] is QM and args[1] is GT
            @tell CLOSING_XML_PAYLOAD_TAG, null
            @data = EMPTY_ARRAY.slice 0
        else if @data[0] is SL
            @tell CLOSING_XML_TAG, null
            @data = EMPTY_ARRAY.slice 0
        else
            # TODO: possible unused code??
            console.log "POSSIBLE UNUSED CODE NEAR #{String.fromCharCode(@data[0])}>"
            @tell CLOSING_XML_TAG, null
      else
        @data = EMPTY_ARRAY.slice 0
        # adiciona < ou /
        # TODO saber o que quebra o token em cada caso
        @data[0] = args[1]


          ###
      if @data.length is 1
        console.log("1")
        if @data[0] is LT
          if args[1] is QM
            tell(OPENING_XML_PAYLOAD_TAG, null)
            @data = EMPTY_ARRAY
          else if args[1] is SL
            tell(CLOSED_XML_TAG, null)
            @data = EMPTY_ARRAY
          else
            # I must delay it if i find a commenet
            tell(OPENING_XML_TAG, null)
            @data[0] = args[1]
        else if args[1] is GT
            if @data[0] is QM
              tell(CLOSING_XML_PAYLOAD_TAG, null)
            else if @data[0] is SL
              tell(CLOSING_XML_TAG, null)
            else
              # IDK if it will even RUN
              @data[@data.length] = args[1]
        else
    else #EOF
      if @data.length > 0
        @tell([DATA, [@data]])
        @data = EMPTY_ARRAY
      @tell([EOF, null])
    ###

###
if args[0] is DATA
  if @data isnt EMPTY
    if @data is LT and (args[1] is SL or args[1] is QM) or args[1] is GT and (@data is SL or @data is QM)
      @tell([DATA, [@data, args[1]]])
      @data = EMPTY
    else
      @tell([DATA, [@data]])
      @data = EMPTY
      @tell([DATA, [args[1]]])
  else if args[1] is LT
    # console.log "opening tag with attributes"
    @data = args[1]
  else if args[1] is SL
    # console.log "send tag"
    @data = args[1]
  else if args[1] is QM
    # console.log "send tag"
    @data = args[1]
    # @status = TAG_TEXT
  else
    @tell([DATA, [args[1]]])
else #EOF
  if @data isnt EMPTY
    @tell([DATA, [@data]])
    @data = EMPTY
  @tell([EOF, null])
###

module.exports = XmlTokenStream
