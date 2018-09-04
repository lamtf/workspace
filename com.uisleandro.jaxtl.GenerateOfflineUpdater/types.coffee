str = "fgh.uml"

fileSufix = (s)->
  if s.indexOf("/") > -1
    return s.substr s.lastIndexOf("/")+1
  else
    return s
  
console.log fileSufix str