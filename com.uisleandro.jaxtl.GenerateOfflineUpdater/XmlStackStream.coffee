Observable = require "./Observable"

class XmlStackStream
  constructor:()->
    @stack = []
    @ob = []
    Observable.extends @

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
