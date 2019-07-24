Observable = require "./Observable"

# all elements have xmi:type
# all elements have xmi:id
# some elements of xmi:type="uml:DataType" have "href=file.ext#internal_id"

class XmiParser
  constructor:()->
    @ids = []
    @stmap = []
    Observable.extends @
  setPrefix:(@prefix)->
    return @
  set:(k,v)->
    @ids[k] = v
  update:(e)->
    that = @
    if e.what is "ADD_PROPERTY"
      if e.key is "xmi:id"
        @ids[e.value] = e.subject
        e.subject.xmiId = e.value
      if e.key.indexOf("base_") > -1
        if not @stmap[e.value]
          @stmap[e.value] = []
        @stmap[e.value].push e.subject
      if e.key is "type"
        e.subject.getXmiObject = ()-> that.getElementById e.value
        e.subject.isFk = true
      else if e.key is "name"
        e.subject.name = e.value
      else if e.key is "xmi:type"
        e.subject.xmiType = e.value
        ###
        if e.value is "uml:Parameter" and not e.subject.getXmiObject?
          e.subject.getXmiObject = ()-> null
        ###
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
                console.error "WARN01 #{$this.getParent().getAttr('name')}.#{$this.getAttr('name')} class has no children"
                $this.fks = []
            $this.fks
          e.subject.getXmiAttributes=()->
            e.subject.children.filter((x)-> x.getXmiObject().xmiType isnt "uml:Class" and x.tagName is "ownedAttribute").map((x)-> x.name)
          e.subject.preProcessXmiNextClassifersForeignKeys=()->
            e.subject.getXmiNextClassifers().forEach (cl) -> cl.getXmiForeignKeys()
  getElementById:(key)-> @ids[key]
  getAppliedStereotypes:(key)-> @stmap[key]

module.exports = XmiParser
