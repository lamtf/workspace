{ special_sort_for_updating_1, println } = require './Util'
XmlParser = require './XmlParser'
XmiQuery = require './XmiQuery'
XmiParser = require './XmiParser'
{ config } = require './config'
XmiFileWatcher = require './XmiFileWatcher'
# Semaphore = require './Semaphore'
DependencySort = require './DependencySort3'

fs  = require 'fs'

uml_model = config.in
target_file = config.out

i = 0
while i < process.argv.length
  if process.argv[i] is "-x"
    uml_model = process.argv[i+1]
    i++
  else if process.argv[i].indexOf("-x") is 0
    uml_model = process.argv[i].substr(2)
  else if process.argv[i] is "-o"
    target_file = process.argv[i+1]
    i++
  else if process.argv[i].indexOf("-o") is 0
    target_file = process.argv[i].substr(2)
  i++

xmiQuery = new XmiQuery()
xmlParser = new XmlParser("#XmlParser")
xmiParser = new XmiParser().observe xmlParser
xmiFileWatcher = new XmiFileWatcher().baseFolderFromFile(uml_model).observe xmlParser
xmiFileWatcher.setXmiParser xmiParser
semaphore = xmiFileWatcher.semaphore()
dependencySort = new DependencySort()

class GenerateQuery
  registerPlugin:(plugin)->
    plugin.configure(@)
  # injectInsert:(@databaseInsert)->
  injectHandleOperation:(@handleOperation)->
  run:()->
    $this = @
    semaphore.begin ()->
      #console.log "BEGIN reading File1 #{uml_model}"
      fs.readFile uml_model,(err,data)->
        #console.log "END reading File1 #{uml_model}"
        if err?
          #console.log "error reading"
        else
          jsonData = xmlParser.parse(data.toString())
          semaphore.end(jsonData)

    semaphore.then (results)->
      jsonData = results[0]
      root_model = jsonData.children[0]
      mvc_package = xmiQuery.getPackageByName(root_model,'mvc')[0]
      viewClss = xmiQuery.getAllClasses xmiQuery.getPackageByName(mvc_package,'queryViews')
      xmiQuery.getPackageByName(mvc_package,'controller')
      .forEach (mCont)->
        xmiQuery.getAllClasses(mCont)
        #.filter((x)->x.name is "boleto_sicoob")
        .forEach (mClass)->
          mOperations = xmiQuery.getAllOperations(mClass)
          # .filter((op)-> op.name is "open_cash_register")[0]
          # console.error "class = #{mClass.name}"
          mOperations.forEach (mOperation)->
            inParameter = null;
            outParameter = null;
            mOperation.getXmiParams().forEach (mParameter)->
              if !mParameter.getXmiObject
                  console.error "XmiMissingObjectException at '#{mParameter.getParent().getParent().getAttr("name")}.#{mParameter.getParent().getAttr("name")}.#{mParameter.getAttr("name")}'"
                  return null
              mObject = mParameter.getXmiObject()
              mObject.preProcessXmiNextClassifersForeignKeys()
              if mObject
                mObject.sortedData = dependencySort.sort mObject.getXmiNextClassifers()
              if mParameter.getAttr("name").indexOf("_in") > -1
                inParameter = mObject
              if mParameter.getAttr("name").indexOf("_out") > -1
                outParameter = mObject

            stereotypes = xmiParser.getAppliedStereotypes mOperation.getAttr "xmi:id"
            $this.handleOperation mOperation.name, inParameter, outParameter, stereotypes


module.exports = GenerateQuery
