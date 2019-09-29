str = (a)->
  a.map((x)=>String.fromCharCode x).join('')

class LogStream
  constructor:()->
  observe:(source)->
    source.addObserver @
  update:(obj)->
    if obj
      console.log obj #str [obj[1]]
  error:(obj)->
    console.error obj

module.exports = LogStream
