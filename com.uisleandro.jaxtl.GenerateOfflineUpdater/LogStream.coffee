class LogStream
  constructor:()->
  observe:(source)->
    source.addObserver @
  update:(obj)->
    if obj[1]
      console.log obj[1].map((x)=>String.fromCharCode x).join('')
  error:(obj)->
    console.error obj

module.exports = LogStream
