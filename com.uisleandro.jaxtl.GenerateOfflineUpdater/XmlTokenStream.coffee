{ READ, END_OF_FILE, LT, GT, EQ, SLASH, SPACE, TAB, _R, _N, Q, QQ } = require "./StateMachine"

TAG_NAME = 0|0
ATTRIB_NAME = 2|0
ATTRIB_VALUE = 4|0
TAG_TEXT = 8|0

class XmlTokenStream

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
    if args[0] is READ
      if @data isnt -1
        if @data is LT and args[1] is SLASH
          # console.log "start closing tag"
          @tell([READ, [@data, args[1]]])
          @data = -1
        else if @data is SLASH and args[1] is GT
          # console.log "send closed tag"
          @tell([READ, [@data, args[1]]])
          @data = -1
        else
          @tell([READ, [@data]])
          @data = -1
          @tell([READ, [args[1]]])
      else if args[1] is LT
        # console.log "opening tag with attributes"
        @data = args[1]
      else if args[1] is SLASH
        # console.log "send tag"
        @data = args[1]
        # @status = TAG_TEXT
      else
        @tell([READ, [args[1]]])
    else #EOF
      if @data isnt -1
        @tell([END_OF_FILE, [@data]])
        @data = -1
      else
        @tell([END_OF_FILE, null])

module.exports = XmlTokenStream
