console.log "module.exports = {"


charName=(c)->
  if c is "/"
    return "SLASH"
  else if c is "<"
    return "LOWER_THAN"
  else if c is ">"
    return "GREATHER_THAN"
  else if c is "["
    return "OPEN_SQUARE_BRACES"
  else if c is "]"
    return "CLOSE_SQUARE_BRACES"
  else if c is ":"
    return "COLON"
  else if c is "."
    return "PERIOD"
  else if c is "?"
    return "QUESTION_MARK"
  else if c is "!"
    return "EXCLAMATION_POINT"
  else if c is "_"
    return "UNDERSCORE"
  else if c is "-"
    return "MINUS"
  else if c is "="
    return "EQUAL"
  else if c is "\t"
    return "TAB"
  else if c is "\r"
    return "CARRIAGE_RETURN"
  else if c is "\n"
    return "LINE_FEED"
  else if c is " "
    return "SPACE"
  else if c is "\'"
    return "SINGLE_QUOTE"
  else if c is "\""
    return "DOUBLE_QUOTE"
  else
    return c


i = 0
constants = ['0','9','C','D','A','T','X','M','L','Z','c','d','a','t','x','m','l','z','/','<','>','[',']',':','.','?','!','_','-','=','\t','\r','\n',' ','\'','"']
constantNames = constants.map (c)-> "CHAR_CODE_#{charName(c)}"
constantValues = constants.map (c)-> c.charCodeAt(0)

constantNames.push "SEND_DATA"
constantValues.push 0

constantNames.push "SEND_END_OF_FILE"
constantValues.push 0xFFFFFFFF

while i < constantNames.length
  c = constants[i]
  console.log "    #{constantNames[i]}: #{constantValues[i]}"
  i++

console.log "}"


console.log "# {#{constantNames.join(",")}} = require './constants'"
