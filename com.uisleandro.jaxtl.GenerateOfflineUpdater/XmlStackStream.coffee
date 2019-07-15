Observable = require "./Observable"

{CHAR_CODE_0,CHAR_CODE_9,CHAR_CODE_C,CHAR_CODE_D,CHAR_CODE_A,
CHAR_CODE_T,CHAR_CODE_X,CHAR_CODE_M,CHAR_CODE_L,CHAR_CODE_Z,
CHAR_CODE_c,CHAR_CODE_d,CHAR_CODE_a,CHAR_CODE_t,CHAR_CODE_x,
CHAR_CODE_m,CHAR_CODE_l,CHAR_CODE_z,CHAR_CODE_SLASH,
CHAR_CODE_LOWER_THAN,CHAR_CODE_GREATHER_THAN,
CHAR_CODE_OPEN_SQUARE_BRACES,CHAR_CODE_CLOSE_SQUARE_BRACES,
CHAR_CODE_COLON,CHAR_CODE_PERIOD,CHAR_CODE_QUESTION_MARK,
CHAR_CODE_EXCLAMATION_POINT,CHAR_CODE_UNDERSCORE,CHAR_CODE_MUNIS,
CHAR_CODE_EQUAL,CHAR_CODE_TAB,CHAR_CODE_CARRIAGE_RETURN,
CHAR_CODE_LINE_FEED,CHAR_CODE_SPACE,CHAR_CODE_SINGLE_QUOTE,
CHAR_CODE_DOUBLE_QUOTE,SEND_DATA,SEND_END_OF_FILE} = require './constants'

{TOKEN_BEGIN_XML, TOKEN_EMPTY_ATTR, TOKEN_ATTR_NAME, TOKEN_ATTR_VALUE,
TOKEN_TAG_HEAD, TOKEN_END_TAG, TOKEN_DATA} = require './TokenType'

str = (s)->
  if s isnt null and typeof(s) isnt "string" and s.length > 0
    val = s.map((x)->String.fromCharCode x).join('')
    return val
  else
    return s

class XmlStackStream
  constructor:()->
    @stack = []
    Observable.extends @
    @nodeId = 0
    @xml = null

  push:(data)->
    @stack.push data
    return
  popCheck:(tagName)->
    # TODO: veryfy the current node
    child = @peek()
    parent2 = @stack[@stack.length-2]
    if child? and child.tagName is tagName
      @stack.pop()
      parent = @peek()
      addChild parent, child
    else
      console.error "ERROR CLOSING TAG #{tagName}, #{JSON.stringify parent2}"


  peek:(data)->
    return @stack[@stack.length-1]

  createNode:(nodeName, parentNode)->
    @stack.push {
      tagName : nodeName
      tagId: @nodeId
      getParent : ()->
        parentNode
      getAttr:(a)->
        i = 0
        while i < @properties.length
          if @properties[i].name is a
            return @properties[i].value
          i++
    }

  showStack:()->
    i = @stack.length-1
    while i > 0
      console.log @peek()
      i--

  addChild = (parent, child)->
    if parent?
      if not parent.children?
        console.log "reset??"
        parent.children = []
      #console.log "addChild01", parent, child
      parent.children.push child
      #console.log "addChild02", parent
      return parent
    else
      return child

  addPropertyName:(propertyName)->
    element = @peek()
    if not element.properties?
      element.properties = []
    element.properties.push { name: propertyName }
    return

  addPropertyValue:(propertyName)->
    element = @peek()
    if not element.properties?
      element.properties = []
    element.properties[element.properties.length-1].value = propertyName
    return

  addContents:(strContents)->
    element = @peek()
    if not element.contents?
      element.contents = []
    element.contents.push strContents
    return

  update:(args)->
    #console.log args
    if args[0] is TOKEN_BEGIN_XML
      @createNode "xml", null
      @nodeId = @nodeId + 1
      @xml = @peek()
    else if args[0] is TOKEN_EMPTY_ATTR
      @addPropertyName str args[1]
      @addPropertyValue true
    else if args[0] is TOKEN_ATTR_NAME
      @addPropertyName str args[1]
    else if args[0] is TOKEN_ATTR_VALUE
      @addPropertyValue str args[1]
    else if args[0] is TOKEN_TAG_HEAD
      console.log "BEGIN TAG", str(args[1])
      @createNode str(args[1]), str args[1]
      @nodeId = @nodeId + 1
    else if args[0] is TOKEN_END_TAG
      console.log "END TAG", str(args[1])
      @popCheck str(args[1])
    else if args[0] is TOKEN_DATA
      @addContents str(args[1])
    else
      console.log args[0], str args[1]
    #console.log "# ".repeat 50
    #@showStack()
    #console.log "# ".repeat 50
    console.log args[0], str args[1]
    #@tell args

module.exports = XmlStackStream
