Observable = require "./Observable"

ADD_PROPERTY = 0
NEW_FILE = 1
END_OF_FILE = 4294967295

class XmiFileWatcher
  constructor:(@fn)->
    @files = []
    @names = []
    @index = 0
    Observable.extends @
    @data = null
    @sent = false

  start:(@fileName)->
    @fn @

  update:(e)->
    $this = @
    #console.log e.key,"=>", e.value
    if e.what is END_OF_FILE
      #console.log "fileName=", e.fileName
      if !@data
        @data = e.xml
      if not @names.find (name)-> name is e.xmiParserfileName
        @names.push e.fileName
        #console.log "A", @files.length
        if @files.length >= @names.length
          @fileName = @files[@index]
          @fn @
          @index++
        else
          @tell {
            what: END_OF_FILE
            elementById: e.elementById
            appliedStereotypes: e.appliedStereotypes
            data: @data
          }
    else if e.what is ADD_PROPERTY
      if e.key is "href"
        fileName = e.value.split("#")[0]
        if (fileName is undefined or (fileName.indexOf('http://') isnt -1) or (fileName.indexOf('https://') isnt -1))
          return

        if (fileName.indexOf('pathmap://') is -1) and ((@files[fileName] is false) or (@files[fileName] is undefined))
          fileName = @fileName.substr(0, @fileName.lastIndexOf("/")+1)+fileName
          if @files.indexOf(fileName) is -1
            @files.push fileName
            return

module.exports = XmiFileWatcher
