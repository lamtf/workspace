class XmlParser

  constructor:()->
    @observers = []

  addObserver:(o)->
    @observers.push o
    return
  removeObserver:(o)->
    @observers = @observers.filter (x)-> x != o
    return

  tell:(event)->
    @observers.forEach (obs)->
      obs.update(event)
      return
    return

  ### line = 0 ###
  class Stack
    lastid = 0|0
    constructor: ()->
      @data = []
      lastid = 0
    push:(obj) ->
      @data[@data.length] = create_node obj,lastid,if @data.length is 0 then 0 else @data[@data.length - 1]
      lastid++
      return @data[@data.length-1]
    peek:()->
      @data[@data.length-1]
    popcheck:(x)->
      ### check_node_name @data[@data.length-1], x ###
      if @data[@data.length-1].tagName is x
        return @pop()
      else
        throw "error: on #{x} != #{@data[@data.length-1].tagName}"
        ### throw "error: on #{line}: #{x} != #{@data[@data.length-1].tagName}" ###
    check:(x)->
      @data[@data.length-1] == x
    pop:()->
      x = @data[@data.length-1]
      @data.length = @data.length - 1
      if @data.length > 0
        add_children @data[@data.length-1],x
      else
        x
    log:(x)->
      if x?
        if @data[@data.length-1] == x
          console.log "Passed"
        else
          console.log "Failed"
      else
        console.log @data[@data.length-1]

  isOpeningTag = (x,i) ->
    x[i] == '<' and ((x[i+1] >= 'a' and x[i+1] <= 'z') or (x[i+1] >= 'A' and x[i+1] <= 'Z'))
  isClosingTag = (x,i) ->
    x[i] == '<' and x[i+1] == '/'
  isClosedTag = (x,i) ->
    x[i] == '/' and x[i+1] == '>'

  isOpenXmldef = (x,i) ->
    x[i] is '<' and x[i+1] is '?'

  isCloseXmldef = (x,i) ->
    x[i] is '?' and x[i+1] is '>'

  isSpace = (x) ->
    x is ' ' or x is '\r' or x is '\n' or x is '\t'

  getNextProperty = (x,i) ->
    n = ""
    v = ""
    ### novo ###
    if not isSpace x[i]
      return null
    while(isSpace x[i])
      i++
    if x[i] is '/' or x[i] is '>'
      return null
    if (x[i] >= 'a' and x[i] <= 'z') or (x[i] >= 'A' and x[i] <= 'Z')
      while(x[i] isnt ' ' and x[i] isnt '=' and x[i] isnt '/' and x[i] isnt '>')
        n += x[i]
        i++
    while(isSpace x[i])
      i++
    if x[i] is '='
      i++
      while(isSpace x[i])
        i++
      if x[i] is '"'
        i++
        while(x[i] isnt '"')
          v += x[i]
          i++
        i++
    name : n
    value : v
    index : i

  getTagName = (x,i) ->
    tagName = ""
    while(x[i] isnt '>' and x[i] isnt '/' and not isSpace x[i])
      tagName += x[i]
      i++
    tagName : tagName
    index  : i

  ###
  check_node_name = (node,nodeName)->
    node.tagName is nodeName
  ###

  create_node = (nodeName, id, parentNode)->
    tagName : nodeName
    tagId: id
    getParent : ()->
      parentNode
    getAttr:(a)->
      i = 0
      while i < @.properties.length
        if @.properties[i].name is a
          return @.properties[i].value
        i++


  add_children = (parent, child)->
    if parent?
      if not parent.children?
        parent.children = []
      parent.children[parent.children.length] = child
      return parent
    else
      child

  add_property = (element, propertyName, propertyValue)->
    if not element.properties?
      element.properties = []
    element.properties[element.properties.length] =
      name: propertyName
      value : propertyValue
    element

  ###
  get_xml_def=(x,i)->
    _name = ""
    _value = ""
    res = []
    while (x[i] isnt '>' and not (x[i] is '?' and x[i+1] is '>' and isSpace x[i]))
      i++
      while(x[i] isnt '=')
        _name += x[i]

      if x[i] is '='
        i++
        while x[i] isnt '"'
          i++
        while(x[i] isnt '""')
          _value += x[i]
          i++

      if _name isnt "" and _value isnt ""
        res[res.length] = {
          name : _name
          value : _value
        }
    {
      index : i
      properties : res
    }
  ###

  parse : (x)->
    stack = new Stack()
    result = null
    i = 0
    while(i < x.length)

      if isOpenXmldef x,i
        while(!isCloseXmldef x,i)
          i++
        i+=2
      else if isOpeningTag x,i
        i++
        t = getTagName x,i
        stack.push t.tagName
        i = t.index
        ### What is previously on the top be the parent of this new node ###

        prop = getNextProperty x,i

        while(null isnt prop)
          ### If i found a property id be inserting it here ###
          add_property stack.peek(),prop.name,prop.value

          @tell {
            what: "ADD_PROPERTY"
            subject: stack.peek()
            key: prop.name
            value: prop.value
          }

          ### stack.peek()[prop.name]=prop.value ###
          i = prop.index
          prop = getNextProperty x,i

        while(x[i] == ' ')
          i++

        ### nao conseguiu fechar o BR ###

        if isClosedTag x,i
          ### Add the actual node to the parent ###
          result = stack.pop()
          i += 2

      else if isClosingTag x,i
        t = getTagName x,i+2
        i = t.index
        result = stack.popcheck t.tagName
        ### Add the actual node to the parent ###
      else
        while(x[i] is '/' or x[i] is '>' or isSpace x[i])
          i++
        if x[i]? and x[i] isnt '<'
          text = ""
          while(x[i] isnt '<')
            text += x[i]
            i++
            stack.peek()["innerText"]=text
            ### add_property stack.peek(),"innerText",text ###
    return result

### eu preciso ter properties like props = [{name: value}] ###

module.exports = XmlParser
