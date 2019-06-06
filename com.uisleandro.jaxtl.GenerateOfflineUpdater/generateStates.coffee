states = [
  "EMPTY_STATUS"
  "OPENING_PAYLOAD"
  "XML_HEAD"
  "CLOSING_PAYLOAD"
  "CLOSED_TAG"
  "OPENING_TAG"
  "OPENING_TAG"
  "CLOSING_TAG"
  "OPENING_COMMENT"
  "CLOSING_COMMENT"
  "OPENING_CDATA"
  "CLOSING_CDATA"
  "TAG_NAME"
  "ATTRIBUTE_NAME"
  "ATTRIBUTE_VALUE"
  "SINGLE_QUOTED"
  "DOUBLE_QUOTED"
  "TAG_HEAD"
  "TAG_HEAD_OR_FOOTER"
  "TAG_CONTENTS"
  "END_TAG"
]

console.log "module.exports = {"
i = 0
while i < states.length
  console.log "  #{states[i]}: 0x#{(1<<(i)>>1).toString(16)}"
  i++

console.log "  MASK: 0xFFFFFFFF"

console.log "}"


states.push "MASK"

console.log "# {#{states.join(",")}} = require './states'"
