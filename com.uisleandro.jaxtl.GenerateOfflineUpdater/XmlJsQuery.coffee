class condition
  constructor: (@a)->
    @ws = [[]]
    @w = @ws[0]
  where: (f)->
    @w[@w.length] = f
    @
  and: (f)->
    @where(f)
  or: (f)->
    @ws[@ws.length] = []
    @w = @ws[@ws.length-1]
    @where(f)

select = (a)->
  new condition(a)

### rever ###
testall = (fn,v)->
  i = 0
  while(i < fn.length)
    if(!fn[i](v))
      return false
    i++
  return true

query=(x)->
  r = []
  i = 0
  j = 0
  while(j < x.ws.length)
    while(i < x.a.length)
      if testall x.ws[j], x.a[i]
        r[r.length] = x.a[i]
      i++
    j++
  r
#@queryR
queryR=(x, r, root)->
  if "undefined" is typeof r
    r = []
    root = x.a
  j = 0
  while(j < x.ws.length)
    if testall x.ws[j], root
      r[r.length] = root
      j = j + 9999
    j++
  if root.children
    i = 0
    while(i < root.children.length)
      r = queryR(x, r, root.children[i])
      i++
  return r

like=(a,b)->
  a.toUpperCase().indexOf(b.toUpperCase()) > -1;

contains_text_like=(text)->
  (a)->
    if typeof a.innerText is "undefined"
      return false
    else
      return a.innerText.indexOf(text) > -1

### i cant use prototype into a object ###
contains_attribute_equals=(k,v)->
  (a)->
    if typeof a.getAttribute isnt "function"
      if typeof a.properties isnt "undefined"
        i = 0
        while(i < a.properties.length)
          if(a.properties[i].name is k and a.properties[i].value is v)
            return true
          i++
      return false
    else
      return a.getAttribute(k) is v

attribute_name_is_like=(k)->
  (a)->
    if typeof a.getAttribute isnt "function"
      if typeof a.properties isnt "undefined"
        i = 0
        while(i < a.properties.length)
          if(like(a.properties[i].name,k))
            return true
          i++
      return false
    else
      return a.getAttribute(k) is v


is_named=(k)->
  (a)->
    if typeof a.properties isnt "undefined"
      i = 0
      while i < a.properties.length
        if a.properties[i].name is 'name'
          return true
        i++
    return false

contains_tagName=(tagName)->
  (a)->
    a.tagName.toUpperCase() is tagName.toUpperCase()
    
    
    
module.exports
  "condition":condition
  "select": select
  "testall":testall
  "query":query
  "queryR":queryR
  "like":like
  "contains_text_like":contains_text_like
  "contains_attribute_equals":contains_attribute_equals
  "attribute_name_is_like":attribute_name_is_like
  "is_named":is_named
  "contains_tagName":contains_tagName