fs = require "fs"
XmlParser = require './XmlParser'
# XmiParser = require './XmiParser'

class XmiFileWatcher
  constructor:(@xmiParser)->
    @baseFolder = null
    @processing = []
  baseFolderFromFile:(@baseFolder)->
    @baseFolder = @baseFolder.substring 0, @baseFolder.lastIndexOf("/")+1
    console.log "BASE_FOLDER", @baseFolder
    @
  observe:(xmlParser)->
    xmlParser.addObserver @
    @parserInstance = xmlParser.newInstance "#XmiFileWatcher"
    return @
  update:(e)->
    $this = @
    if e.from is "#XmiFileWatcher"
      if e.what is "ADD_PROPERTY"
        if e.key is "xmi:id"
          xmiId = "types.uml#"+e.value
          #console.log xmiId
          $this.xmiParser.ids[xmiId] = e.subject
          e.subject.getXmiId = ()-> xmiId
          #console.log $this.xmi.ids[xmiId]
    else
      if e.key is "href"
        # identificar se ja foi processado
        fileName = e.value.split("#")[0]
        if not $this.processing[fileName]
          #if (fileName.indexOf('pathmap://') is -1) and (fileName.indexOf('.profile') is -1)
          if (fileName.indexOf('types.uml') > -1)
            fs.readFile "#{$this.baseFolder}#{fileName}", (error, data)->
              $this.parserInstance.parse data.toString()
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
