{ DATA,EOF,LT,GT,EQ,SL,SP,TA,CR,LF,SQ,DQ,QM,CHa,CHz,CHA,CHZ,CH0,CH9,U_,DOTS,EXC,
  CHx, CHm, CHl, CHX, CHM, CHL, MINUS, OPEN_BRACKET, CLOSE_BRACKET } = require "./StateMachine"

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
XML_DOCUMENT = (OPENING_XML_PAYLOAD_TAG){0,1} MAYBE_OPENING_XML_TAG{1,*}

OPENING_XML_PAYLOAD_TAG -> '<?xml' (ATTR_NAME)+ | '?>'

MAYBE_OPENING_XML_TAG -> '<' TAG_NAME | '?>'

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
MAYBE_OPENING_XML_TAG = 16

COMMENT_OR_CDATA = 17
MAYBE_COMMENT = 18

MAYBE_CLOSE_COMMENT1 = 19
MAYBE_CLOSE_COMMENT2 = 20



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

class XmlTokenStream

  constructor:()->
    @data = EMPTY_ARRAY.slice 0
    @ob = []
    @status = EMPTY_STATUS

  observe:(source)->
    source.addObserver @

  tell:(a)->
    @ob.forEach (x)-> x.update(a)
    return

  addObserver:(b)->
    @ob.push b
    return

  emmitEvent:(data)->
    @tell [@status, data]
    console.log String.fromCharCode data
  resetData:(args)->
    @data = EMPTY_ARRAY.slice 0
    @data[0] = args[1]
    @status = EMPTY_STATUS
  emptyData:()->
    @data = EMPTY_ARRAY.slice 0
    @status = EMPTY_STATUS
  emptyDataKeepStatus:()->
    @data = EMPTY_ARRAY.slice 0

  # the next state will be ATTR_VALUE
  handleAttrName:(args)->
    if IS_ALPHA_NUM_DOTS_U args[1]
      @data.push args[1]
      return
    else
      @emmitEvent @data
      @emptyData()
      if not IS_SPACE args[1]
        @update args

  # TODO XML comments
  update:(args)->
    console.log "ch2 '#{str @data}','#{String.fromCharCode(args[1])}'"
    if args[0] is DATA
      #if IGNORE_SPACE(@status) and IS_SPACE(args[1])
      #  return
      if @status is MAYBE_CLOSE_COMMENT2
        if args[1] is GT
          @status = END_XML_COMMENT
      else if @status is MAYBE_CLOSE_COMMENT1
        if args[1] is MINUS
          @status = MAYBE_CLOSE_COMMENT2
      else if @status is BEGIN_XML_COMMENT
        #console.log "BEGIN_XML_COMMENT"
        if args[1] is MINUS
          @status = MAYBE_CLOSE_COMMENT1
      else if @status is MAYBE_COMMENT
        console.log "MAYBE_COMMENT"
        if args[1] is MINUS
          @status = BEGIN_XML_COMMENT
          @emptyDataKeepStatus()
      else if @status is COMMENT_OR_CDATA
        console.log "COMMENT_OR_CDATA"
        if args[1] is MINUS
          @status = MAYBE_COMMENT
      else if @status is OPENING_XML_TAG
        console.log "OPENING_XML_TAG"
        if IS_ALPHA_NUM_DOTS_U args[1]
          @data.push args[1]
        else
          @emmitEvent @data
          @emptyData()
          if args[1] is GT
            @status = CLOSING_XML_TAG
            @emmitEvent null
            @emptyData()
      else if @status is CLOSING_XML_PAYLOAD_TAG
        console.log "CLOSING_XML_PAYLOAD_TAG"
        if args[1] is GT
          @emmitEvent null
          @emptyData()
          console.log "status ="+ @status
          return
      else if @status is DOUBLE_QUOTED_ATTR_VALUE
        if args[1] isnt DQ
          @data.push args[1]
        else
          @emmitEvent @data
          @emptyData()
          @status = ATTR_NAME
        return
      else if @status is OPENING_XML_PAYLOAD_TAG
        if @data.length is 2
          if (args[1] is CHx) or (args[1] is CHX)
            @data.push CHx
            return
          else
            throw "error parsing OPENING_XML_PAYLOAD_TAG, expecting 'x' but got '#{String.fromCharCode args[1]}'"
        if @data.length is 3
          if (args[1] is CHm) or (args[1] is CHM)
            @data.push CHm
            return
          else
            throw "error parsing OPENING_XML_PAYLOAD_TAG, expecting 'm' but got '#{String.fromCharCode args[1]}'"
        if @data.length is 4
          if (args[1] is CHl) or (args[1] is CHL)
            @emmitEvent null
            @emptyData()
            #expected to be
            @status = ATTR_NAME
            return
          else
            throw "error parsing OPENING_XML_PAYLOAD_TAG, expecting 'l' but got '#{String.fromCharCode args[1]}'"
      else if @data.length > 1
        if @status is ATTR_NAME
          @handleAttrName args
      else if @data.length is 1
        if @status is MAYBE_OPENING_XML_TAG
          if args[1] is QM
            @status = OPENING_XML_PAYLOAD_TAG
            @data[1] = args[1]
          else if args[1] is SL
            @status = CLOSED_XML_TAG
            @data[1] = args[1]
          else if args[1] is EXC
            @status = COMMENT_OR_CDATA
          else if IS_CHARACTER args[1]
            @status = OPENING_XML_TAG
            @data[0] = args[1]
        else if @status is ATTR_NAME
          @handleAttrName args
      else if @data.length is 0
        if IS_SPACE args[1]
          console.log "EMPTY_DATA IGNORE_SPACE"
          return
        else if (args[1] is DQ) and (@status is ATTR_VALUE)
          @status = DOUBLE_QUOTED_ATTR_VALUE
        else if args[1] is LT
          @status = MAYBE_OPENING_XML_TAG
          console.log "MAYBE_OPENING_XML_TAG"
          @data[0] = args[1]
        else if args[1] is EQ
          @status = ATTR_VALUE
        else if args[1] is QM
          @status = CLOSING_XML_PAYLOAD_TAG
        else if IS_CHARACTER args[1]
          if @status is ATTR_NAME
            @data[0] = args[1]
          return
        else
          console.log @status
          throw "Error ????"
          return
    return




        ###
        else if IS_CHARACTER args[1]
          @data[0] = args[1]
          if @status isnt ATTR_NAME
            @status = TAG_NAME
          return
        else if IS_PARSEABLE args[1]
          @data[0] = args[1]
          return
        ###




      ###
      else if @data.length > 1
        if @status is TAG_NAME or @status is ATTR_NAME
          if IS_CHARACTER args[1]
            @data.push args[1]
            return
          else if IS_SPACE args[1]
            @emmitEvent @data
            @emptyData()
            @status = ATTR_NAME
          else
            @emmitEvent @data
            @resetData(args)
        else
          throw "Fatal error, Not Parsed '#{String.fromCharCode args[1]}'"
      else if @data.length is 1
        if IS_CHARACTER @data[0]
          console.log "ch3 '#{String.fromCharCode(@data)}','#{String.fromCharCode(args[1])}'"
          # console.log "segundo caractere"
          if IS_CHARACTER args[1]
            @data.push args[1]
            return
          else
            #@status = TAG_NAME
            @emmitEvent null
            @resetData(args)
            return;
        else if @data[0] is LT
          # console.log "<"+ String.fromCharCode args[1]
          if args[1] is QM
            @status = OPENING_XML_PAYLOAD_TAG
            @emmitEvent null
            @emptyData()
            return
          else if args[1] is SL
            @status = CLOSED_XML_TAG
            @emmitEvent null
            @emptyData()
            return
          else
            # Do not consumes.. So some 'TELLs' consumes and another don't
            @status = MAYBE_OPENING_XML_TAG
            @emmitEvent null
            @resetData args
            return

            # TODO: It seems here occours a break of string
            # console.log String.fromCharCode args[1]
            # console.log "empty data 3"
        else if @data[0] is QM and args[1] is GT
            @tell [CLOSING_XML_PAYLOAD_TAG, null]
            @data = EMPTY_ARRAY.slice 0
        else if @data[0] is SL
            @tell [CLOSING_XML_TAG, null]
            @data = EMPTY_ARRAY.slice 0
        else
            # TODO: possible unused code??
            console.log "POSSIBLE UNUSED CODE NEAR #{String.fromCharCode(@data[0])}>"
            @tell [CLOSING_XML_TAG, null]
      else
        @data = EMPTY_ARRAY.slice 0
        # adiciona < ou /
        # TODO saber o que quebra o token em cada caso
        @data[0] = args[1]
      ###


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
            tell(MAYBE_OPENING_XML_TAG, null)
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
