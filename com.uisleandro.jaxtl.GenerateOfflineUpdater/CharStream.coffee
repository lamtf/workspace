fs = require "fs"

{ READ, END_OF_FILE, SLASH, LT, GT } = require "./StateMachine"

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

  tell:(a)->
    @ob.forEach((x)-> x.update(a))
    return

  addObserver:(b)->
    @ob.push b
    return

  run:()->
    $this = @
    _stream = fs.createReadStream(@fileName, { highWaterMark: CHUNK_LENGTH })
    chunkNumber = 0
    _stream.on 'data', (chunk)->
      _stream.pause()
      chunkNumber++
      chunkIndex = 0
      if read
        while chunkIndex < CHUNK_LENGTH
          $this.tell([READ, chunk[chunkIndex]])
          chunkIndex++
          # console.log(ByteToString(buffer))
      _stream.resume()

    _stream.on 'error', (err)->
      console.error err

    _stream.on 'end', ()->
      $this.tell([END_OF_FILE, null])
      #console.log "FIHISH READING", chunkNumber
    return

module.exports = CharStream
