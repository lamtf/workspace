{ special_sort_for_updating_1, println } = require './Util.coffee'
XmlParser = require './XmlParser.coffee'
XmiQuery = require './XmiQuery.coffee'
XmiParser = require './XmiParser.coffee'
{ config } = require './config.coffee'

fs  = require 'fs'

xmiQuery = new XmiQuery()
xmiParser = new XmlParser()
xmiParser = new XmiParser().observe xmiParser

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
console.log uml_model
console.log target_file

select_query=(what, where)->
  null
#big improve
insert_query=(what)->
  null

update_query=(what, where)->
  null

delete_query=(what)->
  null






# @main
fs.readFile uml_model,(err,data)->
  if err?
    console.log "error reading"
  else
    jsonData = (xmiParser).parse data.toString()
    # console.log jsonData


    ###
    stereos = xmiQuery.getAllStereotypes jsonData
    #console.log stereos.filter((s) -> s.tagName is 'screen:CustomPage')[2]
    stereos
    .filter (s)-> s.tagName.indexOf("query") > -1
    .forEach (s)-> console.log s
    ###

    root_model = jsonData.children[0]
    mvc_package = xmiQuery.getPackageByName(root_model,'mvc')[0]
    viewClss = xmiQuery.getAllClasses xmiQuery.getPackageByName(mvc_package,'queryViews')

    xmiQuery.getPackageByName(mvc_package,'controller')
    .forEach (mCont)->
      xmiQuery.getAllClasses(mCont)
      .filter((x)->x.getAttr("name") is "boleto_sicoob")
      .forEach (mClass)->
        ops = xmiQuery.getAllOperations(mClass)
        .filter((op)-> op.getAttr("name") is "example_op")[0]

        console.log "#{mClass.getAttr("name")}"
        console.log "example_op"
        ops.getXmiParams().forEach (p1)->

          t1 = p1.getXmiType()
          console.log "t1 = #{t1.getAttr("name")}"
          t1.getXmiNextClassifers().forEach (cl1)->
            console.log "cl1 = #{cl1.getAttr("name")}"
            cl1.children.forEach (chi1)->
              console.log "chi-n >>>> #{chi1.getAttr("name")}"
              console.log chi1
          ###
          if t1
            t1.getXmiNextClassifers().forEach (cl1)->
              console.log cl1.getAttr("name")
              chi = cl1.children
              if chi
                # com isso eu percebo que vou ter que carregar outro arquivo dinamicamente
                # propriedade href -> { name: 'href', value: 'types.uml#_4UmrY39YEeaVP9RPox9M_A' }
                # minha intencao era achar um conjunto de classes.. nao consegui
                console.log chi[0]
          ###




        ###
        if ops.length > 0
          console.log "CLASS => #{mClass.getAttr("name")}"
          ops.forEach(
            (op)->
              stereotypes = xmiParser.getAppliedStereotypes op.getAttr "xmi:id"
              if stereotypes.length == 0
                console.log "NO-STEREOTYPES #{op.getAttr("name")}"
              stereotypes.forEach (s) ->
                console.log "<<#{s.tagName}>> #{op.getAttr("name")}"
                if s.tagName is 'query:SelectOneWhere'
                  xmiQuery.getAllParameters op
                  .forEach (par)->
                    if par.getAttr("name") is "find_by_cpf_in"
                      par.getXmiType().children.forEach (ch0)->
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
                  console.log "PAR #{view.getAttr("name")}"
                ###
          #)
          #console.log ""

      #console.log jsonData
# lembrando que eu vou precisar usar o "special_sort_for_updating_1"
# seria bom colocar todos os elementos em um array para buscar pro id
