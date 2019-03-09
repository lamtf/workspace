{ DATA,EOF,LT,GT,EQ,SL,SP,TA,CR,LF,SQ,DQ,QM,CHa,CHz,CHA,CHZ } = require "./StateMachine"

TAG_NAME = 0|0
ATTRIB_NAME = 2|0
ATTRIB_VALUE = 4|0
TAG_TEXT = 8|0

###
  buffer do ['<','?'] e ['?','>']
###

class XmlBufferedStream

  constructor:()->
    @data = -1
    @ob = []
    @status = TAG_NAME

  observe:(source)->
    source.addObserver @

  tell:(a)->
    @ob.forEach((x)-> x.update(a))
    return

  addObserver:(b)->
    @ob.push b
    return

  update:(args)->
    if args[0] is DATA
      if @data isnt -1
        if @data is LT and (args[1] is SL or args[1] is QM) or args[1] is GT and (@data is SL or @data is QM)
          # console.log "start closing tag"
          @tell([DATA, [@data, args[1]]])
          @data = -1
        else
          @tell([DATA, [@data]])
          @data = -1
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
      if @data isnt -1
        @tell([EOF, [@data]])
        @data = -1
      else
        @tell([EOF, null])

module.exports = XmlBufferedStream
