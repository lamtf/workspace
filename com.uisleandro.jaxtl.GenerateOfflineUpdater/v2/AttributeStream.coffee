Observable = require "./Observable"

class AttributeStream
  constructor:()->
    console.log "new AttributeStream()"
    Observable.extends @

  update:(e)->
    #console.log e
    @tell e

module.exports = AttributeStream
