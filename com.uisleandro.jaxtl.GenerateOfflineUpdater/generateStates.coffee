states = [
    "NULL"
    "BEGIN_PAYLOAD"
    "TAG_HEAD"
    "ENDING_TAG"
    "COMMENT"
    "BEGIN_CDATA"
    "LOW_PRIORITY_CONTENT"
    "BEGIN_TAG"
    "END_TAG"
    "END_COMMENT"
    "END_CDATA"
    "END_PAYLOAD"
    "ENDING_TAG"
    "BEGINNING_TAG"
    "READY_FOR_ATTR"
    "BEGIN_ATTRIBUTE"
    "SPACE1"
    "SPACE2"
    "DOUBLE_QUOTTED"
    "SINGLE_QUOTTED"
    "END_ATTRIBUTE"
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
