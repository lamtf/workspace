{ DATA,EOF,LT,GT,EQ,SL,SP,TA,CR,LF,SQ,DQ,QM,CHa,CHz,CHA,CHZ,CH0,CH9,U_,DOTS,EXC,
  CHx, CHm, CHl, CHX, CHM, CHL, MINUS, OPEN_BRACKET, CLOSE_BRACKET, CHC, CHD, CHT
   } = require "./StateMachine"

Observable = require "./Observable"


TAG_NAME = 0|0
ATTRIB_NAME = 2|0
ATTRIB_VALUE = 4|0
TAG_TEXT = 8|0

EMPTY = []

###
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
class XmlKeywordStream

  constructor:()->
    @data = EMPTY.slice(0)
    @type = "XmlKeywordStream"
    new Observable @

  flushData:()->
    $this = @
    @data.forEach (d)-> $this.tell [DATA, [d]]
    @data = EMPTY.slice(0)

  update:(args)->
    if args[0] is DATA
      if @data.length == 8
        if @data[0] is LT and @data[1] is EXC and @data[2] is OPEN_BRACKET and @data[3] is CHC and @data[4] is CHD and @data[5] is CHA and @data[6] is CHT and @data[7] is CHA and args[1] is OPEN_BRACKET
          @data.push OPEN_BRACKET
          @tell([DATA, @data])
          @data = EMPTY.slice(0)
        else
          @flushData()
          # nao sei como vai ser usado
          @data.push args[1]
      else if @data.length == 7
        if @data[0] is LT and @data[1] is EXC and @data[2] is OPEN_BRACKET and @data[3] is CHC and @data[4] is CHD and @data[5] is CHA and @data[6] is CHT and args[1] is CHA
          @data.push CHA
      else if @data.length == 6
        if @data[0] is LT and @data[1] is EXC and @data[2] is OPEN_BRACKET and @data[3] is CHC and @data[4] is CHD and @data[5] is CHA and args[1] is CHT
          @data.push CHT
        else
          @flushData()
          # nao sei como vai ser usado
          @data.push args[1]
      else if @data.length == 5
        if @data[0] is LT and @data[1] is EXC and @data[2] is OPEN_BRACKET and @data[3] is CHC and @data[4] is CHD and args[1] is CHA
          @data.push CHA
        else
          @flushData()
          # nao sei como vai ser usado
          @data.push args[1]
      else if @data.length == 4
        if @data[0] is LT and @data[1] is QM and @data[2] is CHx and @data[3] is CHm and (args[1] is CHL or args[1] is CHl)
          @data.push CHl
          @tell([DATA, @data])
          @data = EMPTY.slice(0)
        else if @data[0] is LT and @data[1] is EXC and @data[2] is OPEN_BRACKET and @data[3] is CHC and args[1] is CHD
          @data.push CHD
        else
          @flushData()
          # nao sei como vai ser usado
          @data.push args[1]
      else if @data.length == 3
        if @data[0] is LT and @data[1] is EXC and @data[2] is OPEN_BRACKET and args[1] is CHC
          @data.push CHC
        else if @data[0] is LT and @data[1] is EXC and @data[2] is MINUS
          if args[1] is MINUS
            @data.push args[1]
            @tell([DATA, @data])
            @data = EMPTY.slice(0)
        else if @data[0] is LT and @data[1] is QM and @data[2] is CHx and (args[1] is CHM or args[1] is CHm)
          @data.push CHm
        else
          @flushData()
          # nao sei como vai ser usado
          @data.push args[1]
      else if @data.length == 2
        if @data[0] is CLOSE_BRACKET and @data[0] is CLOSE_BRACKET and args[1] is GT
          @data.push args[1]
          @tell([DATA, @data])
          @data = EMPTY.slice(0)
        if @data[0] is LT and @data[1] is EXC
          if args[1] is OPEN_BRACKET
            @data.push args[1]
          else if args[1] is MINUS
            @data.push args[1]
        else if @data[0] is MINUS and @data[1] is MINUS and args[1] is GT
          @data.push args[1]
          @tell([DATA, @data])
          @data = EMPTY.slice(0)
        else if @data[0] is LT and @data[1] is QM and (args[1] is CHX or args[1] is CHx)
          @data.push CHx
        else
          @flushData()
          # nao sei como vai ser usado
          @data.push args[1]
      else if @data.length == 1
        if @data[0] is CLOSE_BRACKET and args[1] is CLOSE_BRACKET
          @data.push args[1]
        else if @data[0] is MINUS and args[1] is MINUS
          @data.push args[1]
        else if @data[0] is LT and args[1] is EXC
          @data.push args[1]
        else if @data[0] is LT and args[1] is QM
          @data.push args[1]
        else if @data[0] is LT and args[1] is SL or args[1] is GT and (@data[0] is SL or @data[0] is QM)
          @tell([DATA, [@data[0], args[1]]])
          @data = EMPTY.slice(0)
        else
          @tell([DATA, [@data[0]]])
          @data = EMPTY.slice(0)
          @tell([DATA, [args[1]]])
      else if @data.length == 0
        if args[1] is MINUS
          @data.push args[1]
        else if args[1] is LT
          # console.log "opening tag with attributes"
          @data.push args[1]
        else if args[1] is SL
          # console.log "send tag"
          @data.push args[1]
        else if args[1] is QM
          # console.log "send tag"
          @data.push args[1]
          # @status = TAG_TEXT
        else if args[1] is CLOSE_BRACKET
          # console.log "send tag"
          @data.push args[1]
        else
          @tell([DATA, [args[1]]])
    else #EOF
      if @data.length > 0
        @tell([DATA, [@data[0]]])
        @data = EMPTY.slice(0)
      @tell([EOF, null])

module.exports = XmlKeywordStream
