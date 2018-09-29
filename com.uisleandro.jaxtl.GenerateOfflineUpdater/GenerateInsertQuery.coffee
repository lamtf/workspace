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

# console.log "UML ", uml_model
# console.log target_file

xmiQuery = new XmiQuery()
xmlParser = new XmlParser("#XmlParser")
xmiParser = new XmiParser().observe xmlParser
xmiFileWatcher = new XmiFileWatcher().baseFolderFromFile(uml_model).observe xmlParser
xmiFileWatcher.setXmiParser xmiParser
semaphore = xmiFileWatcher.semaphore()
dependencySort = new DependencySort()

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
  # console.log "FIM DO PROCESSAMENTO"
  # This is the only result
  jsonData = results[0]
  # console.log xmiParser.ids
  root_model = jsonData.children[0]
  mvc_package = xmiQuery.getPackageByName(root_model,'mvc')[0]
  viewClss = xmiQuery.getAllClasses xmiQuery.getPackageByName(mvc_package,'queryViews')

  xmiQuery.getPackageByName(mvc_package,'controller')
  .forEach (mCont)->
    xmiQuery.getAllClasses(mCont)
    #.filter((x)->x.name is "boleto_sicoob")
    .forEach (mClass)->
      ops = xmiQuery.getAllOperations(mClass)
      .filter((op)-> op.name is "example_op")[0]
      console.error "class = #{mClass.name}"
      #console.log "example_op"
      ops.getXmiParams().forEach (p1)->
        if !p1.getXmiObject
            console.error "XmiMissingObjectException at '#{p1.getParent().getParent().getAttr("name")}.#{p1.getParent().getAttr("name")}.#{p1.getAttr("name")}'"
            return null
        t1 = p1.getXmiObject()

        # Processes the Foreign Keys before using it
        t1.preProcessXmiNextClassifersForeignKeys()

        # OVER-HERE we have the view class
        # console.log "view?", t1.name, t1.xmiType


        #console.log "t1 = #{t1.name}"
        # t1.getXmiNextClassifers().forEach (cl1)->
          #console.log "cl1 = #{cl1.name}"
          # cl1.children.forEach (chi1)->
            # clconsole.log "chi-n >>>> #{chi1.name}"
            #console.log chi1

        # temporary double restriction
        # it must be onlu "if t1"

        # I must make it only for a single view
        if t1 and (t1.name is 'db_log_vw')

          console.log "#{t1.name.ToCamelCase()}"

          # console.log 'sorting..'
          sortedData = dependencySort.sort(t1.getXmiNextClassifers())

          sortedData.forEach (cl1)->
          #t1.getXmiNextClassifers().forEach (cl1)->

            # OVER-HERE we have each child class
            #console.log "\t ALWAYS A CLASS"
            #console.log "\t",cl1.name, cl1.xmiType
            console.log "\t", cl1.name.ToCamelCase()

            console.error "\t\tfks:", cl1.getXmiForeignKeys()

            chi = cl1.children
            if chi
              # com isso eu percebo que vou ter que carregar outro arquivo dinamicamente
              # propriedade href -> { name: 'href', value: 'types.uml#_4UmrY39YEeaVP9RPox9M_A' }
              # minha intencao era achar um conjunto de classes.. nao consegui
              chi.forEach (e)->
                #console.log "\t\t", e.tagName, e.name, e.getXmiObject().xmiType, e.getXmiObject().name
                if ! e.isFk
                  console.log "\t\t", e.name.ToCamelCase(), e.getXmiObject().name
                else
                  console.log "\t\t", e.name.ToCamelCase(), "<-", e.getXmiObject().name.ToCamelCase()


      ###
      if ops.length > 0
        console.log "CLASS => #{mClass.name}"
        ops.forEach(
          (op)->
            stereotypes = xmiParser.getAppliedStereotypes op.getAttr "xmi:id"
            if stereotypes.length == 0
              console.log "NO-STEREOTYPES #{op.name}"
            stereotypes.forEach (s) ->
              console.log "<<#{s.tagName}>> #{op.name}"
              if s.tagName is 'query:SelectOneWhere'
                xmiQuery.getAllParameters op
                .forEach (par)->
                  if par.name is "find_by_cpf_in"
                    par.getXmiObject().children.forEach (ch0)->
                      ch0.children.forEach (ch1)->
                        console.log ch1
              if s.tagName is 'query:SelectListWhere'
                console.log "sL"
              if s.tagName is 'query:SelectValueWhere'
                console.log "sv"
              if s.tagName is 'query:ExistsWhere'
                console.log "e1"
              if s.tagName is 'query:Insert'
                console.log "ins"
              if s.tagName is 'query:UpdateWhere'
                console.log "upd"
              if s.tagName is 'query:DeleteWhere'
                console.log "dw"
              if s.tagName is 'query:TooComplexQuery'
                console.log "cpx"
              # quando fazendo o delete, pode ser necessario
              # deletar registros relacionados
              # if s.tagName is 'query:RunBefore'
              #  console.log "rbf"
      ###


              ###
              xmiQuery.getAllParameters(op)
              .forEach (par)->
                xref = par.getAttr("type")
                # elems = xmiQuery.getUmlElementById viewClss, xref
                # view = elems[0]
                view = xmiParser.getElementById xref
                #console.log view
                console.log "PAR #{view.name}"
              ###
