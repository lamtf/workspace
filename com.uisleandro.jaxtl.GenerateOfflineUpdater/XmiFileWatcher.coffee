fs = require "fs"
XmlParser = require './XmlParser'
Semaphore = require './Semaphore'

class XmiFileWatcher
  constructor:()->
    @baseFolder = null
    @processing = []
  baseFolderFromFile:(@baseFolder)->
    @baseFolder = @baseFolder.substring 0, @baseFolder.lastIndexOf("/")+1
    # console.log "BASE_FOLDER", @baseFolder
    @
  observe:(xmlParser)->
    xmlParser.addObserver @
    @parserInstance = xmlParser.newInstance "#XmiFileWatcher"
    return @
  semaphore:()->
    @semaphore = new Semaphore()
    return @semaphore
  setXmiParser:(@xmiParser)->
  update:(e)->
    $this = @
    if e.from is "#XmiFileWatcher"
      if e.what is "ADD_PROPERTY"
        if e.key is "xmi:id"
          # <<<<<<<<<<<<<<<<<<<<<<<<<<<  corrigir
          xmiId = e.value
          #console.log xmiId
          $this.xmiParser.set xmiId, e.subject
          e.subject.xmiId = xmiId
          #console.log $this.xmi.ids[xmiId]
    else if e.from is "#XmlParser"
      if e.key is "href"
        val = e.value.split("#")[1]
        e.subject.getParent().getXmiObject = ()-> $this.xmiParser.getElementById val
        # identificar se ja foi processado
        fileName = e.value.split("#")[0]
        if not $this.processing[fileName]
          try
            if (fileName.indexOf('pathmap://') is -1) and (fileName.indexOf('.profile') is -1)
              # console.log "BEGIN reading File #{$this.baseFolder}#{fileName}"
              if $this.semaphore
                $this.semaphore.begin()
              fs.readFile "#{$this.baseFolder}#{fileName}", (error, data)->
                # console.log "END reading File #{$this.baseFolder}#{fileName}"
                $this.parserInstance.parse data.toString()
                if $this.semaphore
                  $this.semaphore.end()
                # console.log $this.xmiParser
          catch e
          $this.processing[fileName] = true;
        # A partir daqui o arquivo foi tratado

    ###
    what: "ADD_PROPERTY"
    subject: stack.peek()
    key: prop.name
    value: prop.value
    ###
  open:(fileName)->
    if @baseFolder
      fileName = @baseFolder + fileName
    new Promise (accept,reject)->
      fs.readFile fileName, (error,data)->
        if not error
          accept data
        else
          reject error

module.exports =XmiFileWatcher
