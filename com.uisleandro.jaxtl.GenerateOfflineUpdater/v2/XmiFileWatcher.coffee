Observable = require "./Observable"

ADD_PROPERTY = 0
NEW_FILE = 1
END_OF_FILE = 4294967295

class XmiFileWatcher
  constructor:(@baseFolder, @fn)->
    @files = []
    Observable.extends @
    @data = null

  start:(fileName)->
    @fn(@, fileName)

  update:(e)->
    $this = @
    #console.log e.key,"=>", e.value
    if (e.what is END_OF_FILE)
      if !@data
        @data = e.xml
      if @files.length > 0
        currentFileName = @files[0]
        @files = @files.slice(1)
        @fn(@, currentFileName)
        return
      else
        @tell {
          what: END_OF_FILE
          elementById: e.elementById
          appliedStereotypes: e.appliedStereotypes
          data: @data
        }
        return
    else if e.what is ADD_PROPERTY
      #console.log e.key, e.value
      if e.key is "href"
        fileName = e.value.split("#")[0]
        if ((fileName.indexOf('http://') isnt -1) or (fileName.indexOf('https://') isnt -1))
          return

        if (fileName.indexOf('pathmap://') is -1) and ((@files[fileName] is false) or (@files[fileName] is undefined))
          if @files.indexOf(fileName) is -1
            @files.push fileName
            return

module.exports = XmiFileWatcher
