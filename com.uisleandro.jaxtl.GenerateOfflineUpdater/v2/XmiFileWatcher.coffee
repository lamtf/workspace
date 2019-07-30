Observable = require "./Observable"

ADD_PROPERTY = 0
NEW_FILE = 1
# END_OF_FILE = 4294967295

class XmiFileWatcher
  constructor:(@baseFolder, @fn)->
    @files = []
    Observable.extends @

  start:(fileName)->
    @fn(@, fileName)

  update:(e)->
    $this = @
    if e.what is ADD_PROPERTY
      if e.key is "href"
        fileName = e.value.split("#")[0]
        if (fileName.indexOf('pathmap://') is -1) and ((@files[fileName] is false) or (@files[fileName] is undefined))
          @files[fileName] = true
          @fn($this, fileName)
          ###
          @tell {
            what: NEW_FILE
            fileName: fileName
          }
          ###

module.exports = XmiFileWatcher
