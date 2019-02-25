{ READ, END_OF_FILE, SLASH, LT, GT } = require "./StateMachine"

class XmlCharTokenizerStream

  constructor:()->
    @data = []
    @ob = []

  observe:(source)->
    source.addObserver @

  tell:(a)->
    @ob.forEach((x)-> x.update(a))
    return

  addObserver:(b)->
    @ob.push b
    return

  update:(args)->
    @data[@data.length] = args[1]
    if args[0] is READ
      if @data.length is 1
        if @data[0] is LT or @data[0] is SLASH
          return # just accumulate
        else
          @tell [0, @data]
          @data = []
      else if @data.length is 2
        else if @data[0] is LT and @data[1] is SLASH or @data[0] is SLASH and @data[1] is GT
          @tell [READ, @data]
          @data = []
          return
        else
          @tell [READ, [@data[0]]]
          @tell [READ, [@data[1]]]
          @data = []
    else
      @tell [END_OF_FILE, []]
