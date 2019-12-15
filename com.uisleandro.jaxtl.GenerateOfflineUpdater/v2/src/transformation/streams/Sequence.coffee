class SequenceStreams
  constructor:(@maxObservable, @minObserver)->
  observe:(source)->
    source.addObserver @minObserver
    return
  addObserver:(b)->
    @maxObservable.addObserver b
    return
  update:(a)->
    @minObserver.update a
    return
  removeObserver:(b)->
    @maxObservable.removeObserver b
    return
  removeAllObservers:(b)->
    @maxObservable.removeAllObservers()
    return

sequence=()->
  i = 1
  while i < arguments.length
    arguments[i-1].observe arguments[i]
    i++
  if arguments.length is 1
    return new SequenceStreams(arguments[0], arguments[0])
  if arguments.length > 1
    return new SequenceStreams(arguments[0], arguments[arguments.length-1])

module.exports = sequence
