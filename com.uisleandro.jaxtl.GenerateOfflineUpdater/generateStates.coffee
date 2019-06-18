states = [
  "EMPTY_STATUS"
  "BEGIN_PAYLOAD"
  "END_PAYLOAD"
  "TAG_HEAD"
  "PREFIXED"
  "BEGIN_TAG"
  "READY_FOR_ATTRIBUTE"
  "END_TAG"
  "BEGIN_ATTRIBUTE"
  "SPACE"
  "DOUBLE_QUOTTED"
  "SINGLE_QUOTTED"
  "END_ATTRIBUTE"
  "BEGIN_COMMENT"
  "END_COMMENT"
  "BEGIN_CDATA"
  "END_CDATA"
  "LOW_PRIORITY_CONTENT"
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
