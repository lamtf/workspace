Observable = require "./Observable"

ADD_PROPERTY = 0
NEW_FILE = 1
END_OF_FILE = 4294967295

class XmiFileWatcher
  constructor:(@baseFolder, @fn)->
    @files = []
    Observable.extends @

  start:(fileName)->
    @fn(@, fileName)

  update:(e)->
    $this = @
    #console.log e.key,"=>", e.value
    if e.what is END_OF_FILE
      fileName = @files[0]
      @files = @files.slice(1)
      @fn(@, fileName)
    else if e.what is ADD_PROPERTY
      #console.log e.key, e.value
      if e.key is "href"
        fileName = e.value.split("#")[0]
        if (fileName.indexOf('pathmap://') is -1) and ((@files[fileName] is false) or (@files[fileName] is undefined))
          if @files.indexOf(fileName) is -1
            @files.push fileName
            console.log fileName
          ###
          @tell {
            what: NEW_FILE
            fileName: fileName
          }
          ###

module.exports = XmiFileWatcher
