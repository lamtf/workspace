class Observable
  constructor:(obj)->
    obj.ob = []
    obj.observe = @.observe
    obj.tell = @.tell
    obj.addObserver = @.addObserver
    # console.log obj.type

  observe:(source)->
    source.addObserver @
    return

  tell:(a)->
    @ob.forEach (x)-> x.update(a)
    return

  addObserver:(b)->
    @ob.push b
    return

  removeObserver:(b)->
    @ob = @ob.filter (x)-> x != b
    return

  removeAllObservers:(b)->
    delete @ob
    @ob = []
    return

# TODO: i need to make some update in the classes which uses this functionality
#  update:($self, args)->
#    $self.run(args)

module.exports = extends: (ob)->
  return new Observable ob
