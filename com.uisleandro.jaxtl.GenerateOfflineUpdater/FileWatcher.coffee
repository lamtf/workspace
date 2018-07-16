fs = require "fs"

class FileWatcher
  constructor:()->
    @bf = null
  setBaseFolder:(@bf)->
    @
  observe:(parser)->
    parser.addObserver @
    return @
  update:(fileName)->
  open:(fileName)->
    if @bf
      fileName = @bf + fileName
    new Promise (accept,reject)->
      fs.readFile fileName, (error,data)->
        if not error
          accept data
        else
          reject error
