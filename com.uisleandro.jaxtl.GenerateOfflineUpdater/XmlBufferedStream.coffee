{ DATA,EOF,LT,GT,EQ,SL,SP,TA,CR,LF,SQ,DQ,QM,CHa,CHz,CHA,CHZ } = require "./StateMachine"

TAG_NAME = 0|0
ATTRIB_NAME = 2|0
ATTRIB_VALUE = 4|0
TAG_TEXT = 8|0

EMPTY = 0

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
    <div class="teste">Hello <i>Brazil</i></div>
  </body>
  </html>

###

class XmlBufferedStream

  constructor:()->
    @data = EMPTY
    @ob = []

  observe:(source)->
    source.addObserver @

  tell:(a)->
    @ob.forEach (x)-> x.update(a)
    return

  addObserver:(b)->
    @ob.push b
    return

  update:(args)->
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

module.exports = XmlBufferedStream
