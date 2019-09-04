Observable = require "./Observable"

# all elements have xmi:type
# all elements have xmi:id
# some elements of xmi:type="uml:DataType" have "href=file.ext#internal_id"

ADD_PROPERTY = 0
NEW_FILE = 1
END_OF_FILE = 4294967295

class XmiParser
  constructor:()->
    @ids = []
    @stereotypes = []
    Observable.extends @
  setPrefix:(@prefix)->
    return @
  set:(k,v)->
    @ids[k] = v
  update:(e)->
    # TODO: identify and tell about new files
    that = @
    if e.what is END_OF_FILE
      @tell {
        what: END_OF_FILE
        elementById: @ids
        appliedStereotypes: @stereotypes
        xml: e.subject
      }
    else if e.what is ADD_PROPERTY
      if e.key is "href"
        @tell e
      if e.key is "xmi:id"
        @ids[e.value] = e.subject
        e.subject.xmiId = e.value
      if e.key.indexOf("base_") > -1
        if not @stereotypes[e.value]
          @stereotypes[e.value] = []
        @stereotypes[e.value].push e.subject
      if e.key is "type"
        e.subject.getXmiObject = ()-> that.getElementById e.value
        e.subject.isFk = true
      else if e.key is "name"
        e.subject.name = e.value
      else if e.key is "xmi:type"
        e.subject.xmiType = e.value
        if e.value is "uml:Property" and not e.subject.getXmiObject?
          e.subject.getXmiObject = ()-> null
          e.subject.isFk = false
        else if e.value is "uml:Operation"
          e.subject.getXmiParams = ()->
            e.subject.children.filter (x)-> x.tagName is "ownedParameter"
        else if e.value is "uml:Class"
          e.subject.getXmiNextClassifers=()->
            e.subject.children.filter (x)-> x.tagName is "nestedClassifier"
          e.subject.getXmiForeignKeys=()->
            $this = e.subject
            if not $this.fks
              if e.subject.children?
                $this.fks = e.subject.children.filter((x)-> x.getXmiObject().xmiType is "uml:Class" and x.tagName is "ownedAttribute").map((x)-> x.name)
              else
                $this.fks = []
            $this.fks
          e.subject.getXmiAttributes=()->
            e.subject.children.filter((x)-> x.getXmiObject().xmiType isnt "uml:Class" and x.tagName is "ownedAttribute").map((x)-> x.name)
          e.subject.preProcessXmiNextClassifersForeignKeys=()->
            e.subject.getXmiNextClassifers().forEach (cl) -> cl.getXmiForeignKeys()


module.exports = XmiParser
