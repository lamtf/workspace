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

class XmlStackStream
  constructor:()->
    @stack = []
    Observable.extends @
    @nodeId = 0

  push:(data)->
    @stack.push data
    return
  pop:()->
    return stack.pop()
  peek:(data)->
    return stack[stack.length-1]
  update:(args)->

    if args[0] is TOKEN_BEGIN_XML
      @push create_node "xml", nodeId, null
      nodeId = nodeId + 1
    else if args[0] is TOKEN_EMPTY_ATTR
      add_property
    else if args[0] is TOKEN_ATTR_NAME
      @push { type: null, value: null }
    else if args[0] is TOKEN_ATTR_VALUE
      @pop { type: null, value: null }
    else if args[0] is TOKEN_TAG_HEAD
      @push { type: null, value: null }
    else if args[0] is TOKEN_END_TAG
      @pop { type: null, value: null }
    else if args[0] is TOKEN_DATA
      @push { type: null, value: null }

    console.log args[0], str args[1]
    #@tell args

module.exports = XmlStackStream
