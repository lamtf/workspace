{ READ, END_OF_FILE, SLASH, LT, GT, EQ, Q, QQ, SPACE, TAB } = require "./StateMachine"

TAG_NAME = 0|0
ATTRIB_NAME = 2|0
ATTRIB_VALUE = 4|0
TAG_TEXT = 8|0

class XmlTokenStream

  constructor:()->
    @data = ""
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
      if args[1].length is 2
        if args[1][0] LT and args[1][1] is SLASH
          # console.log "start closing tag"

          @status = TAG_NAME
        else if args[1][0] is SLASH and args[1][1] is GT
          # console.log "send closed tag"

          @status = TAG_TEXT
      else if args[1][0] is LT
        # console.log "opening tag with attributes"

        @status = TAG_NAME
      else if args[1][0] is GT
        # console.log "send tag"

        @status = TAG_TEXT
      else
        if @status is TAG_NAME and args[1][0] is SPACE
        #content
    else #EOF
