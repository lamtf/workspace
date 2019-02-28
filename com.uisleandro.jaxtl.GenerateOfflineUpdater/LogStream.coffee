class LogStream
  constructor:()->
  observe:(source)->
    source.addObserver @
  update:(obj)->
    console.log obj
  error:(obj)->
    console.error obj

module.exports = LogStream
