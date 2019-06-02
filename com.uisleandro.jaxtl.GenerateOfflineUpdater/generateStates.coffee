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
  "SINGLE_QUOTED_ATTRIBUTE_VALUE"
  "DOUBLE_QUOTED_ATTRIBUTE_VALUE"
  "TAG_HEAD"
  "TAG_CONTENTS"
  "END_TAG"
]

console.log "module.exports = {"
i = 0
while i < states.length
  console.log "  #{states[i]}: #{i}"
  i++

console.log "}"

console.log "# {#{states.join(",")}} = require './states'"
