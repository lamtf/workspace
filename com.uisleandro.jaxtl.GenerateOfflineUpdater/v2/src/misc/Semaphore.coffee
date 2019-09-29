Observable = require "./Observable"

ADD_WORK = 1
REMOVE_WORK = 2

class Semaphore
  constructor:()->
    @wc = 0
    @results = []
    Observable.extends @
  start:(start)->
    @wc++
    start(@)
    return @
  update:(event)->
    if event.what is ADD_WORK
      @wc++
      console.log "Semaphore::start(#{@wc})"
    else if event.what is REMOVE_WORK
      console.log "Semaphore::end(#{@results.length})"
      if event.value
        @results.push event.value
      if @results.length is @wc
        @tell(@results)

module.exports = Semaphore
