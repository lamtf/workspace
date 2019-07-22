{CHAR_CODE_0,CHAR_CODE_9,CHAR_CODE_C,CHAR_CODE_D,CHAR_CODE_A,
CHAR_CODE_T,CHAR_CODE_X,CHAR_CODE_M,CHAR_CODE_L,CHAR_CODE_Z,
CHAR_CODE_c,CHAR_CODE_d,CHAR_CODE_a,CHAR_CODE_t,CHAR_CODE_x,
CHAR_CODE_m,CHAR_CODE_l,CHAR_CODE_z,CHAR_CODE_SLASH,CHAR_CODE_BACK_SLASH,
CHAR_CODE_LOWER_THAN,CHAR_CODE_GREATHER_THAN,CHAR_CODE_OPEN_SQUARE_BRACES,
CHAR_CODE_CLOSE_SQUARE_BRACES,CHAR_CODE_COLON,CHAR_CODE_PERIOD,
CHAR_CODE_QUESTION_MARK,CHAR_CODE_EXCLAMATION_POINT,CHAR_CODE_UNDERSCORE,
CHAR_CODE_MINUS,CHAR_CODE_EQUAL,CHAR_CODE_TAB,CHAR_CODE_CARRIAGE_RETURN,
CHAR_CODE_LINE_FEED,CHAR_CODE_SPACE,CHAR_CODE_SINGLE_QUOTE,
CHAR_CODE_DOUBLE_QUOTE,SEND_DATA,SEND_END_OF_FILE} = require './constants'

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
    <div><![CDATA[<sexo>Feminino</sexo>]]></div>
    <div class="teste">Hello <i>Brazil</i></div>
  </body>
  </html>
###

str = (x)->
  x.map((y)-> String.fromCharCode y).join("")

###
this class groups some keywords
so it will be easier to identify them
in general keywords are next to some xml data
its used to identify tag-names, the header and
attribute names, attribute values, comments and cdata
the inner-text will be identified as well by
the position they assume, relative to these
so called keywords
###
class XmlCharacterStream

  constructor:()->
    @data = EMPTY.slice(0)
    @type = "XmlCharacterStream"
    Observable.extends @

  flushData:()->
    $this = @
    @data.forEach (d)-> $this.tell [SEND_DATA, [d]]
    @data = EMPTY.slice(0)

  update:(args)->
    if args[0] is SEND_DATA
      if @data.length == 8
        #if @data[0] is CHAR_CODE_LOWER_THAN and @data[1] is CHAR_CODE_EXCLAMATION_POINT and @data[2] is CHAR_CODE_OPEN_SQUARE_BRACES and @data[3] is CHAR_CODE_C and @data[4] is CHAR_CODE_D and @data[5] is CHAR_CODE_A and @data[6] is CHAR_CODE_T and @data[7] is CHAR_CODE_A and args[1] is CHAR_CODE_OPEN_SQUARE_BRACES
        if @data[7] is CHAR_CODE_A and args[1] is CHAR_CODE_OPEN_SQUARE_BRACES
          @data.push CHAR_CODE_OPEN_SQUARE_BRACES
          @tell [SEND_DATA, @data]
          @data = EMPTY.slice(0)
        else
          @flushData()
          # nao sei como vai ser usado
          @data.push args[1]
      else if @data.length == 7
        #if @data[0] is CHAR_CODE_LOWER_THAN and @data[1] is CHAR_CODE_EXCLAMATION_POINT and @data[2] is CHAR_CODE_OPEN_SQUARE_BRACES and @data[3] is CHAR_CODE_C and @data[4] is CHAR_CODE_D and @data[5] is CHAR_CODE_A and @data[6] is CHAR_CODE_T and args[1] is CHAR_CODE_A
        if @data[6] is CHAR_CODE_T and args[1] is CHAR_CODE_A
          @data.push CHAR_CODE_A
      else if @data.length == 6
        #if @data[0] is CHAR_CODE_LOWER_THAN and @data[1] is CHAR_CODE_EXCLAMATION_POINT and @data[2] is CHAR_CODE_OPEN_SQUARE_BRACES and @data[3] is CHAR_CODE_C and @data[4] is CHAR_CODE_D and @data[5] is CHAR_CODE_A and args[1] is CHAR_CODE_T
        if @data[5] is CHAR_CODE_A and args[1] is CHAR_CODE_T
          @data.push CHAR_CODE_T
        else
          @flushData()
          # nao sei como vai ser usado
          @data.push args[1]
      else if @data.length == 5
        #if @data[0] is CHAR_CODE_LOWER_THAN and @data[1] is CHAR_CODE_EXCLAMATION_POINT and @data[2] is CHAR_CODE_OPEN_SQUARE_BRACES and @data[3] is CHAR_CODE_C and @data[4] is CHAR_CODE_D and args[1] is CHAR_CODE_A
        if @data[4] is CHAR_CODE_D and args[1] is CHAR_CODE_A
          @data.push CHAR_CODE_A
        else
          @flushData()
          # nao sei como vai ser usado
          @data.push args[1]
      else if @data.length == 4
        #if @data[0] is CHAR_CODE_LOWER_THAN and @data[1] is CHAR_CODE_QUESTION_MARK and @data[2] is CHAR_CODE_x and @data[3] is CHAR_CODE_m and (args[1] is CHAR_CODE_L or args[1] is CHAR_CODE_l)
        if @data[3] is CHAR_CODE_m and (args[1] is CHAR_CODE_L or args[1] is CHAR_CODE_l)
          @data.push CHAR_CODE_l
          @tell [SEND_DATA, @data]
          @data = EMPTY.slice(0)
        #else if @data[0] is CHAR_CODE_LOWER_THAN and @data[1] is CHAR_CODE_EXCLAMATION_POINT and @data[2] is CHAR_CODE_OPEN_SQUARE_BRACES and @data[3] is CHAR_CODE_C and args[1] is CHAR_CODE_D
        else if @data[3] is CHAR_CODE_C and args[1] is CHAR_CODE_D
          @data.push CHAR_CODE_D
        else
          @flushData()
          # nao sei como vai ser usado
          @data.push args[1]
      else if @data.length == 3
        #if @data[0] is CHAR_CODE_LOWER_THAN and @data[1] is CHAR_CODE_EXCLAMATION_POINT and @data[2] is CHAR_CODE_OPEN_SQUARE_BRACES and args[1] is CHAR_CODE_C
        if @data[2] is CHAR_CODE_OPEN_SQUARE_BRACES and args[1] is CHAR_CODE_C
          @data.push CHAR_CODE_C
        #else if @data[0] is CHAR_CODE_LOWER_THAN and @data[1] is CHAR_CODE_EXCLAMATION_POINT and @data[2] is CHAR_CODE_MINUS
        else if @data[2] is CHAR_CODE_MINUS
          if args[1] is CHAR_CODE_MINUS
            @data.push CHAR_CODE_MINUS
            @tell [SEND_DATA, @data]
            @data = EMPTY.slice(0)
        #else if @data[0] is CHAR_CODE_LOWER_THAN and @data[1] is CHAR_CODE_QUESTION_MARK and @data[2] is CHAR_CODE_x and (args[1] is CHAR_CODE_M or args[1] is CHAR_CODE_m)
        else if @data[2] is CHAR_CODE_x and (args[1] is CHAR_CODE_M or args[1] is CHAR_CODE_m)
          @data.push CHAR_CODE_m
        else
          @flushData()
          # nao sei como vai ser usado
          @data.push args[1]
      else if @data.length == 2
        #if @data[0] is CHAR_CODE_CLOSE_SQUARE_BRACES and @data[1] is CHAR_CODE_CLOSE_SQUARE_BRACES and args[1] is CHAR_CODE_GREATHER_THAN
        if @data[1] is CHAR_CODE_CLOSE_SQUARE_BRACES and args[1] is CHAR_CODE_GREATHER_THAN
          @data.push args[1]
          @tell [SEND_DATA, @data]
          @data = EMPTY.slice(0)
        #else if @data[0] is CHAR_CODE_LOWER_THAN and @data[1] is CHAR_CODE_EXCLAMATION_POINT
        else if @data[1] is CHAR_CODE_EXCLAMATION_POINT
          if args[1] is CHAR_CODE_OPEN_SQUARE_BRACES
            @data.push CHAR_CODE_OPEN_SQUARE_BRACES
          else if args[1] is CHAR_CODE_MINUS
            @data.push CHAR_CODE_MINUS
        #else if @data[0] is CHAR_CODE_MINUS and @data[1] is CHAR_CODE_MINUS and args[1] is CHAR_CODE_GREATHER_THAN
        else if @data[1] is CHAR_CODE_MINUS and args[1] is CHAR_CODE_GREATHER_THAN
          @data.push args[1]
          @tell [SEND_DATA, @data]
          @data = EMPTY.slice(0)
          return
        #else if @data[0] is CHAR_CODE_LOWER_THAN and @data[1] is CHAR_CODE_QUESTION_MARK and (args[1] is CHAR_CODE_X or args[1] is CHAR_CODE_x)
        else if @data[1] is CHAR_CODE_QUESTION_MARK and (args[1] is CHAR_CODE_X or args[1] is CHAR_CODE_x)
          @data.push CHAR_CODE_x
        else
          @flushData()
          # nao sei como vai ser usado
          @data.push args[1]
      else if @data.length == 1
        if @data[0] is CHAR_CODE_CLOSE_SQUARE_BRACES and args[1] is CHAR_CODE_CLOSE_SQUARE_BRACES
          @data.push args[1]
        else if @data[0] is CHAR_CODE_MINUS and args[1] is CHAR_CODE_MINUS
          @data.push args[1]
        else if @data[0] is CHAR_CODE_LOWER_THAN and args[1] is CHAR_CODE_EXCLAMATION_POINT
          @data.push args[1]
        else if @data[0] is CHAR_CODE_LOWER_THAN and args[1] is CHAR_CODE_QUESTION_MARK
          @data.push args[1]
        else if @data[0] is CHAR_CODE_LOWER_THAN and args[1] is CHAR_CODE_SLASH or args[1] is CHAR_CODE_GREATHER_THAN and (@data[0] is CHAR_CODE_SLASH or @data[0] is CHAR_CODE_QUESTION_MARK)
          @tell [SEND_DATA, [@data[0], args[1]]]
          @data = EMPTY.slice(0)
        else if @data[0] is CHAR_CODE_BACK_SLASH
          @tell [SEND_DATA, [@data[0], args[1]]]
          @data = EMPTY.slice(0)
        else
          @tell [SEND_DATA, [@data[0]]]
          @data = EMPTY.slice(0)
          @tell [SEND_DATA, [args[1]]]
      else if @data.length == 0
        if args[1] is CHAR_CODE_MINUS
          @data.push args[1]
        else if args[1] is CHAR_CODE_LOWER_THAN
          # console.log "opening tag with attributes"
          @data.push args[1]
        else if args[1] is CHAR_CODE_GREATHER_THAN
          @tell [SEND_DATA, [args[1]]]
          @data = EMPTY.slice(0)
        else if args[1] is CHAR_CODE_SLASH
          # console.log "send tag"
          @data.push args[1]
        else if args[1] is CHAR_CODE_BACK_SLASH
          @data.push args[1]
        else if args[1] is CHAR_CODE_QUESTION_MARK
          # console.log "send tag"
          @data.push args[1]
          # @status = TAG_TEXT
        else if args[1] is CHAR_CODE_CLOSE_SQUARE_BRACES
          # console.log "send tag"
          @data.push args[1]
        else
          @tell [SEND_DATA, [args[1]]]
    else
      if @data.length > 0
        @flushData()
        @data = EMPTY.slice(0)
      @tell [SEND_END_OF_FILE, null]

module.exports = XmlCharacterStream
