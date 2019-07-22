{ DATA,EOF,LT,GT,EQ,SL,SP,TA,CR,LF,SQ,DQ,QM,CHa,CHz,CHA,CHZ } = require "./StateMachine"

class XmlParser

  constructor:(@Id)->
    @observers = []
    # console.log "new Instance of XmlParser", @Id

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

  isOpeningTag = (x) ->
    x[1][0] is LT and not x[1][1]
  isClosingTag = (x) ->
    x[1][0] is SL and x[1][1] is GT
  isClosedTag = (x) ->
    x[1][0] is LT and x[1][1] is SL
  isOpenXmldef = (x) ->
    x[1][0] is LT and x[1][1] is QM
  isCloseXmldef = (x) ->
    x[1][0] is QM and x[1][1] is GT
  isSpace = (x) ->
    x[1][0] is SP or x[1][0] is CR or x[1][0] is LN or x[1][0] is TA

  getNextProperty = (x,i) ->
    n = ""
    v = ""
    # novo
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

  # i want to process <b> various <b/> strings </b>
  parse : (x)->
    if x[0] isnt END_OF_FILE
      # ignore spaces
      if isSpace x
        # do nothing
        return
      else if isOpenXmldef x
        # do nothing
        return
      else if isCloseXmldef x
        # do nothing
        return
      else if isOpeningTag x
        # next time it will process the xmlTagName

        # then it will process the properties
        return
      else if isClosedTag x
        # remove it from the stack
      else if isClosingTag x
        # remove it from the stack
        # after processing its name




    else
      @tell([END_OF_FILE, null])

###
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
        # What is previously on the top be the parent of this new node

        prop = getNextProperty x,i

        while(null isnt prop)
          # If i found a property id be inserting it here
          add_property @stack.peek(),prop.name,prop.value

          @tell {
            from: @Id
            what: "ADD_PROPERTY"
            subject: @stack.peek()
            key: prop.name
            value: prop.value
          }

          # @stack.peek()[prop.name]=prop.value
          i = prop.index
          prop = getNextProperty x,i

        while(x[i] == ' ')
          i++

        # nao conseguiu fechar o BR

        if isClosedTag x,i
          # Add the actual node to the parent
          result = @stack.pop()
          i += 2

      else if isClosingTag x,i
        t = getTagName x,i+2
        i = t.index
        result = @stack.popcheck t.tagName
        # Add the actual node to the parent
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
            # add_property @stack.peek(),"innerText",text
    return result

###

# eu preciso ter properties like props = [{name: value}]

module.exports = XmlParser
