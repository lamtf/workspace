fs = require "fs"

{ DATA,EOF,LT,GT,EQ,SL,SP,TA,CR,LF,SQ,DQ,QM } = require "./StateMachine"

Observable = require "./Observable"

class CharStream
  CHUNK_LENGTH = 0|0
  _stream = null
  read = false
  chunkNumber = 0|0
  chunkIndex = 0|0

  constructor:(@fileName)->
    read = true
    #CHUNK_LENGTH = 16384
    CHUNK_LENGTH = 4096
    @ob = []
    Observable.extends @
  ###
  tell:(a)->
    @ob.forEach((x)-> x.update(a))
    return

  addObserver:(b)->
    @ob.push b
    return
  ###

  start:()->
    $this = @
    _stream = fs.createReadStream(@fileName, { highWaterMark: CHUNK_LENGTH })
    chunkNumber = 0
    _stream.on 'data', (chunk)->
      _stream.pause()
      chunkNumber++
      chunkIndex = 0
      if read
        while typeof(chunk[chunkIndex]) isnt 'undefined' and chunkIndex < CHUNK_LENGTH
          $this.tell([DATA, chunk[chunkIndex]])
          chunkIndex++
          # console.log(ByteToString(buffer))
      _stream.resume()

    _stream.on 'error', (err)->
      console.error err

    _stream.on 'end', ()->
      $this.tell([EOF, null])
      #console.log "FIHISH READING", chunkNumber
    return

module.exports = CharStream
