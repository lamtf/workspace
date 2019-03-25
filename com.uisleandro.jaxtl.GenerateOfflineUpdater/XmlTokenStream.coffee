{ DATA,EOF,LT,GT,EQ,SL,SP,TA,CR,LF,SQ,DQ,QM } = require "./StateMachine"

TAG_NAME = 0|0
ATTRIB_NAME = 2|0
ATTRIB_VALUE = 4|0
TAG_TEXT = 8|0

###
Does it have a stack?
###

class XmlTokenStream

  constructor:()->
    @data = []
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
    ###
    if args[0] is DATA
      console.log args
    ###


module.exports = XmlTokenStream
