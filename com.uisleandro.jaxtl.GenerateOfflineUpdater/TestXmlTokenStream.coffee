CharStream = require "./CharStream"
XmlBufferedStream = require "./XmlBufferedStream"
LogStream = require "./LogStream"
XmlTokenStream = require "./XmlTokenStream"
pipe = require "./Pipe"
Observable = require "./Observable"

#xmlBufferedStream = new XmlBufferedStream()
#xmlTokenStream = new XmlTokenStream()
logStream = new LogStream()

class MemoryCharStream
  constructor:(@data)->
    new Observable(@)
    @i = 0
  update:(s)->
    @tell(s)
  start:(n)->
    x = 0
    while x < n
      @update(@data[@i].charCodeAt(0))
      @i = @i + 1
      x++

memoryCharStream = new MemoryCharStream("""
<?xml version="1.0" encoding="UTF-8"?>
<root>
  <contact name="name" address="address"/>
  <hastext>text <div>another</div> and another</hastext>
</root>
""")

class ExpectedResultStream
  constructor:(stream)->
    new Observable(@)
    @stream = stream
  expect:(@expected)->
    @stream.start(2)
  update:(s)->
    if s isnt @expected
      throw "Error: Expecting #{@expected} but got #{s}"
    else
      console.log "O teste passou!!"

expected = new ExpectedResultStream(memoryCharStream)

pipe expected, memoryCharStream

expected.expect(60) # == '<'
