class XmiParser
  constructor:()->
    @ids = []
    @stmap = []
  observe:(parser)->
    parser.addObserver @
    return @

  update:(e)->
    that = @
    if e.what is"ADD_PROPERTY"
      if e.key is "xmi:id"
        @ids[e.value] = e.subject
        e.subject.getXmiId = ()-> e.value
      if e.key.indexOf("base_") > -1
        if not @stmap[e.value]
          @stmap[e.value] = []
        @stmap[e.value].push e.subject
      if e.key is "type"
        e.subject.getXmiType = ()-> that.getElementById e.value
      # if e.subject
      if e.key is "xmi:type" and e.value is "uml:Parameter" and not e.subject.getXmiType?
        e.subject.getXmiType = ()-> null
      if e.key is "xmi:type" and e.value is "uml:Operation"
        e.subject.getXmiParams = ()->
          props = []
          e.subject.children.filter (x)-> x.tagName is "ownedParameter"
      if e.key is "xmi:type" and e.value is "uml:Class"
        e.subject.getXmiNextClassifers=()->
          e.subject.children.filter (x)-> x.tagName is "nestedClassifier"



      ###
      if e.key is "xmi:type" and e.value is "uml:Class"
        if e.subject.getParent().getParent()
      ###

      ###
      what: "ADD_PROPERTY"
      subject: stack.peek()
      key: prop.name
      value: prop.value
      ###
  getElementById:(key)-> @ids[key]
  getAppliedStereotypes:(key)-> @stmap[key]


module.exports = XmiParser
