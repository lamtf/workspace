class XmlParser

  constructor:(@Id)->
    @observers = []
    #console.log "new Instance of XmlParser", @Id

  newInstance:(id)->
    $new = new XmlParser(id)
    @observers.forEach (o)-> $new.addObserver o
    $new

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

  create_node = (nodeName, id, parentNode)->
    tagName : nodeName
    tagId: id
    getParent : ()->
      parentNode
    getAttr:(a)->
      i = 0
      while i < @properties.length
        if @properties[i].name is a
          return @properties[i].value
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

  parse : (x)->
    if x[0] isnt END_OF_FILE

    else
      @tell([END_OF_FILE, null])



    if(x[0] is ' ' or ) return

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
        @stack.push t.tagName
        if @Id is 2
          console.log @Id, "=>", @stack.peek()
        i = t.index
        ### What is previously on the top be the parent of this new node ###

        prop = getNextProperty x,i

        while(null isnt prop)
          ### If i found a property id be inserting it here ###
          add_property @stack.peek(),prop.name,prop.value

          @tell {
            from: @Id
            what: "ADD_PROPERTY"
            subject: @stack.peek()
            key: prop.name
            value: prop.value
          }

          ### @stack.peek()[prop.name]=prop.value ###
          i = prop.index
          prop = getNextProperty x,i

        while(x[i] == ' ')
          i++

        ### nao conseguiu fechar o BR ###

        if isClosedTag x,i
          ### Add the actual node to the parent ###
          result = @stack.pop()
          i += 2

      else if isClosingTag x,i
        t = getTagName x,i+2
        i = t.index
        result = @stack.popcheck t.tagName
        ### Add the actual node to the parent ###
      else
        while(x[i] is '/' or x[i] is '>' or isSpace x[i])
          i++
        if x[i]? and x[i] isnt '<'
          text = ""
          while(x[i] isnt '<')
            text += x[i]
            i++
            try
              @stack.peek()["innerText"]=text
            catch e
              console.log "ERROR", x
              throw e
            ### add_property @stack.peek(),"innerText",text ###
    return result

### eu preciso ter properties like props = [{name: value}] ###

module.exports = XmlParser
