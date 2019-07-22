pipe=()->
  i = 1
  while i < arguments.length
    arguments[i-1].observe arguments[i]
    i++

module.exports = pipe
