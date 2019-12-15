class GroupStreams
  constructor:(@observables)->
  observe:(source)->
    @observables.forEach (observable)->
      observable.observe source
    return
  addObserver:(b)->
    @observables.forEach (observable)->
      observable.addObserver b
    return
  removeObserver:(b)->
    @observables.forEach (observable)-> observable.removeObserver b
    return
  removeAllObservers:(b)->
    @observables.forEach (observable)-> observable.removeAllObservers()
    return

group = ()->
  return new GroupStreams [... arguments]

module.exports = group
