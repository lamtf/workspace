Observable = require "./Observable"

ADD_PROPERTY = 0
NEW_FILE = 1
# END_OF_FILE = 4294967295

class XmiFileWatcher
  constructor:(@baseFolder)->
    @files = []
    Observable.extends @
  update:(e)->
    $this = @
    if e.what is ADD_PROPERTY
      if e.key is "href"
        fileName = e.value.split("#")[0]
        if (fileName.indexOf('pathmap://') is -1) and (@files[fileName] is undefined)
          @files[fileName] = true
          @tell {
            what: NEW_FILE
            fileName: fileName
          }

module.exports = XmiFileWatcher
